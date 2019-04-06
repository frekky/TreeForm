package userInterface;

import java.awt.Color;
import java.awt.font.TextAttribute;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerFrameBackgroundColor implements ChangeListener {

    private Properties mProperties;
    private UserFrame mUserFrame;
    private Color color;

    public ListenerFrameBackgroundColor(Properties properties, UserFrame userFrame) {
        mProperties = properties;
        mUserFrame = userFrame;
    }

    public void stateChanged(ChangeEvent e) {
        color = ((ColorSelectionModel) e.getSource()).getSelectedColor();
        if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
        {
            ((ColorSelectionModel)e.getSource()).setSelectedColor(Color.BLACK);
        }
        mProperties.setBackgroundColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
        mUserFrame.getObservableBackgroundColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());
        mUserFrame.getInternalFrame().getSyntaxFacade().changeAttributes(TextAttribute.BACKGROUND,color);
    }
}
