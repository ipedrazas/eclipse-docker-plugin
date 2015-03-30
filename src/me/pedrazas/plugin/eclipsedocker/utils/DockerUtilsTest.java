package me.pedrazas.plugin.eclipsedocker.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;

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

	@Test
	public void testGetPortsAsString() {
		Object[] containers = DockerUtils.getRunningContainers().toArray();
		for(Object c:containers){
			try {
				String id = ((Container) c).id();
				System.out.println(DockerUtils.getPortsAsString(id));
			} catch (DockerException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
