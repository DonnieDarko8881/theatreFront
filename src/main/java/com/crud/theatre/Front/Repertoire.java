package com.crud.theatre.Front;

import com.crud.theatre.controller.RepertoireController;
import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.SpectacleDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.time.format.DateTimeParseException;
import java.util.List;

@Route("repertoire")
@SpringComponent
public class Repertoire extends VerticalLayout {
    private Button backToAdministrationPanel = new Button(new Icon(VaadinIcon.BACKSPACE));
    private Button bookButton = new Button("Book");
    private Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
    private Grid<SpectacleDate> datesGrid = new Grid<>(SpectacleDate.class);
    private SpectacleDateForm dateForm = new SpectacleDateForm(this);
    private ComboBox<SpectacleDto> spectaclesName = new ComboBox<>("Spectacle");
    private CopyStageForm copyStageForm = new CopyStageForm();

    private RepertoireController repertoireController;
    private Reservation reservation;


    @Autowired
    public Repertoire(RepertoireController repertoireController, Reservation reservation) throws URISyntaxException {
        this.repertoireController = repertoireController;
        this.reservation = reservation;
        HorizontalLayout buttonsMenu = new HorizontalLayout(backToAdministrationPanel, refreshButton, bookButton);

        HorizontalLayout mainContent = new HorizontalLayout(datesGrid, bookButton);
        mainContent.setSizeFull();

        bookButton.addClickListener(event -> bookButton.getUI().ifPresent(ui -> ui.navigate("reservation")));
        backToAdministrationPanel.addClickListener(event ->
                backToAdministrationPanel.getUI().ifPresent(ui -> ui.navigate("adminPanel")));

        refreshButton.addClickListener(event -> refresh());

        datesGrid.setColumns("id", "date", "spectacleId", "spectacleName", "stageId", "stageName", "stageCopy");
        refresh();
        HorizontalLayout formes = new HorizontalLayout();
        formes.add(dateForm, copyStageForm);
        spectaclesName.setItems(getSpectacles());
        spectaclesName.setWidth("300px");
        dateForm.add(spectaclesName, dateForm.getButtons());
        add(buttonsMenu, mainContent, formes);
        setSizeFull();
        datesGrid.setSizeFull();

        datesGrid.asSingleSelect().addValueChangeListener(event -> {
            try {
                copyStageForm.getDateIdText().setValue(String.valueOf((event.getValue().getId())));
                copyStageForm.getStageIdText().setValue(String.valueOf(event.getValue().getStageId()));

            } catch (NullPointerException e) {
            }
        });

        dateForm.getAddDateButton().addClickListener(event -> saveDate());
        dateForm.getDeleteButton().addClickListener(event -> deleteDate());
        copyStageForm.getSaveCopyButton().addClickListener(event -> saveCopyStage());
    }

    private void saveCopyStage() {
        String stageId = copyStageForm.getStageIdText().getValue();
        String dateId = copyStageForm.getDateIdText().getValue();
        String spectaclePrice = String.valueOf(copyStageForm.getSpectaclePrice().getValue());
        repertoireController.saveStageCopy(stageId, dateId, spectaclePrice);
        refresh();
        reservation.refresh();
    }

    private void refresh() {
        datesGrid.setItems(getDates());
        copyStageForm.getDateIdText().setValue("");
        copyStageForm.getStageIdText().setValue("");
        dateForm.getDate().setValue("");
        spectaclesName.setItems(getSpectacles());
    }

    private void deleteDate() {
        if (copyStageForm.getDateIdText().getValue().isEmpty()) {
            Notification.show("To remove Date is necessary Date ID, you can write in or you can click," +
                    " single select, on the grid");
        } else {
            String dateId = copyStageForm.getDateIdText().getValue();
            repertoireController.deleteSpectacleDate(dateId);
            refresh();
            reservation.refresh();
            Notification.show("Date Id " + dateId + " has been removed ");
        }
    }


    private void saveDate() {
        String spectacleDate = dateForm.getDate().getValue();
        try {
            Long spectacleId = spectaclesName.getValue().getId();
            repertoireController.saveSpectacleDate(spectacleId, spectacleDate);
            Notification.show("Date has been created " + spectacleDate);
        } catch (DateTimeParseException e) {
            Notification.show("Date has to have format \"yyyy-mm-ddThh:mm\", for example \"2000-03-10T18:35\"");
        } catch (NullPointerException e) {
            Notification.show("To Create Date is necessary to choose Spectacle");
        }
        refresh();
    }


    private List<SpectacleDate> getDates() {
        return repertoireController.getDates();
    }

    private List<SpectacleDto> getSpectacles() {
        return repertoireController.getSpectacles();
    }
}
