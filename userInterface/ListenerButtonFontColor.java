package userInterface;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerButtonFontColor implements ChangeListener {

	private UserFrame mUserFrame;
	private JFrame mFrame;

	public ListenerButtonFontColor(UserFrame userFrame, JFrame frame) {
		mUserFrame = userFrame;
		mFrame = frame;
	}

	public void stateChanged(ChangeEvent arg0) {
		mUserFrame.getObservableFontColor().setValue(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
		mFrame.dispose();
	}
}