package me.pedrazas.plugin.eclipsedocker.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.PortBinding;

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
	
	public static String getPortsAsString(String containerId) throws DockerException, InterruptedException{
		DockerClient client = getDockerClient();
		Map<String, List<PortBinding>> ports = client.inspectContainer(containerId).networkSettings().ports();
		
		Iterator<String> it = ports.keySet().iterator();
		StringBuilder sb = new StringBuilder();
		while(it.hasNext()){
			String key = it.next();
			List<PortBinding> bindings = ports.get(key);
			if(bindings != null){
				for(PortBinding pb : bindings){
					 if (sb.length() > 0) {
		                   sb.append(", ");
		               }
		               if (pb.hostIp() != null) {
		                   sb.append(pb.hostIp()).append(":");
		               }
		               if (pb.hostPort() != null) {
		                   sb.append(key).append("->").append(pb.hostPort());
		               } else {
		                   sb.append(key);
		               }
				}
			}else{
				if (sb.length() > 0) {
	                   sb.append(", ");
	               }
				sb.append(key);
			}
		}
		return sb.toString();
	}
}
