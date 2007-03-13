package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerLineColor implements ChangeListener {

	private Properties mProperties;

	public ListenerLineColor(Properties properties) {
		mProperties = properties;
	}

	public void stateChanged(ChangeEvent e) {
		mProperties.setLineColor(((ColorSelectionModel) e.getSource()).getSelectedColor());

	}

}
