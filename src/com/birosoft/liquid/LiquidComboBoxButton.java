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

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.swing.CellRendererPane;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import com.birosoft.liquid.skin.Skin;
import com.birosoft.liquid.skin.SkinSimpleButtonIndexModel;

public class LiquidComboBoxButton extends JButton
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @uml.property name="comboBox"
     * @uml.associationEnd
     * @uml.property name="comboBox" multiplicity="(0 1)"
     */
    protected JComboBox comboBox;

    /**
     *
     * @uml.property name="listBox"
     * @uml.associationEnd
     * @uml.property name="listBox" multiplicity="(0 1)"
     */
    protected JList listBox;

    /**
     *
     * @uml.property name="rendererPane"
     * @uml.associationEnd
     * @uml.property name="rendererPane" multiplicity="(0 1)"
     */
    protected CellRendererPane rendererPane;

    /**
     *
     * @uml.property name="comboIcon"
     * @uml.associationEnd
     * @uml.property name="comboIcon" multiplicity="(0 1)"
     */
    protected Icon comboIcon;

    protected boolean iconOnly = false;
    BufferedImage focusImg;

    /**
     *
     * @uml.property name="comboBox"
     */
    public final JComboBox getComboBox() {
        return comboBox;
    }

    /**
     *
     * @uml.property name="comboBox"
     */
    public final void setComboBox(JComboBox cb) {
        comboBox = cb;
    }

    /**
     *
     * @uml.property name="comboIcon"
     */
    public final Icon getComboIcon() {
        return comboIcon;
    }

    /**
     *
     * @uml.property name="comboIcon"
     */
    public final void setComboIcon(Icon i) {
        comboIcon = i;
    }


    public final boolean isIconOnly()
    {
        return iconOnly;
    }

    /**
     *
     * @uml.property name="iconOnly"
     */
    public final void setIconOnly(boolean isIconOnly) {
        iconOnly = isIconOnly;
    }


    LiquidComboBoxButton()
    {
        super("");
        DefaultButtonModel model = new DefaultButtonModel()
        {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void setArmed(boolean armed)
            {
                super.setArmed(isPressed() ? true : armed);
            }
        };

        setModel(model);

        // Set the background and foreground to the combobox colors.
        setBackground(UIManager.getColor("ComboBox.background"));
        setForeground(UIManager.getColor("ComboBox.foreground"));

        ImageIcon icon = LiquidLookAndFeel.loadIcon("comboboxfocus.png",this);
        focusImg = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        Graphics g3 = focusImg.getGraphics();
        icon.paintIcon(this, g3, 0, 0);
    }

    public LiquidComboBoxButton(JComboBox cb, Icon i, CellRendererPane pane, JList list)
    {
        this();
        comboBox = cb;
        comboIcon = i;
        rendererPane = pane;
        listBox = list;
        setEnabled(comboBox.isEnabled());
    }

    public LiquidComboBoxButton(JComboBox cb, Icon i, boolean onlyIcon, CellRendererPane pane, JList list)
    {
        this(cb, i, pane, list);
        iconOnly = onlyIcon;
    }

    /**
     *
     * @uml.property name="skinCombo"
     * @uml.associationEnd
     * @uml.property name="skinCombo" multiplicity="(0 1)"
     */
    Skin skinCombo;

    /**
     *
     * @uml.property name="indexModel"
     * @uml.associationEnd
     * @uml.property name="indexModel" multiplicity="(0 1)"
     */
    SkinSimpleButtonIndexModel indexModel = new SkinSimpleButtonIndexModel();

    /**
     *
     * @uml.property name="skinArrow"
     * @uml.associationEnd
     * @uml.property name="skinArrow" multiplicity="(0 1)"
     */
    Skin skinArrow;

    /**
     *
     * @uml.property name="skinButton"
     * @uml.associationEnd
     * @uml.property name="skinButton" multiplicity="(0 1)"
     */
    Skin skinButton;


    /**
     * Mostly taken from the swing sources
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    public void paintComponent(Graphics g)
    {

        boolean leftToRight = getComponentOrientation().isLeftToRight();

        int index = indexModel.getIndexForState(model.isEnabled(),model.isRollover(),model.isArmed() && model.isPressed() | model.isSelected());

        // Paint the button as usual
        if (iconOnly)
        {
            getSkinCombo().draw(g, index, getWidth(), getHeight());

            int middle = (getHeight() - getSkinArrow().getVsize()) / 2;
            getSkinArrow().draw(g, index, getWidth() - getSkinArrow().getHsize()-6, middle,   getSkinArrow().getHsize(), getSkinArrow().getVsize());

            comboBox.getEditor().getEditorComponent().repaint();

        } else
        {
            getSkinCombo().draw(g, index, getWidth(), getHeight());

            int middle = (getHeight() - getSkinArrow().getVsize()) / 2;
            getSkinArrow().draw(g, index, getWidth() - getSkinArrow().getHsize()-6, middle,  getSkinArrow().getHsize(), getSkinArrow().getVsize());

        }

        Insets insets = new Insets(0, 12, 2, 2);

        int width = getWidth() - (insets.left + insets.right);
        int height = getHeight() - (insets.top + insets.bottom);

        if (height <= 0 || width <= 0)
        {
            return;
        }

        int left = insets.left;
        int top = insets.top;
        int iconWidth = LiquidComboBoxUI.comboBoxButtonSize;
        // Let the renderer paint
        Component c = null;
        boolean mustResetOpaque = false;
        boolean savedOpaque = false;
        boolean paintFocus = false;
        if (!iconOnly && comboBox != null)
        {
            ListCellRenderer renderer = comboBox.getRenderer();
            boolean renderPressed = getModel().isPressed();
            c = renderer.getListCellRendererComponent(listBox, comboBox.getSelectedItem(), -1, renderPressed, false);
            c.setFont(rendererPane.getFont());

            if (model.isArmed() && model.isPressed())
            {
                if (isOpaque())
                {
                    c.setBackground(UIManager.getColor("Button.select"));
                }
                c.setForeground(comboBox.getForeground());
            } else if (!comboBox.isEnabled())
            {
                if (isOpaque())
                {
                    c.setBackground(UIManager.getColor("ComboBox.disabledBackground"));
                }
                c.setForeground(UIManager.getColor("ComboBox.disabledForeground"));
            } else
            {
                c.setForeground(comboBox.getForeground());
                c.setBackground(comboBox.getBackground());
            }
            if (!mustResetOpaque && c instanceof JComponent)
            {
                mustResetOpaque = true;
                JComponent jc = (JComponent) c;
                savedOpaque = jc.isOpaque();
                jc.setOpaque(false);
            }

            int cWidth = width - (insets.right + iconWidth);

            // Fix for 4238829: should lay out the JPanel.
            boolean shouldValidate = false;
            if (c instanceof JPanel)
            {
                shouldValidate = true;
            }

            if (leftToRight)
            {
                rendererPane.paintComponent(g, c, this, left, top, cWidth, height, shouldValidate);
            } else
            {
                rendererPane.paintComponent(g, c, this, left + iconWidth, top, cWidth, height, shouldValidate);
            }
            if (paintFocus)
            {
                g.setColor(Color.black);
                Graphics2D g2d = (Graphics2D) g;
                //BasicStroke focusStroke=new BasicStroke(1.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL,1.0f, new float[] {1.0f, 1.0f}, 1.0f);
                Rectangle r = new Rectangle(left, top, 2, 2);
                TexturePaint tp = new TexturePaint(focusImg, r);

                g2d.setPaint(tp);
                g2d.draw(new Rectangle(left,top,cWidth, height));
            }
        }
        if (mustResetOpaque)
        {
            JComponent jc = (JComponent) c;
            jc.setOpaque(savedOpaque);
        }
    }

    /**
     *
     * @uml.property name="skinCombo"
     */
    public Skin getSkinCombo() {
        if (skinCombo == null) {
            skinCombo = new Skin("combobox.png", 4, 10, 6, 18, 4);
        }
        return skinCombo;
    }

    /**
     *
     * @uml.property name="skinArrow"
     */
    public Skin getSkinArrow() {
        if (skinArrow == null) {
            skinArrow = new Skin("comboboxarrow.png", 4, 0);

        }
        return skinArrow;
    }


}
