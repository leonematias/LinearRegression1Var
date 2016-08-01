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
import java.awt.event.WindowAdapter;
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

    private final static int POINTS_COUNT = 500;
    
    private JFrame frame;
    private Plot2D plot2d;
    private JTextArea logArea;
    private LinearRegression linearRegression;
    
    public static void main(String[] args) {
        new UI();
    }
    
    public UI() {
        linearRegression = new LinearRegression();
        
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
        
        plot2d = new Plot2D(WIN_WIDTH, WIN_HEIGHT);
        frame.add(plot2d.getComponent(), BorderLayout.CENTER);
        
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
     * Compute clusters
     */
    private void doRegression() {
        plot2d.reset();
        clearLog();
        
        /*
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            plot2d.drawPoint(new Vector2(rand.nextInt(1000), rand.nextInt(500)));
        }
        
        plot2d.drawSegment(new Vector2(0, 50), new Vector2(300, 350), Color.MAGENTA);
        plot2d.drawLine(1, 0, Color.RED);
        plot2d.drawLine(0.1f, 200, Color.GRAY);
        plot2d.drawLine(-20, 600, Color.GREEN);
        plot2d.drawLine(10, 300, Color.ORANGE);
                */
        
        float m = -0.5f;
        float b = 500;
        log("Original line: y = " + m + "x + " + b);
        List<Vector2> points = getRandomPointsCloseToLine(m, b, 1050, 50, 1000);
        for (Vector2 point : points) {
            plot2d.drawPoint(point);
        }
        
        Vector2 line = linearRegression.computeLine(points, 0.0001f, 10000);
        plot2d.drawLine(line.Y, line.X, Color.RED);
        log("Regression line: y = " + line.Y + "x + " + line.X);
        
        plot2d.render();
    }
    
    private List<Vector2> getRandomPointsCloseToLine(float m, float b, float maxX, float maxVariance, int count) {
        List<Vector2> points = new ArrayList<>(count);
        float halfVar = maxVariance / 2;
        float doubleVar = maxVariance * 2;
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            float x = r.nextFloat() * maxX;
            float y = m * x + b;
            float var = -halfVar + r.nextFloat() * doubleVar;
            y += var;
            
            points.add(new Vector2(x, y));
        }
        return points;
    }
    
    
    private void clearLog() {
        logArea.setText("");
        logArea.setCaretPosition(0);
    }
    
    private void log(String txt) {
        logArea.append(txt);
        logArea.append("\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    
    
    
}
