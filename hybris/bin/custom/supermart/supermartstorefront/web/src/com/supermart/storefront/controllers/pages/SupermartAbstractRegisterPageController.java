package com.supermart.storefront.controllers.pages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermart.storefront.forms.SupermartRegisterForm;
import com.supermart.storefront.validators.SupermartRegistrationValidator;
import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractRegisterPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.acceleratorstorefrontcommons.forms.ConsentForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.GuestForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.RegisterForm;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.consent.data.AnonymousConsentData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.RegisterData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static de.hybris.platform.commercefacades.constants.CommerceFacadesConstants.CONSENT_GIVEN;

public abstract class SupermartAbstractRegisterPageController extends AbstractRegisterPageController
{
    private static final Logger LOGGER = Logger.getLogger(AbstractRegisterPageController.class);

    private static final String FORM_GLOBAL_ERROR = "form.global.error";

    private static final String CONSENT_FORM_GLOBAL_ERROR = "consent.form.global.error";
    private static final String REGISTRATION_CONFIRM_MESSAGE = "registration.confirmation.message.title";
    private static final String REGISTRATION_CONFIRM_LOGIN_MESSAGE = "registration.confirmation.login.message.title";
    private static final String LOGIN_URL = REDIRECT_PREFIX + "/login";

    @Resource(name = "supermartRegistrationValidator")
    private SupermartRegistrationValidator supermartRegistrationValidator;

    protected String processRegisterUserRequest(final String referer, final SupermartRegisterForm form, final BindingResult bindingResult,
                                                final Model model, final HttpServletRequest request, final HttpServletResponse response,
                                                final RedirectAttributes redirectModel) throws CMSItemNotFoundException
    {
        final CustomerData anonymousCustomer = getCustomerFacade().getCurrentCustomer();
        final boolean isSecureCustomerRegistrationEnabled = isSecureCustomerRegistrationEnabled();
        if (bindingResult.hasErrors())
        {
            form.setTermsCheck(false);
            model.addAttribute(form);
            model.addAttribute(new LoginForm());
            model.addAttribute("registerForm", new SupermartRegisterForm());
            model.addAttribute(new GuestForm());
            GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
            return handleRegistrationError(model);
        }

        final RegisterData data = new RegisterData();
        data.setFirstName(form.getFirstName());
        data.setLastName(form.getLastName());
        data.setLogin(form.getEmail());
        data.setAadharNumber(form.getAadharNumber());
        data.setPassword(form.getPwd());
        data.setTitleCode(form.getTitleCode());
        try
        {

            getCustomerFacade().register(data);
            if (isSecureCustomerRegistrationEnabled)
            {
                // Once creation succeed, need set current user to store consent
                getUserFacade().setCurrentUser(form.getEmail().toLowerCase(Locale.getDefault()));
            }
            else
            {
                getAutoLoginStrategy().login(form.getEmail().toLowerCase(Locale.getDefault()), form.getPwd(), request, response);
                GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
                        REGISTRATION_CONFIRM_MESSAGE);
            }
        }
        catch (final DuplicateUidException e)
        {
            return handleDuplicateUidException(model, bindingResult, form, isSecureCustomerRegistrationEnabled, redirectModel);
        }

        // Consent form data
        try
        {
            final ConsentForm consentForm = form.getConsentForm();
            if (consentForm != null && consentForm.getConsentGiven())
            {
                getConsentFacade().giveConsent(consentForm.getConsentTemplateId(), consentForm.getConsentTemplateVersion());
            }
        }
        catch (final Exception e)
        {
            LOGGER.error("Error occurred while creating consents during registration", e);
            GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.ERROR_MESSAGES_HOLDER, CONSENT_FORM_GLOBAL_ERROR);
        }

        // save anonymous-consent cookies as ConsentData
        final Cookie cookie = WebUtils.getCookie(request, WebConstants.ANONYMOUS_CONSENT_COOKIE);
        if (cookie != null)
        {
            try
            {
                final ObjectMapper mapper = new ObjectMapper();
                final List<AnonymousConsentData> anonymousConsentDataList = Arrays.asList(mapper.readValue(
                        URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8.displayName()), AnonymousConsentData[].class));
                anonymousConsentDataList.stream().filter(consentData -> CONSENT_GIVEN.equals(consentData.getConsentState()))
                        .forEach(consentData -> consentFacade.giveConsent(consentData.getTemplateCode(),
                                Integer.valueOf(consentData.getTemplateVersion())));
            }
            catch (final UnsupportedEncodingException e)
            {
                LOGGER.error(String.format("Cookie Data could not be decoded : %s", cookie.getValue()), e);
            }
            catch (final IOException e)
            {
                LOGGER.error("Cookie Data could not be mapped into the Object", e);
            }
            catch (final Exception e)
            {
                LOGGER.error("Error occurred while creating Anonymous cookie consents", e);
            }
        }
        return handleRedirectInRegistration(anonymousCustomer, isSecureCustomerRegistrationEnabled, request, response, redirectModel);

    }

    private String handleRedirectInRegistration(final CustomerData anonymousCustomer,
                                                final boolean isSecureCustomerRegistrationEnabled, final HttpServletRequest request, final HttpServletResponse response,
                                                final RedirectAttributes redirectModel)
    {
        if (isSecureCustomerRegistrationEnabled)
        {
            if (anonymousCustomer != null)
            {
                getUserFacade().setCurrentUser(anonymousCustomer.getUid());
            }
            GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, REGISTRATION_CONFIRM_LOGIN_MESSAGE);
            return LOGIN_URL;
        }
        else
        {
            return REDIRECT_PREFIX + getSuccessRedirect(request, response);
        }
    }

    private boolean isSecureCustomerRegistrationEnabled()
    {
        return Config.getBoolean("toggle.secure.customer.registration.enabled", false);
    }

    private String handleDuplicateUidException(final Model model, final BindingResult bindingResult, final RegisterForm form,
                                               final boolean isSecureCustomerRegistrationEnabled, final RedirectAttributes redirectModel)
            throws CMSItemNotFoundException
    {
        if (isSecureCustomerRegistrationEnabled)
        {
            GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
                    REGISTRATION_CONFIRM_LOGIN_MESSAGE);
            LOGGER.info("registration with duplicated uid. Ignore it");
            return LOGIN_URL;
        }
        else
        {
            LOGGER.debug("registration failed.");
            form.setTermsCheck(false);
            model.addAttribute(form);
            model.addAttribute(new LoginForm());
            model.addAttribute(new GuestForm());
            bindingResult.rejectValue("email", "registration.error.account.exists.title");
            GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
            return handleRegistrationError(model);
        }
    }

    protected Validator getRegistrationValidator()
    {
        return supermartRegistrationValidator;
    }

}
