package com.crud.theatre.Front;

import com.crud.theatre.controller.UserController;
import com.crud.theatre.domain.UserDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@Route("users")
@SpringComponent
@UIScope
public class User extends VerticalLayout {
    private Button backToAdministrationPanel = new Button(new Icon(VaadinIcon.BACKSPACE));
    private Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));

    private Grid<UserDto> usersGrid = new Grid<>(UserDto.class);
    private HorizontalLayout buttonsMenu = new HorizontalLayout(backToAdministrationPanel, refreshButton);

    private Theatre theatre;
    private UserController userController;

    @Autowired
    public User(Theatre theatre, UserController userController) {
        this.theatre = theatre;
        this.userController = userController;

        add(buttonsMenu, usersGrid);
        setSizeFull();
        usersGrid.setSizeFull();

        usersGrid.setColumns("id", "firstName", "lastName", "mail");

        refreshButton.addClickListener(event -> refresh());

        refresh();
        theatre.navigate(backToAdministrationPanel, "adminPanel");
    }

    public void refresh() {
        usersGrid.setItems(userController.getUsers());
    }
}
