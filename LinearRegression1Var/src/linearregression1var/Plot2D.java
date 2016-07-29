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
    
    private final static int POINT_RAD = 2;
    
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
    
    private List<Vector2> points;
    private List<Line> lines;
    
    public Plot2D(int width, int height) {
        normalStroke = new BasicStroke();
        boldStroke = new BasicStroke(2);
        dottedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
        
        renderPanel = new RenderPanel();
        this.points = new ArrayList<>();
        this.lines = new ArrayList<>();
    }
    
    /**
     * Init graphics
     */
    private void init() {
        this.screenMinX = 40;
        this.screenMinY = this.graphDim.height - 40;
        this.screenMaxX = this.graphDim.width - 20;
        this.screenMaxY = 20;
        
    }
    
    public void drawPoint(Vector2 p) {
        this.points.add(p);
    }
    
    public void drawLine(Vector2 start, Vector2 end) {
        this.lines.add(new Line(start, end));
    }
    
    public void clear() {
        this.points.clear();
        this.lines.clear();
        refresh();
    }
     
    /**
     * Main render method
     */
    private void render(Graphics2D g) {

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
        for (int i = 1; i < 12; i++) {
            int x = screenMinX + i * 100;
            g.drawLine(x, screenMinY + 5, x, screenMinY - 5);
            g.drawString(String.valueOf(i * 100), x, screenMinY + 15);
        }
        
        //Draw Y labels
        for (int i = 1; i < 6; i++) {
            int y = screenMinY - i * 100;
            g.drawLine(screenMinX - 5, y, screenMinX + 5, y);
            g.drawString(String.valueOf(i * 100), screenMinX - 15, y - 5);
        }
        
        //Draw points
        g.setColor(Color.BLUE);
        g.setStroke(normalStroke);
        for (Vector2 p : points) {
            g.fillOval((int)(p.X - POINT_RAD), (int)(p.Y - POINT_RAD), POINT_RAD * 2, POINT_RAD * 2);
        }

        //Draw lines
        g.setColor(Color.GREEN);
        g.setStroke(boldStroke);
        for (Line l : lines) {
            g.drawLine((int)l.start.X, (int)l.start.Y, (int)l.end.X, (int)l.end.Y);
        }
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
    
    private class Line {
        public Vector2 start;
        public Vector2 end;

        public Line(Vector2 start, Vector2 end) {
            this.start = start;
            this.end = end;
        }
        
        
    }
}
