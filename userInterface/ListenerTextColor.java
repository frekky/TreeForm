package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerTextColor implements ChangeListener {

	private Properties mProperties;

	public ListenerTextColor(Properties properties) {
		mProperties = properties;
	}

	public void stateChanged(ChangeEvent arg0) {
		mProperties.setFontColor(((ColorSelectionModel) arg0.getSource()).getSelectedColor());

	}

}
