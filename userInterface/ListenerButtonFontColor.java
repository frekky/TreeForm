package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerButtonFontColor implements ChangeListener {

	private UserFrame mUserFrame;

	public ListenerButtonFontColor(UserFrame userFrame) {
		mUserFrame = userFrame;
	}

	public void stateChanged(ChangeEvent arg0) {
		mUserFrame.getObservableFontColor().setValue(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
	}
}