package com.mastermind.prenom.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;

import com.mastermind.prenom.bean.ListePrenomsBean;
import com.mastermind.prenom.bean.ListeResultBean;
import com.mastermind.prenom.bean.PrenomResultBean;
import com.mastermind.prenom.service.RichommeService;

public class PropositionTestMain {
    public static void main(String[] args) throws JAXBException {
        long dateDebut = Calendar.getInstance().getTimeInMillis();
        
        
        JAXBContext jaxbContext = JAXBContext.newInstance(ListeResultBean.class, ListePrenomsBean.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        
        ListeResultBean listeResultats = (ListeResultBean) unmarshaller.unmarshal(new File("src/main/resources/resultats.xml"));
        
        final ListePrenomsBean listePrenoms = (ListePrenomsBean) unmarshaller.unmarshal(new File("src/main/resources/listePrenoms.xml"));
        
        RichommeService service = new RichommeService();
        
        final List<String> prenomsRestants = new ArrayList<String>(listePrenoms.getPrenoms());
        System.out.println("Taille initiale : " + prenomsRestants.size());
        for (PrenomResultBean resultat : listeResultats.getResultats()) {
            final List<String> prenomsATraiter = new ArrayList<String>(prenomsRestants);
            String prenomResultat = resultat.getPrenom();
            for (final String prenom : prenomsATraiter){
                String prenomMaj = StringUtils.upperCase(prenom);
                if (service.calculateSamePlaceLetters(prenomMaj, prenomResultat) != resultat.getBienPlaces()
                        || service.calculateCommonLetters(prenomMaj, prenomResultat) != resultat.getMalPlaces()) {
                    prenomsRestants.remove(prenom);
                }
            }
            System.out.println(prenomResultat + " : " + prenomsRestants.size());
        }
        
        long dateFin = Calendar.getInstance().getTimeInMillis();
        System.out.println(dateFin - dateDebut);
    }
}
