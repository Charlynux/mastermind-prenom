package com.mastermind.prenom.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListeResultBean {
	
	@XmlElement(name="resultat")
	private List<PrenomResultBean> resultats;
	
	public void setResultat(List<PrenomResultBean> resultats) {
		this.resultats = resultats;
	}
	
	public List<PrenomResultBean> getResultats() {
		return resultats;
	}
}
