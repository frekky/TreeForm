package userInterface;

import java.awt.Color;

import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;

public class ListenerFrameLineColor implements ChangeListener {

    private Properties mProperties;
    private UserFrame mUserFrame;
    private Color color;

    public ListenerFrameLineColor(Properties properties, UserFrame userFrame) {
        mProperties = properties;
        mUserFrame = userFrame;
    }

    public void stateChanged(ChangeEvent e) {
        color = ((ColorSelectionModel) e.getSource()).getSelectedColor();
        if (color.getRed() == 1 && color.getGreen() == 1 && color.getBlue() == 1)
        {
            ((ColorSelectionModel)e.getSource()).setSelectedColor(Color.BLACK);
        }
        mProperties.setLineColor(((ColorSelectionModel) e.getSource()).getSelectedColor());
        mUserFrame.getObservableLineColor().setValue(((ColorSelectionModel) e.getSource()).getSelectedColor());

    }
}
