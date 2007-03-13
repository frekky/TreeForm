package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerHighlightColor implements ChangeListener {

	private Properties mProperties;

	public ListenerHighlightColor(Properties properties) {
		mProperties = properties;
	}

	public void stateChanged(ChangeEvent e) {
		mProperties.setBackgroundColor(((ColorSelectionModel) e.getSource()).getSelectedColor());

	}

}
