package com.supermart.storefront.validators;
import com.supermart.storefront.forms.SupermartRegisterForm;
import de.hybris.platform.acceleratorstorefrontcommons.forms.validation.RegistrationValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component("supermartRegistrationValidator")
public class SupermartRegistrationValidator extends RegistrationValidator
{
    @Override
    public void validate(final Object object, final Errors errors)
    {
        final SupermartRegisterForm registerForm = (SupermartRegisterForm) object;
        final String aadharNumber = registerForm.getAadharNumber();

        validateAadharNumber(errors, aadharNumber);
    }

    protected void validateAadharNumber(final Errors errors, final String aadharNumber)
    {
        if (StringUtils.isEmpty(aadharNumber) || StringUtils.length(aadharNumber) != 12 ||
                !aadharNumber.chars().allMatch(Character::isDigit))
        {
            errors.rejectValue("aadharNumber", "register.aadharNumber.invalid");
        }
    }

}
