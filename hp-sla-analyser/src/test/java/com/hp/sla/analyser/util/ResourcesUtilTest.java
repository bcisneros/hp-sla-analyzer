package com.hp.sla.analyser.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

/**
 *
 * @author Benjamín Cisneros Barraza
 */
public class ResourcesUtilTest {

	/**
	 * Test of getResourceFromProjectClasspath method, of class ResourcesUtil.
	 */
	@Test
	public void testGetResourceFromProjectClasspath() {
		ResourcesUtil.classLoader = ResourcesUtil.class.getClassLoader();
		assertNotNull("This file must be not null",
				ResourcesUtil.getResourceFromProjectClasspath("files/reportTemplate.xlsx"));
		assertNull("This file must be null",
				ResourcesUtil.getResourceFromProjectClasspath("files/somethingThatDoesNotExist"));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowAnExceptionWhenAnErrorOcurrDuringRetrievingTheFile() throws Exception {
		ResourcesUtil.classLoader = mock(ClassLoader.class);
		when(ResourcesUtil.classLoader.getResourceAsStream(anyString())).thenThrow(new RuntimeException());
		ResourcesUtil.getResourceFromProjectClasspath("files/somethingThatDoesNotExist");
	}

}
