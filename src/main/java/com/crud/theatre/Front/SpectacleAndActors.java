package com.crud.theatre.Front;

import com.crud.theatre.controller.ActorController;
import com.crud.theatre.controller.SpectacleController;
import com.crud.theatre.controller.StageController;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.domain.StageDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Route("spectacles&actors")
@SpringComponent
public class SpectacleAndActors extends VerticalLayout {
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField spectacleName = new TextField("Spectacle Name");
    private TextField spectacleIdText = new TextField("Spectacle ID");
    private TextField actorIdText = new TextField("Actor Id");
    private TextField spectacleIdToRemoveText = new TextField("Spectacle ID");
    private ComboBox<ActorDto> actorsComboBox = new ComboBox<>("Actors");
    private ComboBox<StageDto> stagesComboBox = new ComboBox<>("Stages");
    private Button deleteSpectacleButton = new Button("Delete");
    private Button deleteActorButton = new Button("Delete");
    private Button addToCastButton = new Button("Add Actor To Cast");
    private Button saveSpectacleButton = new Button("Save");
    private Button saveActorButton = new Button("Save");
    private Button backToAdministrationPanel = new Button(new Icon(VaadinIcon.BACKSPACE));
    private Button addNewActorButton = new Button("Add New Actor");
    private Button addNewSpectacleButton = new Button("Add New Spectacle");
    private Button addActorToCastButton = new Button("Add Actor To Cast");
    private Grid<ActorDto> actorsGrid = new Grid<>(ActorDto.class);
    private Grid<SpectacleDto> spectaclesOfActorGrid = new Grid<>(SpectacleDto.class);
    private Grid<SpectacleDto> spectaclesGrid = new Grid<>(SpectacleDto.class);
    private Grid<ActorDto> castGrid = new Grid<>(ActorDto.class);
    private FormLayout saveActorForm = new FormLayout(firstName, lastName, saveActorButton);
    private FormLayout saveSpectacleForm = new FormLayout(spectacleName, stagesComboBox, saveSpectacleButton);
    private FormLayout addActorToCastForm = new FormLayout(spectacleIdText, actorsComboBox, addToCastButton);
    private FormLayout deleteActorForm = new FormLayout(actorIdText, deleteActorButton);
    private FormLayout deleteSpectacleForm  = new FormLayout(spectacleIdToRemoveText,deleteSpectacleButton);
    private HorizontalLayout buttonsMenu = new HorizontalLayout(backToAdministrationPanel, addNewActorButton,
            addNewSpectacleButton, addActorToCastButton);
    private HorizontalLayout actorsContent = new HorizontalLayout(actorsGrid, spectaclesOfActorGrid);
    private HorizontalLayout spectaclesContent = new HorizontalLayout(spectaclesGrid, castGrid);
    private VerticalLayout allGridsContent = new VerticalLayout(actorsContent, spectaclesContent);
    private VerticalLayout allFormesContent = new VerticalLayout(saveActorForm, saveSpectacleForm, addActorToCastForm,
            deleteActorForm, deleteSpectacleForm);
    private HorizontalLayout mainContent = new HorizontalLayout(allGridsContent, allFormesContent);

    private ActorController actorController;
    private SpectacleController spectacleController;
    private StageController stageController;
    private Theatre theatre;
    private Spectacles spectacles;
    private Actors actors;

    @Autowired
    public SpectacleAndActors(ActorController actorController, SpectacleController spectacleController, StageController stageController, Theatre theatre, Spectacles spectacles, Actors actors) {
        this.actorController = actorController;
        this.spectacleController = spectacleController;
        this.stageController = stageController;
        this.theatre = theatre;
        this.spectacles = spectacles;
        this.actors = actors;
        add(buttonsMenu, mainContent);
        setSizeOfContents();
        setVisibilityComponents();

        setProperitesOfButtons();

        saveActorButton.addClickListener(event -> saveActor());
        saveSpectacleButton.addClickListener(event -> saveSpectacle());
        addToCastButton.addClickListener(event -> addActorToCast());
        deleteActorButton.addClickListener(event -> deleteActor());
        deleteSpectacleButton.addClickListener(event -> deleteSpectacle());


        setAddClickListinerButtonMenu();

        theatre.navigate(backToAdministrationPanel, "adminPanel");
        setGridsColumns();
        refresh();
        gridsAsSingeSelect();

    }

    private void deleteSpectacle() {
        spectacleController.deleteSpectacle(spectacleIdToRemoveText.getValue());
        refresh();
    }

    private void deleteActor() {
        actorController.deleteActor(actorIdText.getValue());
        refresh();
    }

    private void addActorToCast() {
        String spectacleId = spectacleIdText.getValue();
        String actorId = String.valueOf(actorsComboBox.getValue().getId());
        spectacleController.addActorToCast(spectacleId, actorId);
        refresh();
    }

