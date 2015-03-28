package me.pedrazas.plugin.eclipsedocker.views;


import me.pedrazas.plugin.eclipsedocker.utils.DockerUtils;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerInfo;



public class DockerView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "me.pedrazas.plugin.eclipsedocker.views.DockerView";

	private TableViewer viewer;
	private Action stop;
	private Action refresh;
	private Action inspect;
	private Action doubleClickAction;


	/**
	 * The constructor.
	 */
	public DockerView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		viewer = new DockerViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new DockerProvider());
		
		getSite().setSelectionProvider(viewer);
		viewer.setInput(getViewSite());

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "EclipseDocker.viewer");
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
		
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				DockerView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(stop);
		manager.add(new Separator());
		manager.add(inspect);
		manager.add(refresh);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(stop);
		manager.add(inspect);
		manager.add(refresh);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(stop);
		manager.add(inspect);
		manager.add(refresh);
	}

	private void makeActions() {
		stop = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection)selection).getFirstElement();
				Container container =(Container) ((IStructuredSelection)selection).getFirstElement();
				DockerUtils.stopContainer(container.id());
				viewer.setContentProvider(new DockerProvider());
				viewer.setInput(getViewSite());
			}
			
			    
		};
		stop.setText("Stop");
		stop.setToolTipText("Stop container");
		stop.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
			getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		refresh = new Action(){
			public void run(){
				viewer.setContentProvider(new DockerProvider());
				viewer.setInput(getViewSite());
			}
		};
		
		refresh.setText("Refresh");
		refresh.setToolTipText("Refresh table");
		stop.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		inspect = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Container container =(Container) ((IStructuredSelection)selection).getFirstElement();
				ContainerInfo info = DockerUtils.inspectContainer(container.id());
			}
		};
		inspect.setText("Inspect");
		inspect.setToolTipText("Isnpect container");
		inspect.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().
				getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		
		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Container container =(Container) ((IStructuredSelection)selection).getFirstElement();
				showMessage("Double-click detected on "+ container.id());
			}
		};
	}
	


	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	private void showMessage(String message) {
		MessageDialog.openInformation(
			viewer.getControl().getShell(),
			"Docker - Running Containers",
			message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}