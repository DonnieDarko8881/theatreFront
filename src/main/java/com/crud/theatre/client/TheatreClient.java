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
    public final static String BASE_URL = "https://theatreapp.herokuapp.com/v1/";
//    public final static String BASE_URL = "http://localhost:8080/v1/";
    private RestTemplate restTemplate;

    public TheatreClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<SpectacleDateDto> getSpectacleDateDto() {

        URI url = null;
        try {
            url = new URI(BASE_URL + "dates");
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
            url = new URI(BASE_URL + "spectacles");
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
            url = new URI(BASE_URL + "actors");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, ActorDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public List<StageDto> getStages() {
        URI url = null;
        try {
            url = new URI(BASE_URL + "stages");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, StageDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public UserDto getUserByMail(String mail) {
        URI url = getUriUserByMail(mail);
        return restTemplate.getForObject(url, UserDto.class);
    }

    private URI getUriUserByMail(String mail) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("users/findBy/")
                .path(mail)
                .build().encode().toUri();
    }

    public Success loginSuccess(String mail, String password) {
        URI url = getUriLoginSuccess(mail, password);
        return restTemplate.getForObject(url, Success.class);
    }


    private URI getUriLoginSuccess(String mail, String password) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("users/")
                .path(mail)
                .path("/")
                .path(password)
                .build().encode().toUri();
    }

    public Success mailExist(String mail) {
        URI url = getUriMailExist(mail);
        return restTemplate.getForObject(url, Success.class);
    }

    private URI getUriMailExist(String mail) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("users/")
                .path(mail)
                .build().encode().toUri();
    }

    public List<UserDto> getUsers() {
        URI url = null;
        try {
            url = new URI(BASE_URL + "users");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, UserDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public List<ReservationDto> getReservations() {
        URI url = null;
        try {
            url = new URI(BASE_URL + "reservations");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, ReservationDto[].class)).
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
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("actors/")
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
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("spectacles/")
                .path(spectacleIdString)
                .path("/cast")
                .build().encode().toUri();
    }

    public List<StageCopyDto> getStageCopiesDtoList() {
        URI url = null;
        try {
            url = new URI(BASE_URL + "stageCopies");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ofNullable(restTemplate.getForObject(url, StageCopyDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

    public void changeStatus(String stageCopyId, String seatsId, String status) {
        URI url = getUriChangeStatus(stageCopyId, seatsId, status);
        restTemplate.put(url, null);
    }

    private URI getUriChangeStatus(String stageCopyId, String seatsId, String status) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("stageCopies/")
                .path(stageCopyId)
                .path("/seats/")
                .path(seatsId)
                .path("/status/")
                .path(status)
                .build().encode().toUri();
    }

    public void saveStage(StageDto stageDto) {
        String url = BASE_URL + "stages";
        HttpEntity<StageDto> request = new HttpEntity<>(stageDto);
        restTemplate.postForEntity(url, request, StageDto.class);
    }

    public void updateStage(StageDto stageDto) {
        String url = BASE_URL + "stages";
        HttpEntity<StageDto> request = new HttpEntity<>(stageDto);
        restTemplate.put(url, request, StageDto.class);
    }

    public void saveReservation(ReservationDto reservationDto) {
        String url = BASE_URL + "reservations";
        HttpEntity<ReservationDto> request = new HttpEntity<>(reservationDto);
        restTemplate.postForEntity(url, request, ReservationDto.class);
    }

    public void saveUser(UserDto userDto) {
        String url = BASE_URL + "users";
        HttpEntity<UserDto> request = new HttpEntity<>(userDto);
        restTemplate.postForEntity(url, request, UserDto.class);
    }

    public void saveActor(ActorDto actorDto) {
        String url = BASE_URL + "actors";
        HttpEntity<ActorDto> request = new HttpEntity<>(actorDto);
        restTemplate.postForEntity(url, request, ActorDto.class);
    }

    public void saveSpectacle(SpectacleDto spectacleDto) {
        String url = BASE_URL + "spectacles";
        HttpEntity<SpectacleDto> request = new HttpEntity<>(spectacleDto);
        restTemplate.postForEntity(url, request, SpectacleDto.class);
    }


    public void saveSpectacleDate(long spectacleId, String spectacleDate) {
        URI uri = getUriSaveSpectacleDate(spectacleId);
        SpectacleDateDto spectacleDateDto = new SpectacleDateDto(LocalDateTime.parse(spectacleDate));
        HttpEntity<SpectacleDateDto> request = new HttpEntity<>(spectacleDateDto);
        restTemplate.postForEntity(uri, request, SpectacleDateDto.class);
    }

    private URI getUriSaveSpectacleDate(long spectacleId) {
        String spectacleIdString = String.valueOf(spectacleId);
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("spectacles/")
                .path(spectacleIdString)
                .path("/dates")
                .build().encode().toUri();
    }

    public void deleteSpectacleDate(String dateId) {
        URI uri = getUriDeleteSpectacleDate(dateId);
        restTemplate.delete(uri);
    }

    private URI getUriDeleteSpectacleDate(String dateId) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("dates/")
                .path(dateId)
                .build().encode().toUri();
    }

    public void deleteActor(String actorId) {
        URI uri = getUriDeleteActor(actorId);
        restTemplate.delete(uri);
    }

    private URI getUriDeleteActor(String actorId) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("actors/")
                .path(actorId)
                .build().encode().toUri();
    }

    public void deleteSpectacle(String spectacleId) {
        URI uri = getUriDeleteSpectacle(spectacleId);
        restTemplate.delete(uri);
    }

    private URI getUriDeleteSpectacle(String spectacleId) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("spectacles/")
                .path(spectacleId)
                .build().encode().toUri();
    }


    public void saveStageCopy(String stageId, String dateId, String spectaclePrice) {
        URI uri = getUriSaveStageCopy(stageId, dateId, spectaclePrice);
        restTemplate.postForEntity(uri, null, StageCopyDto.class);

    }

    private URI getUriSaveStageCopy(String stageId, String dateId, String spectaclePrice) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("stageCopies/")
                .path(stageId)
                .path("/dates/")
                .path(dateId)
                .path("/price/")
                .path(spectaclePrice)
                .build().encode().toUri();
    }

    public void addActorToCast(String spectacleId, String actorId) {
        URI uri = getUriAddActorToCast(spectacleId, actorId);
        restTemplate.put(uri, null);
    }

    private URI getUriAddActorToCast(String spectacleId, String actorId) {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path("spectacles/")
                .path(spectacleId)
                .path("/actors/")
                .path(actorId)
                .build().encode().toUri();
    }
}

