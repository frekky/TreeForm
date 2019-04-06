package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerCloseProperties implements ActionListener {

    private PropertiesFrame mFrame;

    public ListenerCloseProperties(PropertiesFrame frame) {
        mFrame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        mFrame.dispose();

    }

}
