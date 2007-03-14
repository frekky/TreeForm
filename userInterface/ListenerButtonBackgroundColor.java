package userInterface;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerButtonBackgroundColor implements ChangeListener {

	private UserFrame mUserFrame;

	public ListenerButtonBackgroundColor(UserFrame userFrame) {
		mUserFrame = userFrame;
	}

	public void stateChanged(ChangeEvent e) {
		mUserFrame.getObservableBackgroundColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
	}
}