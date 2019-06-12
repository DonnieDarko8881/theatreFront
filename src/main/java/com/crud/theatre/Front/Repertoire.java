package com.crud.theatre.Front;

import com.crud.theatre.controller.RepertoireController;
import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.SpectacleDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.List;

@Route("repertoire")
@SpringComponent
public class Repertoire extends VerticalLayout {
    private Grid<SpectacleDate> datesGrid = new Grid<>(SpectacleDate.class);
    private SpectacleDateForm dateForm = new SpectacleDateForm(this);
    private ComboBox<SpectacleDto> spectaclesName = new ComboBox<>("Spectacle");
    private CopyStageForm copyStageForm = new CopyStageForm();
    private Button bookButton = new Button("Book");

    private RepertoireController repertoireController;
    private Reservation reservation;

    @Autowired
    public Repertoire(RepertoireController repertoireController, Reservation reservation) throws URISyntaxException {
        this.repertoireController = repertoireController;
        this.reservation = reservation;
        HorizontalLayout mainContent = new HorizontalLayout(datesGrid, bookButton);
        mainContent.setSizeFull();

        bookButton.addClickListener(event -> bookButton.getUI().ifPresent(ui -> ui.navigate("reservation")));

        datesGrid.setColumns("id", "date", "spectacleId", "spectacleName", "stageId", "stageName", "stageCopy");
        refresh();
        HorizontalLayout formes = new HorizontalLayout();
        formes.add(dateForm, copyStageForm);
        spectaclesName.setItems(getSpectacles());
        spectaclesName.setWidth("300px");
        dateForm.add(spectaclesName, dateForm.getButtons());
        add(mainContent, formes);
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
        repertoireController.saveStageCopy(stageId, dateId,spectaclePrice);
        refresh();
        reservation.refresh();
    }

    private void refresh() {
        datesGrid.setItems(getDates());
    }

    private void deleteDate() {
        String dateId = copyStageForm.getDateIdText().getValue();
        repertoireController.deleteSpectacleDate(dateId);
        refresh();
        Notification.show("Date Id " + dateId + " has been removed ");
    }


    private void saveDate() {
        String spectacleDate = dateForm.getDate().getValue();
        try {
            Long spectacleId = spectaclesName.getValue().getId();
            repertoireController.saveSpectacleDate(spectacleId, spectacleDate);
            Notification.show("Date has been created " + spectacleDate);
        } catch (NullPointerException e) {
            Notification.show("To Create Date is neccesary to choose Spectacle");
        }
        refresh();
    }


    private List<SpectacleDate> getDates() {
        return repertoireController.getDates();
    }

    private List<SpectacleDto> getSpectacles() {
        return repertoireController.getSpectacles();
    }

    public CopyStageForm getCopyStageForm() {
        return copyStageForm;
    }
}
