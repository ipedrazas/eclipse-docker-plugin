package me.pedrazas.plugin.eclipsedocker.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import com.spotify.docker.client.messages.Container;

public class ContainerAdapterFactory implements IAdapterFactory {

	  @Override
	  public Object getAdapter(Object adaptableObject, Class adapterType) {
	    if (adapterType== IPropertySource.class && adaptableObject instanceof Container){
	      return new ContainerPropertySource((Container) adaptableObject);
	    }
	    return null;
	  }

	  @Override
	  public Class[] getAdapterList() {
	    return new Class[] { IPropertySource.class };
	  }

	} 
