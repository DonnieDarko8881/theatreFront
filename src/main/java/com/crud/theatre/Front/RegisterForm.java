package com.crud.theatre.Front;

import com.crud.theatre.controller.UserController;
import com.crud.theatre.domain.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
public class RegisterForm extends FormLayout {
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField mailText = new TextField("Mail");
    private PasswordField passwordField = new PasswordField("Password");
    private Button registerButton = new Button("Register");

    private UserController userController;

    @Autowired
    public RegisterForm(UserController userController) {
        this.userController = userController;
        add(firstName, lastName, mailText, passwordField, registerButton);

        registerButton.addClickListener(event -> register());
    }

    private void register() {
        UserDto userDto = new UserDto(firstName.getValue(), lastName.getValue(),
                mailText.getValue(), passwordField.getValue());
        if (!userController.mailExist(mailText.getValue())) {
            userController.save(userDto);
            Notification.show(mailText.getValue() + " has been registered");
            setVisible(false);
            mailText.setInvalid(false);
        } else {
            mailText.setInvalid(true);
            Notification.show(mailText.getValue() + " is existing in our DataBase. Try Login in");
            mailText.focus();
        }
    }
}
