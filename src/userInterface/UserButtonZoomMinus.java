
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

package userInterface;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
/**
 * @author Donald Derrick
 * @version 0.1
 *
 * One of the UserButtons used to populate the toolbar.  All of these buttons are similar,
 * varying only in their observers and other small details.
 *
 **/
public class UserButtonZoomMinus extends JButton implements Observer{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="mObservableNew"
     * @uml.associationEnd
     * @uml.property name="mObservableNew" multiplicity="(1 1)"
     */
    private ObservableNew mObservableNew;

    /**
     *
     * @uml.property name="mHoldIcon"
     * @uml.associationEnd
     * @uml.property name="mHoldIcon" multiplicity="(0 1)"
     */
    private ImageIcon mHoldIcon;

    /**
     *
     * @uml.property name="mActiveIconSmall"
     * @uml.associationEnd
     * @uml.property name="mActiveIconSmall" multiplicity="(1 1)"
     */
    private ImageIcon mActiveIconSmall;

    /**
     *
     * @uml.property name="mDisableIconSmall"
     * @uml.associationEnd
     * @uml.property name="mDisableIconSmall" multiplicity="(1 1)"
     */
    private ImageIcon mDisableIconSmall;

    /**
     *
     * @uml.property name="mEnableIconSmall"
     * @uml.associationEnd
     * @uml.property name="mEnableIconSmall" multiplicity="(1 1)"
     */
    private ImageIcon mEnableIconSmall;

    /**
     *
     * @uml.property name="mActiveIcon"
     * @uml.associationEnd
     * @uml.property name="mActiveIcon" multiplicity="(1 1)"
     */
    private ImageIcon mActiveIcon;

    /**
     *
     * @uml.property name="mDisableIcon"
     * @uml.associationEnd
     * @uml.property name="mDisableIcon" multiplicity="(1 1)"
     */
    private ImageIcon mDisableIcon;

    /**
     *
     * @uml.property name="mEnableIcon"
     * @uml.associationEnd
     * @uml.property name="mEnableIcon" multiplicity="(1 1)"
     */
    private ImageIcon mEnableIcon;

    private boolean mSize;
    /**
     *
     * @param pUserFrame The UserFrame for this instance of TreeForm
     * @param pEnableIcon The large icon for an enabled button
     * @param pDisableIcon The large icon for a disabled button
     * @param pActiveIcon The large icon for an active button
     * @param pEnableIconSmall The small icon for an enabled button
     * @param pDisableIconSmall The small icon for a disabled button
     * @param pActiveIconSmall The small icon for an active button
     * @param pSize The size of the button (as dictated by screen resolution)
     * @param pObservableNew The shared observable used by all buttons that
     * should be disabled when there is nothing in the UserInternalFrame.
     *
     */
    public UserButtonZoomMinus(UserFrame pUserFrame,
        ImageIcon pEnableIcon,
        ImageIcon pDisableIcon,
        ImageIcon pActiveIcon,
        ImageIcon pEnableIconSmall,
        ImageIcon pDisableIconSmall,
        ImageIcon pActiveIconSmall, boolean pSize, ObservableNew pObservableNew) {
        super();
        mEnableIcon = pEnableIcon;
        mDisableIcon = pDisableIcon;
        mActiveIcon = pActiveIcon;
        mEnableIconSmall = pEnableIconSmall;
        mDisableIconSmall = pDisableIconSmall;
        mActiveIconSmall = pActiveIconSmall;
        mSize = pSize;
        mObservableNew = pObservableNew;
        setIcons();
    }
    /**
     *
     */
    private void setIcons() {
        if (mSize)
        {
            this.setIcon(mEnableIcon);
        }
        else
        {
            this.setIcon(mEnableIconSmall);
        }
        if (mSize)
        {
            this.setDisabledIcon(mDisableIcon);
        }
        else
        {
            this.setDisabledIcon(mDisableIconSmall);
        }
    }
    public void activate()
    {
        mHoldIcon = (ImageIcon) this.getIcon();
        if (mSize)
        {
            this.setIcon(mActiveIcon);
        }
        else
        {
            this.setIcon(mActiveIconSmall);
        }
    }
    public void deactivate()
    {
        this.setIcon(mHoldIcon);
    }
    public void makeSmall()
    {
        mSize = false;
        setIcons();
    }
    public void makeBig()
    {
        mSize = true;
        setIcons();
    }
    /* (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update(Observable pObservable, Object arg1) {
        if (pObservable == mObservableNew)
        {
            if (mObservableNew.getValue() == 0)
            {
                this.setEnabled(false);
            }
            else
            {
                this.setEnabled(true);
            }
        }

    }
}
