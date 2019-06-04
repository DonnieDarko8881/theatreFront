package com.crud.theatre.Front;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class CopyStageForm extends FormLayout {
    private TextField dateIdText = new TextField("Date ID");
    private TextField stageIdText = new TextField("Stage ID");
    private Button saveCopyButton = new Button("Add Stage Copy");

    public CopyStageForm() {
//        HorizontalLayout buttons = new HorizontalLayout(saveCopyButton);
        add(dateIdText, stageIdText,saveCopyButton);
    }

    public Button getSaveCopyButton() {
        return saveCopyButton;
    }

    public TextField getDateIdText() {
        return dateIdText;
    }

    public TextField getStageIdText() {
        return stageIdText;
    }
}
