package userInterface;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JColorChooser;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import syntaxTree.Properties;

public class UserColorChooser extends JColorChooser implements Observer 
{

	public UserColorChooser(Properties properties)
	{
		this.setChooserPanels(newPanels());
		
	}
	private static final long serialVersionUID = 1L;

	private AbstractColorChooserPanel[] newPanels()
	{
	AbstractColorChooserPanel[] newPanels = new AbstractColorChooserPanel[3];
	newPanels[0] = new DefaultSwatchChooserPanel();
    newPanels[1] = findPanel(this, "javax.swing.colorchooser.DefaultHSBChooserPanel");
    newPanels[2] = findPanel(this, "javax.swing.colorchooser.DefaultRGBChooserPanel");
    return newPanels;
	}
	public AbstractColorChooserPanel findPanel(JColorChooser chooser, String name) {
        AbstractColorChooserPanel[] panels = chooser.getChooserPanels();
    
        for (int i=0; i<panels.length; i++) {
            String clsName = panels[i].getClass().getName();
    
            if (clsName.equals(name)) {
                return panels[i];
            }
        }
        return null;
    }
	public void update(Observable arg0, Object arg1) {
	}
}
