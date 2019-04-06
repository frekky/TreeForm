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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

import com.birosoft.liquid.skin.Skin;
import com.birosoft.liquid.skin.SkinSimpleButtonIndexModel;

/**
 * This button is placed at either end of a scrollbar to move the scrollbar
 */
public class LiquidScrollButton extends BasicArrowButton
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /** one of the four skins for the button */
    private static Skin skinUp= new Skin("scrollbuttonsup.png", 4, 0);
    /** one of the four skins for the button */
    private static Skin skinDown= new Skin("scrollbuttonsdown.png", 4, 0);
    /** one of the four skins for the button */
    private static Skin skinLeft= new Skin("scrollbuttonsleft.png", 4, 0);
    /** one of the four skins for the button */
    private static Skin skinRight= new Skin("scrollbuttonsright.png", 4, 0);

    /**
     * the skin used for this instance of the scroll button
     *
     * @uml.property name="skin"
     * @uml.associationEnd
     * @uml.property name="skin" multiplicity="(1 1)"
     */
    private Skin skin;

    /**
     * the index model for this button
     *
     * @uml.property name="indexModel"
     * @uml.associationEnd
     * @uml.property name="indexModel" multiplicity="(1 1)"
     */
    private SkinSimpleButtonIndexModel indexModel = new SkinSimpleButtonIndexModel();


    /**
     * Create a new ScrollButton.
     *
     */
    public LiquidScrollButton(int orientation,int width, boolean freeStanding)
    {
        super(orientation);
        setBorder(null);
        setRolloverEnabled(true);
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(null);
        indexModel.setButton(this);

        switch (orientation)
        {
        case SwingConstants.NORTH:
            skin= skinUp;
            break;
        case SwingConstants.SOUTH:
            skin= skinDown;
            break;
        case SwingConstants.EAST:
            skin= skinRight;
            break;
        case SwingConstants.WEST:
            skin= skinLeft;
            break;
        }
    }

    public void setFreeStanding( boolean freeStanding )
    {
    }

    /**
     * Paints the button
     * @see java.awt.Component#paint(Graphics)
     */
    public void paint(Graphics g)
    {
        skin.draw(g, indexModel.getIndexForState(), getWidth(), getHeight());
    }

    /**
     * Returns the preferred size of the component wich is the size of the skin
     * @see java.awt.Component#getPreferredSize()
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(skinUp.getHsize(),skinUp.getVsize());
    }

    /**
     * Returns the skin for a button with the arrow pointing upwards
     * @return Skin
     *
     * @uml.property name="skinUp"
     */
    public static Skin getSkinUp() {
        return skinUp;
    }

    /**
     * Returns the skin for a button with the arrow pointing downwards
     * @return Skin
     *
     * @uml.property name="skinDown"
     */
    public static Skin getSkinDown() {
        return skinDown;
    }

    /**
     * Returns the skin for a button with the arrow pointing to the left
     * @return Skin
     *
     * @uml.property name="skinLeft"
     */
    public static Skin getSkinLeft() {
        return skinLeft;
    }

    /**
     * Returns the skin for a button with the arrow pointing to the right
     * @return Skin
     *
     * @uml.property name="skinRight"
     */
    public static Skin getSkinRight() {
        return skinRight;
    }

    /**
     * returns the skin for this instance of the button.
     * @return Skin
     *
     * @uml.property name="skin"
     */
    public Skin getSkin() {
        return skin;
    }

}