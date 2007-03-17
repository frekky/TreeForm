package userInterface;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerButtonFontColor implements ChangeListener {

	private UserFrame mUserFrame;
	private JFrame mFrame;
	private Color color;

	public ListenerButtonFontColor(UserFrame userFrame, JFrame frame) {
		mUserFrame = userFrame;
		mFrame = frame;
	}

	public void stateChanged(ChangeEvent arg0) {
		color = ((ColorSelectionModel) arg0.getSource()).getSelectedColor();
		if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
		{
			((ColorSelectionModel)arg0.getSource()).setSelectedColor(Color.BLACK);
		}
		mUserFrame.getObservableFontColor().setValue(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
		mFrame.dispose();
	}
}