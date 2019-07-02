package com.crud.theatre.Front;

import com.crud.theatre.controller.ReservationController;
import com.crud.theatre.domain.ReservationDto;
import com.crud.theatre.domain.SeatsDto;
import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.domain.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route("reservation")
@SpringComponent
public class Reservation extends VerticalLayout {
    private TextField userIdText = new TextField("User Id");
    private TextField stageCopyIdText = new TextField("stageCopyId");
    private TextField seatsNumberText = new TextField("Seats Number");
    private Grid<SeatsDto> seatsGrid = new Grid<>(SeatsDto.class);
    private Grid<StageCopy> stageCopyDtoGrid = new Grid<>(StageCopy.class);
    private Button bookButton = new Button("Book");
    private Button changeStatusButton = new Button("Change Status");
    private Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
    private Button home = new Button(new Icon(VaadinIcon.HOME));
    private HorizontalLayout buttons = new HorizontalLayout(home, refreshButton, changeStatusButton);
    private VerticalLayout reservationForm = new VerticalLayout(userIdText, stageCopyIdText, seatsNumberText, bookButton);
    private HorizontalLayout mainContent = new HorizontalLayout(stageCopyDtoGrid, seatsGrid);
    private TextField seatsIdText = new TextField("Seats Id");
    private ComboBox<Status> statusComboBox = new ComboBox<>("Status");
    private Button changeButton = new Button("Change");
    private VerticalLayout statusForm = new VerticalLayout(seatsIdText, statusComboBox, changeButton);
    private VerticalLayout formes = new VerticalLayout(reservationForm, statusForm);


    private final ReservationController reservationController;
    private Theatre theatre;

    @Autowired
    public Reservation(ReservationController reservationController, Theatre theatre) {
        this.reservationController = reservationController;
        this.theatre = theatre;
        mainContent.add(formes);
        add(buttons, mainContent);
        setSizeFull();
        mainContent.setSizeFull();
        stageCopyDtoGrid.setSizeFull();
        seatsGrid.setSizeFull();

        seatsGrid.setVisible(false);
        formes.setVisible(false);
        changeStatusButton.setVisible(false);
        userIdText.setRequired(true);

        statusComboBox.setItems(Status.values());

        bookButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        stageCopyDtoGrid.setColumns("id", "date", "spectacleName", "seats", "spectaclePricePLN");
        stageCopyDtoGrid.getColumnByKey("seats").setVisible(false);
        stageCopyDtoGrid.getColumnByKey("id").setVisible(false);

        seatsGrid.setColumns("id", "number", "status");
        seatsGrid.getColumnByKey("id").setVisible(false);
        refresh();

        navigate(home, "");

        changeButton.addClickListener(event -> {
            changeStatus();
            refresh();
        });

        refreshButton.addClickListener(event -> refresh());

        changeStatusButton.addClickListener(event -> {
            formes.setVisible(true);
            reservationForm.setVisible(false);
            statusForm.setVisible(true);
            statusComboBox.focus();
        });

        bookButton.addClickListener(event -> {
            if (!userIdText.isEmpty()) {
                makeReservation();
                refresh();
            } else {
                Notification.show("fulFill User Id");
            }
        });

        stageCopyDtoGrid.asSingleSelect().addValueChangeListener(event -> {
            seatsGrid.setVisible(true);
            try {
                stageCopyIdText.setValue(String.valueOf(event.getValue().getId()));
                seatsGrid.setItems(getSeats(event.getValue()));
            } catch (NullPointerException e) {
            }

        });

        seatsGrid.asSingleSelect().addValueChangeListener(event -> {
            try {
                seatsNumberText.setValue(String.valueOf(event.getValue().getNumber()));
                seatsIdText.setValue(String.valueOf(event.getValue().getId()));
                String userId = theatre.getUserId();
                if (theatre.getUserId() != null) {
                    userIdText.setValue(userId);
                }
                if (event.getValue().getStatus().equals(Status.FREE.toString())) {
                    formes.setVisible(true);
                    statusForm.setVisible(false);
                    reservationForm.setVisible(true);
                } else {
                    formes.setVisible(false);
                    reservationForm.setVisible(false);
                }
                if (userId.equals("ADMIN")) {
                    changeStatusButton.setVisible(true);
                    statusForm.setVisible(true);
                } else {
                    changeStatusButton.setVisible(false);
                    statusForm.setVisible(false);
                }

            } catch (NullPointerException e) {
            }
            userIdText.focus();
        });
    }

    private void changeStatus() {
        String stageCopyId = stageCopyIdText.getValue();
        String seatsId = seatsIdText.getValue();
        String status = statusComboBox.getValue().toString();
        reservationController.changeStatus(stageCopyId, seatsId, status);
    }

    private void makeReservation() {
        long userId = Long.valueOf(userIdText.getValue());
        long stageCopyId = Long.valueOf(stageCopyIdText.getValue());
        int seatsNumber = Integer.valueOf(seatsNumberText.getValue());
        ReservationDto reservationDto = new ReservationDto(userId, stageCopyId, seatsNumber);
        reservationController.saveReservation(reservationDto);
    }

    private void navigate(Button button, String location) {
        button.addClickListener(event -> button.getUI().ifPresent(ui -> ui.navigate(location)));
    }

    public void refresh() {
        stageCopyDtoGrid.setItems(reservationController.getStageCopies());
        seatsGrid.deselectAll();
        stageCopyDtoGrid.deselectAll();
        seatsGrid.setVisible(false);
        formes.setVisible(false);
        changeStatusButton.setVisible(false);
    }

    private List<SeatsDto> getSeats(StageCopy stageCopy) {
        return stageCopy.getSeats();
    }
}
