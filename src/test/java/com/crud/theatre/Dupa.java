package com.crud.theatre;

import com.crud.theatre.domain.ActorDto;
import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.SpectacleDateDto;
import com.crud.theatre.domain.StageCopyDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;


@RunWith(SpringRunner.class)
@SpringBootTest
public class Dupa {


    @Test
    public void postStageCopy(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity("http://localhost:8080/v1/stageCopy/283/dates/328", null, StageCopyDto.class);
    }

    @Test
    public void postDate() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String spectacleId = String.valueOf(287);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1")
                .path("/spectacles/")
                .path(spectacleId)
                .path("/dates")
                .build().encode().toUri();
        System.out.println(uri);


        SpectacleDateDto spectacleDateDto = new SpectacleDateDto(LocalDateTime.parse("2015-03-10T12:30"));
        HttpEntity<SpectacleDateDto> request = new HttpEntity<>(spectacleDateDto);
        restTemplate.postForEntity(uri,request,SpectacleDateDto.class);

    }

    @Test
    public void testowaniePost() {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ActorDto> request = new HttpEntity<>(new ActorDto("Szymon Test", "Medykowski Test"));
        ActorDto actorDto = restTemplate.postForObject("http://localhost:8080/v1/actors", request, ActorDto.class);

        Assert.assertEquals("Szymon Test", actorDto.getFirstName());

    }

    @Test
    public void testowanieGetSpectacleDtos() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8080/v1/dates");

        List<SpectacleDateDto> dates = ofNullable(restTemplate.getForObject(url, SpectacleDateDto[].class)).
                map(Arrays::asList)
                .orElseGet(ArrayList::new);

        dates.stream().forEach(spectacleDateDto -> System.out.println(spectacleDateDto.getId()));

    }

}
