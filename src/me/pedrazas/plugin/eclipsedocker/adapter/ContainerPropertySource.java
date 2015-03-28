package me.pedrazas.plugin.eclipsedocker.adapter;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import me.pedrazas.plugin.eclipsedocker.utils.DockerUtils;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerInfo;

public class ContainerPropertySource implements IPropertySource {

	private final Container container;

	public ContainerPropertySource(Container container) {
		super();
		this.container = container;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return new IPropertyDescriptor[] { new TextPropertyDescriptor("id", "Id"),
				new TextPropertyDescriptor("image", "Image"), new TextPropertyDescriptor("command", "Command"),
				new TextPropertyDescriptor("volumes", "Volumes"), new TextPropertyDescriptor("created", "Created"),
				new TextPropertyDescriptor("status", "Status"), new TextPropertyDescriptor("driver", "Driver"),
				new TextPropertyDescriptor("ipaddress", "IP Address"), new TextPropertyDescriptor("ports", "Ports")};
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals("id")) {
			return container.id();
		}
		if (id.equals("image")) {
			return container.image();
		}
		if (id.equals("command")) {
			return container.command();
		}
		if (id.equals("created")) {
			ContainerInfo info = DockerUtils.inspectContainer(container.id());
			return info.created();
		}
		if (id.equals("status")) {
			return container.status();
		}
		if (id.equals("driver")) {
			ContainerInfo info = DockerUtils.inspectContainer(container.id());
			return info.driver();
		}
		if (id.equals("ports")) {
			ContainerInfo info = DockerUtils.inspectContainer(container.id());
			return DockerUtils.portBindingAsString(info.networkSettings().ports());			
		}
		if (id.equals("ipaddress")) {
			ContainerInfo info = DockerUtils.inspectContainer(container.id());
			return info.networkSettings().ipAddress();
		}
		if (id.equals("volumes")) {
			ContainerInfo info = DockerUtils.inspectContainer(container.id());
			
			Map<String, String> volumes = info.volumes();
			StringBuilder sb = new StringBuilder();
			volumes.forEach((k, v) -> sb.append(k).append(":").append(v).append(" "));
			return sb.toString();
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		String s = (String) value;
		if (id.equals("image")) {

		}
	}
}
