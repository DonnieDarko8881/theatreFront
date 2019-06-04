package com.crud.theatre.client;

import com.crud.theatre.domain.*;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class TheatreClient {
    private RestTemplate restTemplate;


    public TheatreClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<SpectacleDateDto> getSpectacleDateDto() {

        URI url = null;
        try {
            url = new URI("http://localhost:8080/v1/dates");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return ofNullable(restTemplate.getForObject(url, SpectacleDateDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public List<SpectacleDto> getSpectacleDtoList() {
        URI url = null;
        try {
            url = new URI("http://localhost:8080/v1/spectacles");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, SpectacleDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public List<ActorDto> getActorsDtoList() {
        URI url = null;
        try {
            url = new URI("http://localhost:8080/v1/actors");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, ActorDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public List<SpectacleDto> getSpectaclesOfActor(long actorId) {
        URI url = getUriSpectaclesOfActors(actorId);
        return ofNullable(restTemplate.getForObject(url, SpectacleDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    private URI getUriSpectaclesOfActors(long actorId) {
        String actorIdString = String.valueOf(actorId);
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/actors/")
                .path(actorIdString)
                .path("/spectacles")
                .build().encode().toUri();
    }

    public List<ActorDto> getCast(long spectacleId) {
        URI url = getUriCast(spectacleId);
        return ofNullable(restTemplate.getForObject(url, ActorDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    private URI getUriCast(long spectacleId) {
        String spectacleIdString = String.valueOf(spectacleId);
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/spectacles/")
                .path(spectacleIdString)
                .path("/cast")
                .build().encode().toUri();
    }

    public List<StageCopyDto> getStageCopiesDtoList() {
        URI url = null;
        try {
            url = new URI("http://localhost:8080/v1/stageCopies");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, StageCopyDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public void changeStatus(String stageCopyId, String seatsId, String status) {
        URI url = getUriChangeStatus(stageCopyId,seatsId,status);
        restTemplate.put(url,null);
    }

    private URI getUriChangeStatus(String stageCopyId, String seatsId, String status) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/stageCopies/")
                .path(stageCopyId)
                .path("/seats/")
                .path(seatsId)
                .path("/status/")
                .path(status)
                .build().encode().toUri();
    }

    public void saveReservation(ReservationDto reservationDto) {
        String url = "http://localhost:8080/v1/reservations";
        HttpEntity<ReservationDto> request = new HttpEntity<>(reservationDto);
        restTemplate.postForEntity(url, request, ReservationDto.class);
    }


    public void saveSpectacleDate(long spectacleId, String spectacleDate) {
        URI uri = getUriSaveSpectacleDate(spectacleId);
        SpectacleDateDto spectacleDateDto = new SpectacleDateDto(LocalDateTime.parse(spectacleDate));
        HttpEntity<SpectacleDateDto> request = new HttpEntity<>(spectacleDateDto);
        restTemplate.postForEntity(uri, request, SpectacleDateDto.class);
    }

    private URI getUriSaveSpectacleDate(long spectacleId) {
        String spectacleIdString = String.valueOf(spectacleId);
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/spectacles/")
                .path(spectacleIdString)
                .path("/dates")
                .build().encode().toUri();
    }

    public void deleteSpectacleDate(String dateId) {
        URI uri = getUriDeleteSpectacleDate(dateId);
        restTemplate.delete(uri);
    }

    private URI getUriDeleteSpectacleDate(String dateId) {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/dates/")
                .path(dateId)
                .build().encode().toUri();
    }

    public void saveStageCopy(String stageId, String dateId) {
        URI uri = getUriSaveStageCopy(stageId, dateId);
        restTemplate.postForEntity(uri, null, StageCopyDto.class);

    }

    private URI getUriSaveStageCopy(String stageId, String dateId){
        return UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/stageCopies/")
                .path(stageId)
                .path("/dates/")
                .path(dateId)
                .build().encode().toUri();
    }
}
