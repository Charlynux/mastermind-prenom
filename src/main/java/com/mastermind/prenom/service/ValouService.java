package com.mastermind.prenom.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

public class ValouService {

    
    public Double calculEntropie(final List<Integer> results, final Integer nbPossibilites) {
        BigDecimal entropie = new BigDecimal(0);
        
        MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
        BigDecimal nbPossBigDecimal = new BigDecimal(nbPossibilites);
        for (Integer result : results) {
            BigDecimal proba = new BigDecimal(result);
            proba = proba.divide(nbPossBigDecimal, mathContext);
            BigDecimal logProba = new BigDecimal(Math.log(proba.doubleValue()));
            
            
            entropie = entropie.add(proba.multiply(logProba));
        }
        
        
        
        return -entropie.doubleValue();
    }


} 
