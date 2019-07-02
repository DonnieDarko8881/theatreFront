package com.crud.theatre.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpectacleDateDto {
    @JsonProperty("id")
    private long id;
    @JsonProperty("date")
    private LocalDateTime date;
    @JsonProperty("spectacleDto")
    private SpectacleDto spectacleDto;
    @JsonProperty("stageDto")
    private StageDto stageDto;
    @JsonProperty("stageCopyDto")
    private StageCopyDto stageCopyDto;

    public SpectacleDateDto(LocalDateTime date) {
        this.date = date;
    }
}