    private void saveSpectacle() {
        SpectacleDto spectacleDto = new SpectacleDto(spectacleName.getValue(), stagesComboBox.getValue().getId());
        spectacleController.saveSpectacle(spectacleDto);
        refresh();
    }

    private void saveActor() {
        ActorDto actorDto = new ActorDto(firstName.getValue(), lastName.getValue());
        actorController.saveActor(actorDto);
        refresh();
    }

    private void setProperitesOfButtons() {
        saveActorButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        saveSpectacleButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        addToCastButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        deleteActorButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);
        deleteSpectacleButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);

        spectacleIdToRemoveText.setReadOnly(true);
        actorIdText.setReadOnly(true);
    }

    private void setAddClickListinerButtonMenu() {
        addNewActorButton.addClickListener(event -> {
            refresh();
            setVisibilityAfterClickinOnMenuButton(allFormesContent, saveActorForm, saveSpectacleForm, addActorToCastForm);
            firstName.focus();
        });

        addNewSpectacleButton.addClickListener(event -> {
            refresh();
            setVisibilityAfterClickinOnMenuButton(allFormesContent, saveSpectacleForm, saveActorForm, addActorToCastForm);
            spectacleName.focus();
        });

        addActorToCastButton.addClickListener(event -> {
            setVisibilityAfterClickinOnMenuButton(allFormesContent, addActorToCastForm, saveActorForm, saveSpectacleForm);
            actorsContent.setVisible(false);
            spectaclesContent.setVisible(true);
            deleteActorForm.setVisible(false);
            deleteActorForm.setVisible(false);
        });
    }

    private void setVisibilityAfterClickinOnMenuButton(VerticalLayout allFormesContent, FormLayout formToAppear, FormLayout formToVanish, FormLayout formToVanish1) {
        allFormesContent.setVisible(true);
        formToAppear.setVisible(true);
        formToVanish.setVisible(false);
        formToVanish1.setVisible(false);
    }

    private void setGridsColumns() {
        actorsGrid.setColumns("id", "firstName", "lastName");
        spectaclesOfActorGrid.setColumns("id", "name");
        spectaclesGrid.setColumns("id", "name");
        castGrid.setColumns("id", "firstName", "lastName");
    }

    private void gridsAsSingeSelect() {
        actorsGrid.asSingleSelect().addValueChangeListener(event -> {
            try {
                Long actorId = event.getValue().getId();
                if (getSpectaclesOfActor(actorId).isEmpty()) {
                    allFormesContent.setVisible(true);
                    deleteActorForm.setVisible(true);
                    spectaclesContent.setVisible(false);
                    actorIdText.setValue(String.valueOf(actorId));
                } else {
                    setVisibilityComponents();
                }
                spectaclesOfActorGrid.setItems(getSpectaclesOfActor(actorId));
            } catch (NullPointerException e) {
            }
        });

        spectaclesGrid.asSingleSelect().addValueChangeListener(event -> {
            try {
                Long spectacleId = event.getValue().getId();
                if (getCast(spectacleId).isEmpty()) {
                    allFormesContent.setVisible(true);
                    deleteSpectacleForm.setVisible(true);
                    actorsContent.setVisible(false);
                    spectacleIdToRemoveText.setValue(String.valueOf(spectacleId));
                } else {
                    setVisibilityComponents();
                }
                castGrid.setItems(getCast(spectacleId));
                spectacleIdText.setValue(String.valueOf(spectacleId));
            } catch (NullPointerException e) {
            }
        });
    }

    private List<SpectacleDto> getSpectaclesOfActor(long actorId) {
        return actorController.getSpectaclesOfAcotr(actorId);
    }

    private List<ActorDto> getCast(long spectacleId) {
        return spectacleController.getCast(spectacleId);
    }

    private void refresh() {
        actorsGrid.setItems(actorController.getActors());
        spectaclesGrid.setItems(spectacleController.getSpectacles());
        stagesComboBox.setItems(stageController.getStages());
        actorsComboBox.setItems(actorController.getActors());
        setVisibilityComponents();
        spectacles.refresh();
        actors.refresh();
    }

    private void setSizeOfContents() {
        setSizeFull();
        mainContent.setSizeFull();
        allGridsContent.setSizeFull();
        actorsContent.setSizeFull();
        spectaclesContent.setSizeFull();
        actorsGrid.setSizeFull();
        castGrid.setSizeFull();
        spectaclesGrid.setSizeFull();
        spectaclesOfActorGrid.setSizeFull();
        allFormesContent.setWidth("400px");
    }

    private void setVisibilityComponents() {
        actorsContent.setVisible(true);
        spectaclesContent.setVisible(true);
        allFormesContent.setVisible(false);
        saveActorForm.setVisible(false);
        saveSpectacleForm.setVisible(false);
        addActorToCastForm.setVisible(false);
        deleteActorForm.setVisible(false);
        deleteSpectacleForm.setVisible(false);
    }
}
