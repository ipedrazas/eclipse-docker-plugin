package me.pedrazas.plugin.eclipsedocker.views;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;

public class RunDockerComposeAction implements IActionDelegate, IPropertyChangeListener {


	protected ISelection currentSelection;
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.currentSelection = selection;
		
	}

	@Override
	public void run(IAction action) {
		
		
	}
}
