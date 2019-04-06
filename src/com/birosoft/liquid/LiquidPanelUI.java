/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *        Liquid Look and Feel                                                   *
 *                                                                              *
 *  Author, Miroslav Lazarevic                                                  *
 *                                                                              *
 *   For licensing information and credits, please refer to the                 *
 *   comment in file com.birosoft.liquid.LiquidLookAndFeel                      *
 *                                                                              *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.birosoft.liquid;

import com.birosoft.liquid.util.Colors;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;


public class LiquidPanelUI extends BasicPanelUI {
    // Shared UI object
    private static LiquidPanelUI panelUI;

    public static ComponentUI createUI(JComponent c) {
        if (panelUI == null) {
            panelUI = new LiquidPanelUI();
        }

        return panelUI;
    }

    public void installUI(JComponent c) {
        JPanel p = (JPanel) c;
        super.installUI(p);
        installDefaults(p);
    }

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
    }

    public void paint(Graphics g, JComponent c) {
        Color bg = LiquidLookAndFeel.getBackgroundColor();
        Container container = c.getParent();

        if (LiquidLookAndFeel.areStipplesUsed()) {
            if (container instanceof JPanel && c.isOpaque()) {
                c.setOpaque(false);
                c.repaint();
            }

            if (LiquidLookAndFeel.getBackgroundColor().equals(c.getBackground()) &&
                c.isOpaque()) {
                Colors.drawStipples(g, c, bg);
            }

            if (container instanceof JLayeredPane) {
                c.setOpaque(true);
            }
        }

        super.paint(g, c);
    }
}