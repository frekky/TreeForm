package syntaxTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import enumerators.SyntacticLevel;

public class ListenerToggleTriangle implements ActionListener {

	private SyntaxFacade mSF;
	private SyntacticStructure mSS;

	public ListenerToggleTriangle(SyntaxFacade pSF, SyntacticStructure pSS) {
		mSF = pSF;
		mSS = pSS;
	}

	public void actionPerformed(ActionEvent arg0) {
		if(mSS.getSyntacticLevel() == SyntacticLevel.TRIANGLE)
		{
			mSS.setSyntacticLevel(SyntacticLevel.HEAD);
		}
		else
		{
			mSS.setSyntacticLevel(SyntacticLevel.TRIANGLE);
		}
		mSF.displayTree();

	}

}
