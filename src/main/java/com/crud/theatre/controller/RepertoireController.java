package com.crud.theatre.controller;

import com.crud.theatre.client.TheatreClient;
import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.SpectacleDto;
import com.crud.theatre.mapper.SpectacleDateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;

@Service
public class RepertoireController {

    private final TheatreClient theatreClient;
    private final SpectacleDateMapper dateMapper;

    @Autowired
    public RepertoireController(TheatreClient theatreClient, SpectacleDateMapper dateMapper) {
        this.theatreClient = theatreClient;
        this.dateMapper = dateMapper;
    }

    public List<SpectacleDate> getDates()  {
        return dateMapper.mapToSpectacleDates(theatreClient.getSpectacleDateDto());
    }

    public List<SpectacleDto> getSpectacles(){
        return theatreClient.getSpectacleDtoList();
    }

    public void saveSpectacleDate(long spectacleId, String spectacleDate){
        theatreClient.saveSpectacleDate(spectacleId,spectacleDate);
    }

    public void deleteSpectacleDate(String dateId){
        theatreClient.deleteSpectacleDate(dateId);
    }

    public void saveStageCopy(String stageId, String dateId, String spectaclePrice){
        theatreClient.saveStageCopy(stageId,dateId,spectaclePrice);
    }
}
