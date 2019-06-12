package com.crud.theatre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StageDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("seatsAmount")
    private int seatsAmount;

    public StageDto(String name, int seatsAmount) {
        this.name = name;
        this.seatsAmount = seatsAmount;
    }

    @Override
    public String toString() {
        return name;
    }
}
