package com.crud.theatre.Front;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class HomeButton {
    private Button home;

    public HomeButton() {
        this.home = new Button(new Icon(VaadinIcon.HOME));

    }

    public Button getHome() {
        return home;
    }
}
