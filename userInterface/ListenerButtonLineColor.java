package userInterface;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ListenerButtonLineColor implements ChangeListener {

	private JFrame mFrame;
	private Color color;
	private UserButtonLineColor mBLC;
	public ListenerButtonLineColor(UserButtonLineColor BLC, JFrame frame) {
		mBLC = BLC;
		mFrame = frame;
	}

	public void stateChanged(ChangeEvent arg0) {
		color = ((ColorSelectionModel) arg0.getSource()).getSelectedColor();
		if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
		{
			((ColorSelectionModel)arg0.getSource()).setSelectedColor(Color.BLACK);
		}
		mBLC.setColor(color);
		mFrame.dispose();
	}
}
