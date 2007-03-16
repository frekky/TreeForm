package userInterface;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerButtonBackgroundColor implements ChangeListener {

	private UserFrame mUserFrame;
	private JFrame mFrame;

	public ListenerButtonBackgroundColor(UserFrame userFrame, JFrame frame) {
		mUserFrame = userFrame;
		mFrame = frame;
	}

	public void stateChanged(ChangeEvent e) {
		mUserFrame.getObservableBackgroundColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
		mFrame.dispose();
	}
}