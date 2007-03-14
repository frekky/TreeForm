
//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;

package syntaxTree;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * A class containing the popup menu for SyntacticFeatures
 */
public class SSPopupMenu extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @uml.property name="mSF"
	 * @uml.associationEnd 
	 * @uml.property name="mSF" multiplicity="(1 1)"
	 */
	private SyntaxFacade mSF;

	/**
	 * 
	 * @uml.property name="mSS"
	 * @uml.associationEnd 
	 * @uml.property name="mSS" multiplicity="(1 1)"
	 */
	private SyntacticStructure mSS;

	/**
	 * 
	 * @uml.property name="mRepositionSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mRepositionSubtree" multiplicity="(1 1)"
	 */
	private JMenuItem mRepositionSubtree;

	/**
	 * 
	 * @uml.property name="mDeleteSubtree"
	 * @uml.associationEnd 
	 * @uml.property name="mDeleteSubtree" multiplicity="(1 1)"
	 */
	private JMenuItem mDeleteSubtree;

	private JMenuItem mDeleteStartTrace;

	private JMenuItem mDeleteEndTrace;

	private JMenuItem mCustomizeStartTrace;

	private JMenuItem mCustomizeEndTrace;

	private JMenuItem mChangeLineColor;

	/**
	 * 
	 * @param pSF The SyntaxFacade for this menu
	 * @param pSS The SyntacticStructure associated with this menu
	 * <br>
	 * This menu includes Delete Subtree, and Reposition Subtree
	 * commands.
	 * TODO: Add i18n
	 */
	public SSPopupMenu(SyntaxFacade pSF, SyntacticStructure pSS)
	{
		mSS = pSS;
		mSF = pSF;
		mDeleteSubtree = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("DELETE_SUBTREE"));
		mDeleteSubtree.addActionListener(new ListenerDelete(mSF, mSS));
		add(mDeleteSubtree);
		mRepositionSubtree = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("REPOSITION_SUBTREE"));
		mRepositionSubtree.addActionListener(new ListenerReposition(mSF, mSS));
		add(mRepositionSubtree);
		if(pSS.getStartTrace().size() > 0)
		{
			add(new JSeparator());
			mDeleteStartTrace = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("DELETE_START_TRACE"));
			mDeleteStartTrace.addActionListener(new ListenerDeleteStartTrace(mSF,mSS));
			add(mDeleteStartTrace);
			mCustomizeStartTrace = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("CUSTOMIZE_START_TRACE"));
			mCustomizeStartTrace.addActionListener(new ListenerCustomizeStartTrace(mSF,mSS));
			add(mCustomizeStartTrace);
		}
		if(pSS.getEndTrace().size() > 0)
		{
			add(new JSeparator());
			mDeleteEndTrace = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("DELETE_END_TRACE"));
			mDeleteEndTrace.addActionListener(new ListenerDeleteEndTrace(mSF,mSS));
			add(mDeleteEndTrace);
			mCustomizeEndTrace = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("CUSTOMIZE_END_TRACE"));
			mCustomizeEndTrace.addActionListener(new ListenerCustomizeEndTrace(mSF,mSS));
			add(mCustomizeEndTrace);
		}
		add(new JSeparator());
		mChangeLineColor = new JMenuItem((String) pSF.getUIF().getUserFrame().getI18n().getObject("CHANGE_LINE_COLOR"));
		mChangeLineColor.addActionListener(new ListenerChangeLineColor(mSF,mSS));
		add(mChangeLineColor);
	}
}
