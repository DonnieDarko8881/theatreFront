package com.crud.theatre.controller;

import com.crud.theatre.client.TheatreClient;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpectacleController {

    private final TheatreClient theatreClient;

    @Autowired
    public SpectacleController(TheatreClient theatreClient) {
        this.theatreClient = theatreClient;
    }

    public List<SpectacleDto> getSpectacles(){
        return theatreClient.getSpectacleDtoList();
    }

    public List<ActorDto> getCast(long spectacleId){
        return theatreClient.getCast(spectacleId);
    }

    public void saveSpectacle(SpectacleDto spectacleDto){
        theatreClient.saveSpectacle(spectacleDto);
    }

    public void addActorToCast(String spectacleId, String actorId){
        theatreClient.addActorToCast(spectacleId,actorId);
    }

    public void deleteSpectacle(String spectacleId){
        theatreClient.deleteSpectacle(spectacleId);
    }

}

