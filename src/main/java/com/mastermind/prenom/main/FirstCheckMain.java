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

public class FirstCheckMain {
	public static void main(String[] args) throws JAXBException {
		long dateDebut = Calendar.getInstance().getTimeInMillis();
		
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ListeResultBean.class, ListePrenomsBean.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		
		ListeResultBean listeResultats = (ListeResultBean) unmarshaller.unmarshal(new File("src/main/resources/resultats.xml"));
		List<PrenomResultBean> resultats = listeResultats.getResultats();
		
		ListePrenomsBean listePrenoms = (ListePrenomsBean) unmarshaller.unmarshal(new File("src/main/resources/listePrenoms.xml"));
		
		RichommeService service = new RichommeService();
		
		List<String> prenomsValides = new ArrayList<String>();
		for (final String prenom : listePrenoms.getPrenoms()){
			String prenomMaj = StringUtils.upperCase(prenom);
			boolean valide = true;
			for (PrenomResultBean resultat : resultats) {
				String prenomResultat = resultat.getPrenom();
				if (service.calculateSamePlaceLetters(prenomMaj, prenomResultat) != resultat.getBienPlaces()
						|| service.calculateCommonLetters(prenomMaj, prenomResultat) != resultat.getMalPlaces()) {
					valide = false;
				}
			}
			if (valide) {
				prenomsValides.add(prenom);
			}
			
		}
		
		ListePrenomsBean listePrenomsReduite = new ListePrenomsBean();
		listePrenomsReduite.setPrenoms(prenomsValides);
		
		final Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.marshal(listePrenomsReduite, new File("src/main/resources/listePrenomsReduite.xml"));
	
		long dateFin = Calendar.getInstance().getTimeInMillis();
		System.out.println(dateFin - dateDebut);
	}
}
