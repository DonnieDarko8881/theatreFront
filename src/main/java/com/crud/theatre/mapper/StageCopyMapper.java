package com.crud.theatre.mapper;

import com.crud.theatre.domain.StageCopy;
import com.crud.theatre.domain.StageCopyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StageCopyMapper {

    public List<StageCopy> mapToStageCopyList(List<StageCopyDto> stageCopyDtoList) {
        return stageCopyDtoList.stream()
                .map(copyDto -> new StageCopy(
                        copyDto.getId(),
                        copyDto.getSpectacleDateDto().getDate().toString(),
                        copyDto.getSpectacleDateDto().getSpectacleDto().getName(),
                        copyDto.getSeats(),
                        copyDto.getSpectaclePricePLN()
                ))
                .collect(Collectors.toList());
    }
}
