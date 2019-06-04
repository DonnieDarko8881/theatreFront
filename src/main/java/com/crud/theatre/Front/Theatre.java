package com.crud.theatre.Front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "")
@SpringComponent
public class Theatre extends VerticalLayout {
    private String userId;
    private TextField mailText = new TextField("Mail");
    private Button signInButton = new Button(new Icon(VaadinIcon.SIGN_IN));
    private Button login = new Button("Login");
    private Button spectacle = new Button("Spectacle");
    private Button actors = new Button("Actors");
    private Button repertoire = new Button("Repertoire");
    private Button reservation = new Button("Reservation");
    private HorizontalLayout loginForm = new HorizontalLayout(mailText,signInButton);

//    private Reservation resev;
    @Autowired
    public Theatre() {

        HorizontalLayout toolBar = new HorizontalLayout(login, spectacle, actors, repertoire, reservation);
        add(toolBar, loginForm);

        loginForm.setVisible(false);

        navigate(spectacle, "spectacles");
        navigate(actors, "actors");
        navigate(repertoire, "repertoire");
        navigate(reservation, "reservation");

        login.addClickListener(event -> {
            loginForm.setVisible(true);
        });

        signInButton.addClickListener(event -> {
            setUserId(mailText.getValue());
            Notification.show("narazie zamiast miala wpisuje id użytkownika lub id administratora");
            loginForm.setVisible(true);
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

