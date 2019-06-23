package com.crud.theatre.Front;

import com.crud.theatre.controller.UserController;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "")
@SpringComponent
public class Theatre extends VerticalLayout {
    private String userId;
    private TextField mailText = new TextField("Mail");
    private PasswordField passwordField = new PasswordField("Password");

    private Button signInButton = new Button(new Icon(VaadinIcon.SIGN_IN));
    private Button registerButton = new Button("Register");
    private Button login = new Button("Login");
    private Button administationPanelButton = new Button("Administration Panel");
    private Button spectacle = new Button("Spectacle");
    private Button actors = new Button("Actors");
    private Button reservation = new Button("Reservation");
    private FormLayout loginForm = new FormLayout(mailText, passwordField, signInButton, registerButton);


    private FormLayout registerForm;
    private UserController userController;

    //    private Reservation resev;
    @Autowired
    public Theatre(FormLayout registerForm, UserController userController) {
        this.registerForm = registerForm;
        this.userController = userController;

        HorizontalLayout toolBar = new HorizontalLayout(login, administationPanelButton, spectacle, actors, reservation);
        add(toolBar, loginForm, this.registerForm);
        loginForm.setWidth("400px");
        loginForm.setVisible(false);
        administationPanelButton.setVisible(false);
        this.registerForm.setVisible(false);


        navigate(spectacle, "spectacles");
        navigate(actors, "actors");
        navigate(reservation, "reservation");
        navigate(administationPanelButton, "adminPanel");

        login.addClickListener(event -> {
            loginForm.setVisible(true);
            this.registerForm.setVisible(false);
        });

        registerButton.addClickListener(event -> {
            loginForm.setVisible(false);
            this.registerForm.setVisible(true);

        });

        signInButton.addClickListener(event -> {
            if (userController.loginSuccess(mailText.getValue(), passwordField.getValue())) {
                setUserId(String.valueOf(userController.getUserDtoByMail(mailText.getValue()).getId()));
                Notification.show("Logged in successfully");
                loginForm.setVisible(false);
                administationPanelButton.setVisible(false);
            } else {
                Notification.show("Password or mail is wrong");
            }
            if (mailText.getValue().equals("ADMIN") && passwordField.getValue().equals("ADMIN")) {
                setUserId("ADMIN");
                administationPanelButton.setVisible(true);
                loginForm.setVisible(false);
            }
        });
    }

    protected void navigate(Button button, String location) {
        button.addClickListener(event -> button.getUI().ifPresent(ui -> ui.navigate(location)));
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

