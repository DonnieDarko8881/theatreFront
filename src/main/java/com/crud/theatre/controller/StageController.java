package com.crud.theatre.controller;

import com.crud.theatre.client.TheatreClient;
import com.crud.theatre.domain.StageDto;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StageController {

    private final TheatreClient theatreClient;

    @Autowired
    public StageController(TheatreClient theatreClient) {
        this.theatreClient = theatreClient;
    }

    public List<StageDto> getStages(){
        return theatreClient.getStages();
    }

    public void saveStage(StageDto stageDto){
        theatreClient.saveStage(stageDto);
    }

    public void updateStage(StageDto stageDto){
        theatreClient.updateStage(stageDto);
    }
}
