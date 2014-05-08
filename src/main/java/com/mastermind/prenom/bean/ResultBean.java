package com.mastermind.prenom.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class ResultBean {

    private int bienPlaces;
    private int malPlaces;

}