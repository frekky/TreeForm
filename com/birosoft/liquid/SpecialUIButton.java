/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
*	Liquid Look and Feel                                                   *
*                                                                              *
*  Author, Miroslav Lazarevic                                                  *
*                                                                              *
*   For licensing information and credits, please refer to the                 *
*   comment in file com.birosoft.liquid.LiquidLookAndFeel                      *
*                                                                              *
* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package com.birosoft.liquid;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import java.awt.Frame;
import javax.swing.plaf.ButtonUI;

/**
 * This Button uses the given ButtonUI to paint itself. Useful if a button should have
 * a different look than the standard button. It refuses to change the UI delegate of the button. 
 */
public class SpecialUIButton extends JButton {
    
    
	public SpecialUIButton() {
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @uml.property name="myUI"
	 * @uml.associationEnd 
	 * @uml.property name="myUI" multiplicity="(0 1)"
	 */
	ButtonUI myUI;

	/**
	 * 
	 * @uml.property name="frame"
	 * @uml.associationEnd 
	 * @uml.property name="frame" multiplicity="(0 1)"
	 */
	JInternalFrame frame;

    Frame window;
    
    public SpecialUIButton(ButtonUI ui) {
        this.ui=ui;
        myUI=ui;         
        ui.installUI(this);
    }
    
    public SpecialUIButton(ButtonUI ui, JInternalFrame frame) {
        this.ui=ui;
        myUI=ui;         
        ui.installUI(this);
        this.frame = frame;
        this.setOpaque(false);
        this.setFocusPainted(false);
        this.setFocusable(false);
    }
    
    public SpecialUIButton(ButtonUI ui, Frame frame) {
        this.ui=ui;
        myUI=ui;         
        ui.installUI(this);
        this.window = frame;
        this.setOpaque(false);
        this.setFocusPainted(false);
        this.setFocusable(false);
    }
    
    /**
     * refuses to change the UI delegate. It keeps the one set in the constructor.
     * @see javax.swing.AbstractButton#setUI(ButtonUI)
     */
    public void setUI(ButtonUI ui) {            
    }       
}