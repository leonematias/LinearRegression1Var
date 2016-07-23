package linearregression1var;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

/**
 *
 * @author matias.leone
 */
public class UI {

    private final static int WIN_WIDTH = 1200;
    private final static int WIN_HEIGHT = 720;
    
    private final static int SCREEN_MIN_X = 20;
    private final static int SCREEN_MIN_Y = WIN_HEIGHT - 150;
    private final static int SCREEN_MAX_X = WIN_WIDTH - 20;
    private final static int SCREEN_MAX_Y = 20;

    private final static int POINTS_COUNT = 500;
    
    private JFrame frame;
    private RenderPanel renderPanel;
    private BufferedImage renderImg;
    private Graphics2D renderG;
    private Dimension graphDim;
    private JTextArea logArea;
    private JSpinner spinner;
    private Stroke normalStroke;
    private Stroke boldStroke;
    private Stroke dottedStroke;
    
    public static void main(String[] args) {
        new UI();
    }
    
    public UI() {
        
        normalStroke = new BasicStroke();
        boldStroke = new BasicStroke(2);
        dottedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
        
        frame = new JFrame("Linear Regression - Gradient Descent");
        frame.setMinimumSize(new Dimension(WIN_WIDTH, WIN_HEIGHT));
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        JPanel controlsPanel = new JPanel(new FlowLayout());
        JButton button = new JButton("Restart");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doRegression();
            }
        });
        controlsPanel.add(button);
        frame.add(controlsPanel, BorderLayout.NORTH);
        
        renderPanel = new RenderPanel();
        frame.add(renderPanel, BorderLayout.CENTER);
        
        logArea = new JTextArea(4, 100);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setMinimumSize(new Dimension(-1, 200));
        frame.add(scrollPane, BorderLayout.SOUTH);
        
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenDim.width / 2 - WIN_WIDTH / 2, screenDim.height / 2 - WIN_HEIGHT / 2);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        frame.setVisible(true);
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
    
    /**
     * Compute clusters
     */
    private void doRegression() {
        int clustersCount = ((SpinnerNumberModel)spinner.getModel()).getNumber().intValue();
        
        renderPanel.repaint();
    }
    
    private void onMouseClicked(int x, int y) {
    }
    
    private void log(String txt) {
        logArea.append(txt);
        logArea.append("\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
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
