package com.crud.theatre.mapper;

import com.crud.theatre.domain.SpectacleDate;
import com.crud.theatre.domain.SpectacleDateDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpectacleDateMapper {

    public List<SpectacleDate> mapToSpectacleDates(final List<SpectacleDateDto> spectacleDateDtos) {
        return spectacleDateDtos.stream()
                .map(dateDto -> SpectacleDate.builder()
                        .id(dateDto.getId())
                        .date(dateDto.getDate().toString())
                        .spectacleId(dateDto.getSpectacleDto().getId())
                        .spectacleName(dateDto.getSpectacleDto().getName())
                        .stageId(dateDto.getStageDto().getId())
                        .stageName(dateDto.getStageDto().getName())
                        .stageCopy(dateDto.getStageCopyDto())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
