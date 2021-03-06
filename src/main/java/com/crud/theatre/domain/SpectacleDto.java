package com.crud.theatre.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpectacleDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("stageId")
    private Long stageId;

    public SpectacleDto(String name, Long stageId) {
        this.name = name;
        this.stageId = stageId;
    }

    @Override
    public String toString() {
        return name;
    }
}
