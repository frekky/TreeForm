
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
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @author Donald Derrick
 * @version 0.1
 *
 * The base UserButton used for toolbar icons.  The UserButton is an i18n version
 * of buttons, with changeable imageicons (small and large) dictated by the i18n
 * files.
 *
 */
public class UserButton extends JButton {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

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
     */
    public UserButton(UserFrame pUserFrame,
        ImageIcon pEnableIcon,
        ImageIcon pDisableIcon,
        ImageIcon pActiveIcon,
        ImageIcon pEnableIconSmall,
        ImageIcon pDisableIconSmall,
        ImageIcon pActiveIconSmall, boolean pSize) {
        super();
        mEnableIcon = pEnableIcon;
        mDisableIcon = pDisableIcon;
        mActiveIcon = pActiveIcon;
        mEnableIconSmall = pEnableIconSmall;
        mDisableIconSmall = pDisableIconSmall;
        mActiveIconSmall = pActiveIconSmall;
        mSize = pSize;
        setIcons();
    }
    /**
     * Sets the icons
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
    /**
     * sets the activated icons
     *
     */
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
    /**
     * deactivates the icons
     *
     */
    public void deactivate()
    {
        this.setIcon(mHoldIcon);
    }
    /**
     * makes icons small
     *
     */
    public void makeSmall()
    {
        mSize = false;
        setIcons();
    }
    /**
     * makes icons big.
     *
     */
    public void makeBig()
    {
        mSize = true;
        setIcons();
    }
}
