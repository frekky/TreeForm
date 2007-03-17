package userInterface;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerButtonBackgroundColor implements ChangeListener {

	private UserFrame mUserFrame;
	private JFrame mFrame;
	private Color color;

	public ListenerButtonBackgroundColor(UserFrame userFrame, JFrame frame) {
		mUserFrame = userFrame;
		mFrame = frame;
	}

	public void stateChanged(ChangeEvent e) {
		color = ((ColorSelectionModel) e.getSource()).getSelectedColor();
		if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
		{
			((ColorSelectionModel)e.getSource()).setSelectedColor(Color.BLACK);
		}
		mUserFrame.getObservableBackgroundColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());
		mUserFrame.getObservableClipboard().getValue().setHighlight(mUserFrame.getUserControl().getAttributes());
		mFrame.dispose();
	}
}