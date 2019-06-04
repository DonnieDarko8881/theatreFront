package com.crud.theatre.Front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.net.URISyntaxException;

public class SpectacleDateForm extends FormLayout {
    private Button addDateButton = new Button("Add Date");
    private Button deleteButton = new Button("Delete Date");
    private HorizontalLayout buttons = new HorizontalLayout(addDateButton, deleteButton);
    private TextField date = new TextField("Spectacle Date");
//    Binder<SpectacleDate> binder = new Binder<>(SpectacleDate.class);
    private Repertoire repertoire;



    public SpectacleDateForm(Repertoire repertoire) throws URISyntaxException {
        this.repertoire = repertoire;

        date.setPlaceholder("yyyy-mm-ddThh:mm");
        date.setWidth("100px");
        add(date);
        setWidth("50%");
//        binder.bindInstanceFields(this);

    }



    public Button getDeleteButton() {
        return deleteButton;
    }

    public TextField getDate() {
        return date;
    }

    public HorizontalLayout getButtons() {
        return buttons;
    }

    public Button getAddDateButton() {
        return addDateButton;
    }
}
