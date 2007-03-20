package userInterface;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import syntaxTree.SyntacticStructure;

public class UserMovementPane extends JComponent {

	private UserFrame mUserFrame;
	private SyntacticStructure mStructure;

	public UserMovementPane(UserFrame userFrame, SyntacticStructure structure) {
		mUserFrame = userFrame;
		mStructure = structure;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setPosition(MouseEvent pme) {
		// TODO Auto-generated method stub
		
	}

	public void setEnd(SyntacticStructure structure) {
		mUserFrame.getSyntaxFacade().addTrace(structure, mStructure);
		
	}

}
