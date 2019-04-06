package userInterface;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class ListenerHighlightBackgroundColor implements ChangeListener {


	private JFrame mFrame;
	private Color color;
	private UserButtonBackgroundColor mBBC;

	public ListenerHighlightBackgroundColor(UserButtonBackgroundColor BBC, JFrame frame) {
		
		mFrame = frame;
		mBBC = BBC;
	}

	public void stateChanged(ChangeEvent e) {
		color = ((ColorSelectionModel) e.getSource()).getSelectedColor();
		if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
		{
			((ColorSelectionModel)e.getSource()).setSelectedColor(Color.BLACK);
		}
		if (color.getRed() == 255 & color.getGreen() == 255 && color.getBlue() == 255)
		{
			((ColorSelectionModel)e.getSource()).setSelectedColor(new Color(255,255,255,0));
		}
		
		mBBC.setColor(color);
		mFrame.dispose();
	}
}