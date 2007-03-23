package userInterface;

import java.awt.Color;
import java.awt.font.TextAttribute;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerFrameFontColor implements ChangeListener {

	private Properties mProperties;
	private UserFrame mUserFrame;
	private Color color;

	public ListenerFrameFontColor(Properties properties,UserFrame userFrame) {
		mProperties = properties;
		mUserFrame = userFrame;
	}

	public void stateChanged(ChangeEvent arg0) {
		color = ((ColorSelectionModel) arg0.getSource()).getSelectedColor();
		if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
		{
			((ColorSelectionModel)arg0.getSource()).setSelectedColor(Color.BLACK);
		}
		mProperties.setFontColor(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getObservableFontColor().setValue(((ColorSelectionModel) arg0.getSource()).getSelectedColor());
		mUserFrame.getInternalFrame().getSyntaxFacade().changeAttributes(TextAttribute.FOREGROUND,color);
	}
}
