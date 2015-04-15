package com.codex.restCrud.service;

import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.model.User;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Service
public class SignupProcessor implements Validator {
    @Autowired
    private UserDao userDao;

    public String processSignup(SignupForm signupForm, BindingResult result) throws BeansException {
        validate(signupForm, result);
        if (result.hasErrors()) {
            return "signup";
        }

        User newUser = addUser(signupForm);
        if (newUser == null)
            return "signup-error";

        sendActivationLink(newUser, signupForm.getEmail());

        return "signup-sent";

    }

    public boolean activateUser(Integer id) {
        return userDao.setAsActivated(id);
    }

    public void validate(Object signupform, Errors errors) {
        SignupForm signupForm = (SignupForm) signupform;

        validateUserName(signupForm, errors);
        validatePasswords(signupForm, errors);
        validateEmail(signupForm, errors);
    }

    public boolean supports(Class<?> clazz) {
        return SignupForm.class.isAssignableFrom(clazz);
    }

    private User addUser(SignupForm signupForm) {
        User user = new User();
        user.setName(signupForm.getUsername());
        user.setEmail(signupForm.getEmail());
        user.setAdmin(false);

        String shapassword;
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        shapassword = encoder.encodePassword(signupForm.getPassword(), null);
        user.setPassword(shapassword);

        userDao.createUser(user);
        return user;
    }

    private void sendActivationLink(User user, String email) throws BeansException {
        @SuppressWarnings("resource")
        BeanFactory beanfactory = new ClassPathXmlApplicationContext(
                "WEB-INF/spring/root-context.xml");
        Mail mail = (Mail) beanfactory.getBean("mail");

        mail.send("You are welcome!",
                "http://localhost:8080/restCrud/signup/" + user.getId(),
                email);
    }

    private void validateEmail(SignupForm signupForm, Errors errors) {
        if (!EmailValidator.getInstance().isValid(signupForm.getEmail())) {
            errors.rejectValue("email", "email.notValid",
                    "Email address is not valid.");
        }
    }

    private void validatePasswords(SignupForm signupForm, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "password.empty", "Password must not be empty.");

        if (!(signupForm.getPassword()).equals(signupForm.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                    "confirmPassword.passwordDontMatch",
                    "Passwords don't match.");
        }
    }

    private void validateUserName(SignupForm signupForm, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
                "username.empty", "Username must not be empty.");

        String username = signupForm.getUsername();

        if ((username.length()) > 16) {
            errors.rejectValue("username", "username.tooLong",
                    "Username must not more than 16 characters.");
        }

        if (!noSuchUser(username))
            errors.rejectValue("username", "username.occupied",
                    "Username occupied");
    }

    private boolean noSuchUser(String login) {
        if (userDao.getLazyByName(login) == null)
            return true;
        else
            return false;
    }
}
