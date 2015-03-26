package me.pedrazas.plugin.eclipsedocker.utils;

import java.util.ArrayList;
import java.util.List;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;

public class DockerUtils {

	
	public static DockerClient getDockerClient(){
		return new DefaultDockerClient("unix:///var/run/docker.sock");
	}
	
	public static Object[] getImageFromRunningContainers(){
		DockerClient client = getDockerClient();
		List<String> images = new ArrayList<String>();
		try {
			List<Container> containers = client.listContainers();
			for(Container c:containers){
				images.add(c.image());
			}
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
		return images.toArray();
	}
	public static List<Container> getRunningContainers(){
		DockerClient client = getDockerClient();
		try {
			return client.listContainers();
		} catch (DockerException | InterruptedException e) {
			e.printStackTrace();
		}
		return new ArrayList<Container>();
	}
}
