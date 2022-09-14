package org.sebastiandine.cardcollectionmanager.services;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class MtgApiClientTest {
	
	@Test
	public void testGetAllSets(){
		assertThat(MtgApiClient.getAllSets(), is(arrayWithSize(greaterThan(0))));
	}

}
