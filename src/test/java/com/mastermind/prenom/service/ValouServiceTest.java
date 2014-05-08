package com.mastermind.prenom.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class ValouServiceTest {

    @Test
    public void testCalculEntropie() throws Exception {
        List<Integer> result = new ArrayList<>();
        result.add(Integer.valueOf(625));
        result.add(Integer.valueOf(500));
        result.add(Integer.valueOf(150));
        result.add(Integer.valueOf(20));
        result.add(Integer.valueOf(1));
        
        ValouService service = new ValouService();
        System.out.println(service.calculEntropie(result, Integer.valueOf(1296)));
    }

}
