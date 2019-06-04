package com.crud.theatre.Front;

import com.crud.theatre.controller.ActorController;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("actors")
@SpringComponent
public class Actors extends VerticalLayout {
    private Grid<ActorDto> actorsGrid = new Grid<>(ActorDto.class);
    private Grid<SpectacleDto> spectacleGrid = new Grid<>(SpectacleDto.class);
    private Button home = new Button(new Icon(VaadinIcon.HOME));
    private HorizontalLayout buttonsMenu = new HorizontalLayout(home);
    private HorizontalLayout mainContent = new HorizontalLayout(actorsGrid, spectacleGrid);


    private ActorController actorController;
    private Theatre theatre;

    @Autowired
    public Actors(ActorController actorController, Theatre theatre) {
        this.actorController = actorController;
        this.theatre = theatre;
        add(buttonsMenu, mainContent);
        setSizeFull();
        mainContent.setSizeFull();
        actorsGrid.setSizeFull();
        spectacleGrid.setSizeFull();


        actorsGrid.setColumns("id", "firstName", "lastName");
        actorsGrid.getColumnByKey("id").setVisible(false);

        spectacleGrid.setColumns("id", "name");
        spectacleGrid.setVisible(false);
        spectacleGrid.getColumnByKey("id").setVisible(false);

        this.theatre.navigate(home, "");

        refresh();

        actorsGrid.asSingleSelect().addValueChangeListener(event -> {
            spectacleGrid.setVisible(true);
            try {
                spectacleGrid.getColumnByKey("name").setHeader("Spectacle which played "
                        + event.getValue().getFirstName() + " "
                        + event.getValue().getLastName() + " in:");
                spectacleGrid.setItems(getSpectacles(event.getValue().getId()));
            }catch (NullPointerException e){

            }
        });
    }

    private void refresh() {
        actorsGrid.setItems(actorController.getActors());
    }

    private List<SpectacleDto> getSpectacles(long actorId) {
        return actorController.getSpectaclesOfAcotr(actorId);
    }
}
