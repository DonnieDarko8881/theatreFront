package com.crud.theatre.Front;

import com.crud.theatre.controller.ReservationController;
import com.crud.theatre.domain.ReservationDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;


@Route("reservations")
@SpringComponent
public class Reservations extends VerticalLayout {
    private Button backToAdministrationPanel = new Button(new Icon(VaadinIcon.BACKSPACE));
    private Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
    private Grid<ReservationDto> reservationGrid = new Grid<>(ReservationDto.class);
    private HorizontalLayout buttonsMenu = new HorizontalLayout(backToAdministrationPanel, refreshButton);

    private Theatre theatre;
    private ReservationController reservationController;

    @Autowired
    public Reservations(Theatre theatre, ReservationController reservationController) {
        this.theatre = theatre;
        this.reservationController = reservationController;
        add(buttonsMenu, reservationGrid);
        setSizeFull();
        reservationGrid.setSizeFull();

        reservationGrid.setColumns("reservationId", "reservationDate", "userId", "stageCopyId", "seatsId", "seatsNumber");

        refreshButton.addClickListener(event -> refresh());

        refresh();
        theatre.navigate(backToAdministrationPanel, "adminPanel");
    }

    public void refresh() {
        reservationGrid.setItems(reservationController.gerReservations());
    }
}
