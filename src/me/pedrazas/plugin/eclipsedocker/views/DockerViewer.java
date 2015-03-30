package me.pedrazas.plugin.eclipsedocker.views;

import me.pedrazas.plugin.eclipsedocker.utils.DockerUtils;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.spotify.docker.client.DockerException;
import com.spotify.docker.client.messages.Container;


public class DockerViewer extends TableViewer{

	public DockerViewer(Composite parent, int style) {
        super(parent, style);
        Table table = getTable();
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        table.setLayoutData(gridData);
        createColumns();
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        setContentProvider(new DockerProvider());
    }

    private void createColumns(){
        String[] titles = { "Image", "Command", "Name", "Ports", "Status", "Id" };
        int[] bounds = { 200, 350, 100, 350, 150, 200 };

        TableViewerColumn column = createTableViewerColumn(titles[0], bounds[0], 0);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                if(element instanceof Container){
                	return ((Container)element).image();
                }    
                    		
                return super.getText(element);
            }
        });

        column = createTableViewerColumn(titles[1], bounds[1], 1);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                if(element instanceof Container)
                    return ((Container)element).command();
                return super.getText(element);
            }
        });

        column = createTableViewerColumn(titles[2], bounds[2], 2);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                if(element instanceof Container){
                	if(((Container)element).names() != null){
                        return String.join(",", ((Container)element).names());
                	}                	
                }
                return super.getText(element);
            }
        });
        


        column = createTableViewerColumn(titles[3], bounds[3], 3);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                if(element instanceof Container){                	
                	try {
						return DockerUtils.getPortsAsString(((Container) element).id());
					} catch (DockerException | InterruptedException e) {
						e.printStackTrace();
					}
                }
                return super.getText(element);
            }
        });

        column = createTableViewerColumn(titles[4], bounds[4], 4);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                if(element instanceof Container)
                    return ((Container)element).status();
                return super.getText(element);
            }
        });
        
        column = createTableViewerColumn(titles[5], bounds[5], 5);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                if(element instanceof Container)
                    return ((Container)element).id();
                return super.getText(element);
            }
        });

    }
    private TableViewerColumn createTableViewerColumn(String header, int width, int idx) {
        TableViewerColumn column = new TableViewerColumn(this, SWT.LEFT, idx);
        column.getColumn().setText(header);
        column.getColumn().setWidth(width);
        column.getColumn().setResizable(true);
        column.getColumn().setMoveable(true);

        return column;
    }
}
