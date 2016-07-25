package linearregression1var;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

/**
 * Tool to plot 2D graphics
 * 
 * @author Matias Leone
 */
public class Plot2D {
    
    private final static int SCREEN_MIN_X = 20;
    private final static int SCREEN_MIN_Y = WIN_HEIGHT - 150;
    private final static int SCREEN_MAX_X = WIN_WIDTH - 20;
    private final static int SCREEN_MAX_Y = 20;
    
    private Stroke normalStroke;
    private Stroke boldStroke;
    private Stroke dottedStroke;
    private RenderPanel renderPanel;
    private BufferedImage renderImg;
    private Graphics2D renderG;
    private Dimension graphDim;                                                                                                    
    
    public Plot2D(int width, int height) {
        normalStroke = new BasicStroke();
        boldStroke = new BasicStroke(2);
        dottedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
        
        renderPanel = new RenderPanel();
    }
    
    /**
     * Init graphics
     */
    private void init() {

    }
    
    /**
     * Main render method
     */
    private void render(Graphics2D g) {

        //Draw axis
        g.setColor(Color.BLACK);
        g.setStroke(boldStroke);
        g.drawLine(SCREEN_MIN_X, SCREEN_MIN_Y, SCREEN_MAX_X, SCREEN_MIN_Y);
        g.drawLine(SCREEN_MIN_X, SCREEN_MIN_Y, SCREEN_MIN_X, SCREEN_MAX_Y);

    }
    
    public Component getComponent() {
        return this.renderPanel;
    }
    
    public void refresh() {
        this.renderPanel.repaint();
    }
    
    private void onMouseClicked(int x, int y) {
    }
    
    /**
     * Render panel
     */
    private class RenderPanel extends Canvas implements MouseListener {
        
        public RenderPanel() {
            addMouseListener(this);
        }
        
        @Override
        public void paint(Graphics g){
                update(g);
	}
        
        @Override
        public void update(Graphics g) {
            
            if(renderImg == null) {
                graphDim = getSize();
                renderImg = (BufferedImage)createImage(graphDim.width, graphDim.height);
                renderG = renderImg.createGraphics();
                init();
            }
            
            renderG.setPaint(Color.WHITE);
            renderG.fillRect(0, 0, graphDim.width, graphDim.height);
            
            render(renderG);
            
            g.drawImage(renderImg, 0, 0, this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            onMouseClicked(e.getX(), e.getY());
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
