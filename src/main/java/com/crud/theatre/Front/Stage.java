package com.crud.theatre.Front;

import com.crud.theatre.controller.StageController;
import com.crud.theatre.domain.StageDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@Route("stages")
@SpringComponent
@UIScope
public class Stage extends VerticalLayout {
    private TextField stageName = new TextField("Stage Name");
    private TextField stagId = new TextField("Stage Id");
    private NumberField seatsAmount = new NumberField("Amount Of Seats");
    private Button saveStageButton = new Button("Save");
    private Button backToAdministrationPanel = new Button(new Icon(VaadinIcon.BACKSPACE));
    private Button addNewStage = new Button("Add New Stage");
    private Grid<StageDto> stagesGrid = new Grid<>(StageDto.class);
    private VerticalLayout saveStageForm = new VerticalLayout(stagId, stageName, seatsAmount, saveStageButton);
    private HorizontalLayout stagesContent = new HorizontalLayout(stagesGrid, saveStageForm);
    private HorizontalLayout menuButtons = new HorizontalLayout(backToAdministrationPanel, addNewStage);


    private Theatre theatre;
    private StageController stageController;

    @Autowired
    public Stage(Theatre theatre, StageController stageController) {
        this.theatre = theatre;
        this.stageController = stageController;
        add(menuButtons, stagesContent);

        seatsAmount.setMax(100);
        seatsAmount.setMin(10);
        seatsAmount.setHasControls(true);

        saveStageButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_SUCCESS);

        stagesGrid.setColumns("id", "name", "seatsAmount");

        refresh();
        addNewStage.addClickListener(event -> {
            stagId.setVisible(false);
            stageName.clear();
            seatsAmount.clear();
            saveStageForm.setVisible(true);
        });

        saveStageButton.addClickListener(event -> {
            if (!stagId.isVisible()) {
                save();
            } else {
                update();
            }

        });

        stagesGrid.asSingleSelect().addValueChangeListener(event -> {
            saveStageForm.setVisible(true);
            stagId.setVisible(true);
            try {
                stagId.setValue(String.valueOf(event.getValue().getId()));
                stageName.setValue(event.getValue().getName());
                seatsAmount.setValue(Double.valueOf(event.getValue().getSeatsAmount()));
            } catch (NullPointerException e) {
            }
        });

        theatre.navigate(backToAdministrationPanel, "adminPanel");
    }

    private void update() {
        StageDto stageDto = new StageDto(Long.valueOf(stagId.getValue()), stageName.getValue(), seatsAmount.getValue().intValue());
        stageController.updateStage(stageDto);
        saveStageForm.setVisible(true);
        refresh();
        Notification.show("Stage " + stageDto.getId() + " has been updated");
    }

    private void save() {
        StageDto stageDto = new StageDto(stageName.getValue(), seatsAmount.getValue().intValue());
        stageController.saveStage(stageDto);
        saveStageForm.setVisible(true);
        refresh();
        Notification.show(stageName.getValue() + " has been created");
    }

    private void setVisibilityOfComponents() {
        saveStageForm.setVisible(false);
        stagId.setVisible(false);
    }

    private void setSizeOfComponents() {
        setSizeFull();
        stagesContent.setSizeFull();
        stagesGrid.setSizeFull();
        saveStageForm.setWidth("400px");
    }

    private void refresh() {
        stagesGrid.setItems(stageController.getStages());
        setVisibilityOfComponents();
        setSizeOfComponents();
    }
}
