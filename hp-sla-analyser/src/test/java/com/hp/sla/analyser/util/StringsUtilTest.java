package com.hp.sla.analyser.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringsUtilTest {

	@Test
	public void shouldReturnTrueWithNullString() throws Exception {
		assertTrue(StringsUtil.isNullOrEmpty(null));
	}

	@Test
	public void shouldReturnTrueWithEmptyString() throws Exception {
		assertTrue(StringsUtil.isNullOrEmpty(""));
	}

	@Test
	public void shouldReturnTrueWithOnlySpacesString() throws Exception {
		assertTrue(StringsUtil.isNullOrEmpty("           "));
	}

	@Test
	public void shouldReturnFalseWithAnyRegularString() throws Exception {
		assertFalse(StringsUtil.isNullOrEmpty("hello"));
	}

}
