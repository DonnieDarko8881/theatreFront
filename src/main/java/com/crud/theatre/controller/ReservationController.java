package com.crud.theatre.controller;

import com.crud.theatre.client.TheatreClient;
import com.crud.theatre.domain.ReservationDto;
import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.mapper.StageCopyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationController {

    private final TheatreClient theatreClient;
    private final StageCopyMapper mapper;

    @Autowired
    public ReservationController(TheatreClient theatreClient, StageCopyMapper mapper) {
        this.theatreClient = theatreClient;
        this.mapper = mapper;
    }

    public List<StageCopy> getStageCopies() {
        return mapper.mapToStageCopyList(theatreClient.getStageCopiesDtoList());
    }

    public void saveReservation(ReservationDto reservationDto) {
        theatreClient.saveReservation(reservationDto);
    }

    public void changeStatus(String stageCopyId, String seatsId, String status) {
        theatreClient.changeStatus(stageCopyId, seatsId, status);
    }

    public List<ReservationDto> gerReservations() {
        return theatreClient.getReservations();
    }
}
