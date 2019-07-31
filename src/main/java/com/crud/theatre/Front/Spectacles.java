package com.crud.theatre.Front;

import com.crud.theatre.controller.SpectacleController;
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
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("spectacles")
@SpringComponent
@UIScope
public class Spectacles extends VerticalLayout {
    private Grid<ActorDto> actorsGrid = new Grid<>(ActorDto.class);
    private Grid<SpectacleDto> spectacleGrid = new Grid<>(SpectacleDto.class);
    private Button home = new Button(new Icon(VaadinIcon.HOME));
    private Button refreshButton = new Button(new Icon(VaadinIcon.REFRESH));
    private HorizontalLayout buttonsMenu = new HorizontalLayout(home, refreshButton);
    private HorizontalLayout mainContent = new HorizontalLayout(spectacleGrid, actorsGrid);

    private Theatre theatre;
    private SpectacleController spectacleController;

    @Autowired
    public Spectacles(Theatre theatre, SpectacleController spectacleController) {
        this.theatre = theatre;
        this.spectacleController = spectacleController;
        add(buttonsMenu, mainContent);
        setSizeFull();
        mainContent.setSizeFull();
        actorsGrid.setSizeFull();
        spectacleGrid.setSizeFull();

        spectacleGrid.setColumns("id", "name");
        spectacleGrid.getColumnByKey("id").setVisible(false);

        actorsGrid.setColumns("id", "firstName", "lastName");
        actorsGrid.getColumnByKey("id").setVisible(false);
        actorsGrid.setVisible(false);

        theatre.navigate(home, "");

        refresh();

        refreshButton.addClickListener(event -> refresh());

        spectacleGrid.asSingleSelect().addValueChangeListener(event -> {
            try {
                actorsGrid.setVisible(true);
                actorsGrid.setItems(getCast(event.getValue().getId()));
            } catch (NullPointerException e) {
            }
        });

    }

    public void refresh() {
        spectacleGrid.setItems(spectacleController.getSpectacles());
        actorsGrid.setVisible(false);
    }

    private List<ActorDto> getCast(long spectacleId) {
        return spectacleController.getCast(spectacleId);
    }
}


