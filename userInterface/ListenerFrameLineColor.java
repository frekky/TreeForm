package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerFrameLineColor implements ChangeListener {

	private Properties mProperties;
	private UserFrame mUserFrame;

	public ListenerFrameLineColor(Properties properties, UserFrame userFrame) {
		mProperties = properties;
		mUserFrame = userFrame;
	}

	public void stateChanged(ChangeEvent e) {
		mProperties.setLineColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mUserFrame.getObservableLineColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());

	}
}
