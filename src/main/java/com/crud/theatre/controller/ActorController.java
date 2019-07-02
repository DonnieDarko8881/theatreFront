package com.crud.theatre.controller;

import com.crud.theatre.client.TheatreClient;
import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActorController {
    private final TheatreClient theatreClient;

    @Autowired
    public ActorController(TheatreClient theatreClient) {
        this.theatreClient = theatreClient;
    }

    public List<SpectacleDto> getSpectaclesOfAcotr(long actorId) {
        return theatreClient.getSpectaclesOfActor(actorId);
    }

    public List<ActorDto> getActors() {
        return theatreClient.getActorsDtoList();
    }

    public void saveActor(ActorDto actorDto) {
        theatreClient.saveActor(actorDto);
    }

    public void deleteActor(String actorId) {
        theatreClient.deleteActor(actorId);
    }
}
