package com.crud.theatre.Front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

@Route("adminPanel")
@SpringComponent
public class AdministrationPanel extends VerticalLayout {
    private Button home = new Button(new Icon(VaadinIcon.HOME));
    private Button usersButton = new Button("Users");
    private Button spectaclesActorsButton =  new Button("Spectacles And Actors");
    private Button stagesButton = new Button("Stages");
    private Button reservationsButton = new Button("Reservations");
    private HorizontalLayout menuBarButtons = new HorizontalLayout(home,spectaclesActorsButton, stagesButton,
            reservationsButton, usersButton);

    private Theatre theatre;

    @Autowired
    public AdministrationPanel(Theatre theatre) {
        this.theatre = theatre;
        add(menuBarButtons);

        theatre.navigate(reservationsButton,"reservations");
        theatre.navigate(spectaclesActorsButton,"spectacles&actors");
        theatre.navigate(home,"");
        theatre.navigate(stagesButton,"stages");
        theatre.navigate(usersButton, "users");
    }
}
