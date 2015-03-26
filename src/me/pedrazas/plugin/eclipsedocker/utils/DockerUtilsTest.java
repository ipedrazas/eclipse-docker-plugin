package me.pedrazas.plugin.eclipsedocker.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DockerUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetImageFromRunningContainers() {
		String[] images = (String[]) DockerUtils.getImageFromRunningContainers();
		for(String i:images){
			System.out.println(i);
		}
	}

}
