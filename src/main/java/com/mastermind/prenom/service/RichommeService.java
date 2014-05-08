package com.mastermind.prenom.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.mastermind.prenom.bean.ResultBean;

public class RichommeService {

	public int calculateCommonLetters(final String premierMot, final String secondMot) {
		int count=0;
		Set<Character> checkedCharacters = new HashSet<Character>();
		for (int i=0; i < premierMot.length(); i++) {
			char character = premierMot.charAt(i);
			if (!checkedCharacters.contains(character)
					&& secondMot.indexOf(character) >= 0) {
				count++;
			}
			checkedCharacters.add(character);
		}
		
		return count;
	}
	
	public int calculateSamePlaceLetters(final String premierMot, final String secondMot) {
		int count=0;
		int maxSize = Math.max(premierMot.length(), secondMot.length());
		for (int i=0; i < maxSize; i++) {
			if (premierMot.charAt(i) == secondMot.charAt(i)) {
				count++;
			}
		}
		return count;
	}
	
	public ResultBean calculResultat(final String motATester, final String motRepere) {

        final Map<Character, Integer> characters = new HashMap<Character, Integer>();
        int bienPlaces = 0;
        for (int i = 0; i < motRepere.length(); i++) {
            if (motATester.charAt(i) == motRepere.charAt(i)) {
                bienPlaces++;
            } else {
                Integer nbChar = characters.get(motRepere.charAt(i));
                if (nbChar == null) {
                    nbChar = Integer.valueOf(0);
                }
                characters.put(motRepere.charAt(i), nbChar + 1);
            }
        }

        int malPlaces = 0;
        for (int i = 0; i < motRepere.length(); i++) {
            if (motATester.charAt(i) != motRepere.charAt(i) && characters.keySet().contains(motATester.charAt(i))) {
                final Integer nbChar = characters.get(motATester.charAt(i));
                if (Integer.valueOf(1).equals(nbChar)) {
                    characters.remove(motATester.charAt(i));
                } else {
                    characters.put(motATester.charAt(i), nbChar - 1);
                }
                malPlaces++;
            }
        }

        final ResultBean resultBean = new ResultBean();
        resultBean.setBienPlaces(bienPlaces);
        resultBean.setMalPlaces(malPlaces);
        return resultBean;
    }
	
}
