package userInterface;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

class DefaultSwatchChooserPanel  extends  AbstractColorChooserPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MainSwatchPanel mainPalette;
    //RecentSwatchPanel recentPalette;
    MouseListener mouseHandler;
    static abstract class SwatchPanel  extends  JPanel {
        protected int cellWidth = 10;
        protected int cellHeight = 10;
        protected int gap = 1;
        protected int numRows;
        protected int numCols;
        SwatchPanel() {
            super();
            setBackground(Color.WHITE);
        }
        public Dimension getPreferredSize() {
            int height = numRows * cellHeight + (numRows - 1) * gap;
            int width = numCols * cellWidth + (numCols - 1) * gap;
            Insets insets = getInsets();
            return new Dimension(width + insets.left + insets.right, height + insets.top + insets.bottom);
        }
        public abstract Color getColorForPosition(int x, int y);
        protected abstract void initializeColors();
    }
    static class MainSwatchPanel  extends  SwatchPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	
        public static final Color BLUE1 = new Color(36,139,192);
        public static final Color BLUE2 = new Color(129,173,214);
        public static final Color BLUE3 = new Color(187,210,237);
        public static final Color BLUE4 = new Color(224,238,255);
        
        public static final Color ORANGE1 = new Color(254,151,41);
        public static final Color ORANGE2 = new Color(255,176,98);
        public static final Color ORANGE3 = new Color(255,199,138);
        public static final Color ORANGE4 = new Color(255,228,207);
        
        public static final Color GREEN1 = new Color(50,171,90);
        public static final Color GREEN2 = new Color(118,199,123);
        public static final Color GREEN3 = new Color(176,218,174);
        public static final Color GREEN4 = new Color(217,239,207);

        public static final Color RED1 = new Color(223,61,51);
        public static final Color RED2 = new Color(243,125,113);
        public static final Color RED3 = new Color(255,170,165);
        public static final Color RED4 = new Color(255,214,223);
        
        public static final Color PURPLE1 = new Color(166,127,190);
        public static final Color PURPLE2 = new Color(189,160,207);
        public static final Color PURPLE3 = new Color(208,191,220);
        public static final Color PURPLE4 = new Color(240,223,251);
        
        public static final Color BROWN1 = new Color(157,106,93);
        public static final Color BROWN2 = new Color(183,139,127);
        public static final Color BROWN3 = new Color(207,172,163);
        public static final Color BROWN4 = new Color(249,215,206);
        
        public static final Color PINK1 = new Color(232,151,199);
        public static final Color PINK2 = new Color(240,170,210);
        public static final Color PINK3 = new Color(249,196,218);
        public static final Color PINK4 = new Color(255,222,242);
 
        public static final Color GRAY1 = new Color(145,145,144);
        public static final Color GRAY2 = new Color(177,178,178);
        public static final Color GRAY3 = new Color(208,208,208);
        public static final Color GRAY4 = new Color(244,244,244);
        
        public static final Color GOLD1 = new Color(198,197,42);
        public static final Color GOLD2 = new Color(211,210,110);
        public static final Color GOLD3 = new Color(224,223,157);
        public static final Color GOLD4 = new Color(249,244,191);
        
        public static final Color TEAL1 = new Color(5,199,215);
        public static final Color TEAL2 = new Color(122,211,223);
        public static final Color TEAL3 = new Color(171,224,232);
        public static final Color TEAL4 = new Color(213,244,252);
        
        public static final Color DARKGRAY1 = new Color(1,1,1);
        public static final Color DARKGRAY2 = Color.YELLOW;
        public static final Color DARKGRAY3 = Color.MAGENTA;
        public static final Color DARKGRAY4 = Color.CYAN;

        public static final Color PRIMARY1 = Color.WHITE;
        public static final Color PRIMARY2 = Color.RED;
        public static final Color PRIMARY3 = Color.GREEN;
        public static final Color PRIMARY4 = Color.BLUE;

        static Color[] colors = {BLUE1,BLUE2,BLUE3,BLUE4,
        	ORANGE1,ORANGE2,ORANGE3,ORANGE4,
        	GREEN1,GREEN2,GREEN3,GREEN4,
        	RED1,RED2,RED3,RED4,
        	PURPLE1,PURPLE2,PURPLE3,PURPLE4,
        	BROWN1,BROWN2,BROWN3,BROWN4,
        	PINK1,PINK2,PINK3,PINK4,
        	GRAY1,GRAY2,GRAY3,GRAY4,
        	GOLD1,GOLD2,GOLD3,GOLD4,
        	TEAL1,TEAL2,TEAL3,TEAL4,
        	DARKGRAY1,DARKGRAY2,DARKGRAY3,DARKGRAY4,
        	PRIMARY1,PRIMARY2,PRIMARY3,PRIMARY4};
        	MainSwatchPanel() {
            super();
            numCols = 4;
            numRows = 12;
            initializeColors();
            revalidate();
        }
        public Color getColorForPosition(int x, int y) {
            if (x % (cellWidth + gap) > cellWidth || y % (cellHeight + gap) > cellHeight) return null;
            int row = y / (cellHeight + gap);
            int col = x / (cellWidth + gap);
            return colors[row * numCols + col];
        }
        protected void initializeColors() {
        }
        public void paint(Graphics graphics) {
            int index = 0;
            Insets insets = getInsets();
            int currX = insets.left;
            int currY = insets.top;
            Color saved = graphics.getColor();
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    graphics.setColor(colors[index++]);
                    graphics.fill3DRect(currX, currY, cellWidth, cellHeight, true);
                    currX += gap + cellWidth;
                }
                currX = insets.left;
                currY += gap + cellHeight;
            }
            graphics.setColor(saved);
        }
        public String getToolTipText(MouseEvent e) {
            Color c = getColorForPosition(e.getX(), e.getY());
            if (c == null) return null;
            return (c.getRed() + "," + c.getGreen() + "," + c.getBlue());
        }
    }
   
    class MouseHandler  extends  MouseAdapter {
        public void mousePressed(MouseEvent e) {
            SwatchPanel panel = (SwatchPanel)e.getSource();
            Color c = panel.getColorForPosition(e.getX(), e.getY());
           // recentPalette.addColorToQueue(c);
            DefaultSwatchChooserPanel.this.getColorSelectionModel().setSelectedColor(c);
            DefaultSwatchChooserPanel.this.repaint();
        }
    }
    static class MainPanelLayout implements LayoutManager {
        public void addLayoutComponent(String name, Component comp) {
        }
        public void layoutContainer(Container parent) {
            Component[] comps = parent.getComponents();
            Insets insets = parent.getInsets();
            Dimension[] pref = new Dimension[comps.length];
            int xpos = 0;
            int ypos = 0;
            int maxHeight = 0;
            int totalWidth = 0;
            for (int i = 0; i < comps.length; i++) {
                pref[i] = comps[i].getPreferredSize();
                if (pref[i] == null) return;
                maxHeight = Math.max(maxHeight, pref[i].height);
                totalWidth += pref[i].width;
            }
            ypos = (parent.getSize().height - maxHeight) / 2 + insets.top;
            xpos = insets.left + (parent.getSize().width - totalWidth) / 2;
            for (int i = 0; i < comps.length; i++) {
                if (pref[i] == null) continue;
                comps[i].setBounds(xpos, ypos, pref[i].width, pref[i].height);
                xpos += pref[i].width;
            }
        }
        public void removeLayoutComponent(Component comp) {
        }
        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }
        public Dimension preferredLayoutSize(Container parent) {
            int xmax = 0;
            int ymax = 0;
            Component[] comps = parent.getComponents();
            Dimension pref;
            for (int i = 0; i < comps.length; i++) {
                pref = comps[i].getPreferredSize();
                if (pref == null) continue;
                xmax += pref.width;
                ymax = Math.max(ymax, pref.height);
            }
            Insets insets = parent.getInsets();
            return new Dimension(insets.left + insets.right + xmax, insets.top + insets.bottom + ymax);
        }
    }
   
    DefaultSwatchChooserPanel() {
        super();
    }
    public void updateChooser() {
    }
    protected void buildChooser() {
        setLayout(new MainPanelLayout());
        JPanel mainPaletteHolder = new JPanel();
        mainPalette = new MainSwatchPanel();
        mouseHandler = new MouseHandler();
        mainPalette.addMouseListener(mouseHandler);
        mainPaletteHolder.setLayout(new BorderLayout());
        mainPaletteHolder.add(mainPalette, BorderLayout.CENTER);
        JPanel main = new JPanel();
        main.add(mainPaletteHolder);
        this.add(main);
    }
    public void uninstallChooserPanel(JColorChooser chooser) {
     //   recentPalette = null;
       mainPalette = null;
       removeAll();
        super.uninstallChooserPanel(chooser);
    }
   public String getDisplayName() {
		return "Swatches";
    }
    public Icon getSmallDisplayIcon() {
        return null;
    }
    public Icon getLargeDisplayIcon() {
        return null;
    }
    public void paint(Graphics g) {
        super.paint(g);    }
    public String getToolTipText(MouseEvent e) {
        return null;
    }
}

