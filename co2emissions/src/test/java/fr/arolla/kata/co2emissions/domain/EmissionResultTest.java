package fr.arolla.kata.co2emissions.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class EmissionResultTest {

	@Test
	public void testEquals_Error() throws Exception {
		assertEquals(new EmissionResult("No parameter given for computation"), new EmissionResult("No parameter given for computation"));
	}
	
	@Test
	public void testEquals_Success() throws Exception {
		assertEquals(new EmissionResult(12,"No parameter given for computation"), new EmissionResult(12, "No parameter given for computation"));
	}
}
