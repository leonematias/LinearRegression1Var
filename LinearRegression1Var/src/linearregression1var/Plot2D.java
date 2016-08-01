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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Tool to plot 2D graphics
 * 
 * @author Matias Leone
 */
public class Plot2D {
    
    private final static int POINT_RAD = 5;
    private final static int X_OFFSET = 40;
    private final static int Y_OFFSET = 30;
    private final static float SCALE = 10;
    
    private Stroke normalStroke;
    private Stroke boldStroke;
    private Stroke dottedStroke;
    private RenderPanel renderPanel;
    private BufferedImage renderImg;
    private Graphics2D renderG;
    private Dimension graphDim;                 
    
    private int screenMinX;
    private int screenMinY;
    private int screenMaxX;
    private int screenMaxY;

    
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
        this.screenMinX = X_OFFSET;
        this.screenMinY = this.graphDim.height - Y_OFFSET;
        this.screenMaxX = this.graphDim.width - X_OFFSET;
        this.screenMaxY = Y_OFFSET;
        
    }
    
    public void reset() {
        Graphics2D g = renderG;
        
        //Clear
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, graphDim.width, graphDim.height);
        
        //Draw axis
        g.setColor(Color.BLACK);
        g.setStroke(boldStroke);
        g.drawLine(screenMinX, screenMinY, screenMaxX, screenMinY);
        g.drawLine(screenMinX, screenMinY, screenMinX, screenMaxY);
        
        //Draw arrows on axis
        g.drawLine(screenMaxX, screenMinY, screenMaxX - 10, screenMinY - 5);
        g.drawLine(screenMaxX, screenMinY, screenMaxX - 10, screenMinY + 5);
        g.drawLine(screenMinX, screenMaxY, screenMinX - 5, screenMaxY + 10);
        g.drawLine(screenMinX, screenMaxY, screenMinX + 5, screenMaxY + 10);
        
        //Draw X labels
        for (int i = 1; i < 22; i++) {
            int x = screenMinX + i * 50;
            g.drawLine(x, screenMinY + 5, x, screenMinY - 5);
            g.drawString(String.valueOf(i * 5), x - 10, screenMinY + 20);
        }
        
        //Draw Y labels
        for (int i = 1; i < 11; i++) {
            int y = screenMinY - i * 50;
            g.drawLine(screenMinX - 5, y, screenMinX + 5, y);
            g.drawString(String.valueOf(i * 5), screenMinX - 30, y + 5);
        }
    }
     

    public void render() {
        renderPanel.repaint();
    }
    
    public void drawPoint(float x, float y, Color color, int size) {
        Graphics2D g = renderG;
        g.setColor(color);
        g.setStroke(normalStroke);
        g.fillOval(xToScreen(x) - size, yToScreen(y) - size, size * 2, size * 2);
    }
    
    public void drawPoint(float x, float y) {
        drawPoint(x, y, Color.BLUE, POINT_RAD);
    }
    
    public void drawPoint(Vector2 p, Color color, int size) {
        drawPoint(p.X, p.Y, color, size);
    }
    
    public void drawPoint(Vector2 p) {
        drawPoint(p, Color.BLUE, POINT_RAD);
    }
    
    public void drawSegment(float startX, float startY, float endX, float endY, Color color) {
        Graphics2D g = renderG;
        g.setColor(color);
        g.setStroke(boldStroke);
        g.drawLine(xToScreen(startX), yToScreen(startY), xToScreen(endX), yToScreen(endY));
    }
    
    public void drawSegment(float startX, float startY, float endX, float endY) {
        drawSegment(startX, startY, endX, endY, Color.GREEN);
    }
    
    public void drawSegment(Vector2 start, Vector2 end) {
        drawSegment(start.X, start.Y, end.X, end.Y);
    }
    
    public void drawSegment(Vector2 start, Vector2 end, Color color) {
        drawSegment(start.X, start.Y, end.X, end.Y, color);
    }
    
    public void drawLine(float m, float b) {
        drawLine(m, b, Color.GREEN);
    }
    
    /**
     * y = mx + b
     */
    public void drawLine(float m, float b, Color color) {
        float startX = 0;
        float startY = b;
        float endX = screenMaxX;
        float endY = m * endX + b;
        drawSegment(startX, startY, endX, endY, color);
    }
    
    private int xToScreen(float x) {
        return (int)(x * SCALE + X_OFFSET);
    }
    
    private int yToScreen(float y) {
        return (int)(screenMinY - y * SCALE);
    }
    
    
    public Component getComponent() {
        return this.renderPanel;
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
