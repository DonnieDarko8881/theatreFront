package com.crud.theatre.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StageCopy {
    private long id;
    private String date;
    private String spectacleName;
    private List<SeatsDto> seats;
}
