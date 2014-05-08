package com.mastermind.prenom.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.mastermind.prenom.bean.ResultBean;


public class RichommeServiceTest {

    private final RichommeService service = new RichommeService();
    
	@Test
	public void testCalculateCommonLettersCas1() throws Exception {
		int result = service.calculateCommonLetters("VICTOR", "DAMIEN");
		assertEquals(1, result);
	}
	
	@Test
	public void testCalculateCommonLettersCasSimilaire() throws Exception {
		int result = service.calculateCommonLetters("DAMIEN", "DAMIEN");
		assertEquals(6, result);
	}
	
	@Test
	public void testCalculateCommonLettersCasZero() throws Exception {
		int result = service.calculateCommonLetters("TOTOTO", "DAMIEN");
		assertEquals(0, result);
	}
	
	@Test
	public void testCalculateCommonLettersCasJerome() throws Exception {
		int result = service.calculateCommonLetters("JEROME", "DAMIEN");
		assertEquals(2, result);
	}

	@Test
	public void testCalculateSamePlaceLettersCasZero() throws Exception {
		int result = service.calculateSamePlaceLetters("JEROME", "DAMIEN");
		assertEquals(0, result);
	}

	@Test
	public void testCalculateSamePlaceLettersCasEqual() throws Exception {
		int result = service.calculateSamePlaceLetters("DAMIEN", "DAMIEN");
		assertEquals(6, result);
	}
	
	@Test
	public void testCalculateSamePlaceLettersCas3() throws Exception {
		int result = service.calculateSamePlaceLetters("DOMSIN", "DAMIEN");
		assertEquals(3, result);
	}
	
	@Test
    public void testCalculResultatCas1() {
        final ResultBean result = service.calculResultat("ABC", "AXZ");
        assertNotNull(result);
        assertEquals(1, result.getBienPlaces());
        assertEquals(0, result.getMalPlaces());

    }

    @Test
    public void testCalculResultatCas2() {
        final ResultBean result = service.calculResultat("ABC", "ABZ");
        assertNotNull(result);
        assertEquals(2, result.getBienPlaces());
        assertEquals(0, result.getMalPlaces());

    }

    @Test
    public void testCalculResultatCas3() {
        final ResultBean result = service.calculResultat("ZAC", "ABC");
        assertNotNull(result);
        assertEquals(1, result.getBienPlaces());
        assertEquals(1, result.getMalPlaces());

    }

    @Test
    public void testCalculResultatCas4() {
        final ResultBean result = service.calculResultat("AAA", "ABC");
        assertNotNull(result);
        assertEquals(1, result.getBienPlaces());
        assertEquals(0, result.getMalPlaces());

    }
}
