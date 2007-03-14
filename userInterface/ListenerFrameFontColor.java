package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerFrameFontColor implements ChangeListener {

	private Properties mProperties;
	private UserFrame mUserFrame;

	public ListenerFrameFontColor(Properties properties,UserFrame userFrame) {
		mProperties = properties;
		mUserFrame = userFrame;
	}

	public void stateChanged(ChangeEvent arg0) {
		mProperties.setFontColor(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getObservableFontColor().setValue(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
	}
}
