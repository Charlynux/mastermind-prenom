package com.mastermind.prenom.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.mastermind.prenom.bean.ListePrenomsBean;
import com.mastermind.prenom.bean.ListeResultBean;
import com.mastermind.prenom.bean.ResultBean;
import com.mastermind.prenom.service.RichommeService;
import com.mastermind.prenom.service.ValouService;

public class EntropieMain {
    public static void main(String[] args) throws JAXBException {
        long dateDebut = Calendar.getInstance().getTimeInMillis();
        
        
        JAXBContext jaxbContext = JAXBContext.newInstance(ListeResultBean.class, ListePrenomsBean.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        
        ListePrenomsBean listePrenoms = (ListePrenomsBean) unmarshaller.unmarshal(new File("src/main/resources/listePrenoms.xml"));
        ListePrenomsBean listeReponses = (ListePrenomsBean) unmarshaller.unmarshal(new File("src/main/resources/listePrenoms.xml"));
        
        //ListePrenomsBean listeReponses = (ListePrenomsBean) unmarshaller.unmarshal(new File("src/main/resources/listePrenomsReduite.xml"));
        
        RichommeService richommeService = new RichommeService();
        ValouService valouService = new ValouService();
        
        Map<String, Double> mapEntropies = new HashMap<String, Double>();
        for (String prenomPossible : listePrenoms.getPrenoms()) {
            Map<ResultBean, Integer> mapResults = new HashMap<ResultBean, Integer>();
            for (String prenomTest : listeReponses.getPrenoms()) {
                ResultBean result = richommeService.calculResultat(prenomPossible, prenomTest);
                Integer nbResult = mapResults.get(result);
                if (nbResult == null) {
                    nbResult = Integer.valueOf(0);
                }
                mapResults.put(result, nbResult+1);
            }
            Double entropie = valouService.calculEntropie(new ArrayList<Integer>(mapResults.values()), listeReponses.getPrenoms().size());
            mapEntropies.put(prenomPossible, entropie);
        }
        
        for (Entry<String, Double> resultat : mapEntropies.entrySet()) {
            System.out.println(resultat.getKey() + " : " + resultat.getValue());
        }
        
        long dateFin = Calendar.getInstance().getTimeInMillis();
        System.out.println(dateFin - dateDebut);
    }
}
