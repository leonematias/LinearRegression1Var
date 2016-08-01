package linearregression1var;

import java.util.ArrayList;
import java.util.List;

/**
 * Linear regression for one variable with gradient descend
 * 
 * @author Matias Leone
 */
public class LinearRegression {
    
    private float rangeX;
    private float rangeY;
    private float avgX;
    private float avgY;
    
    public LinearRegression() {
        
    }
    
    public Vector2 computeLine(List<Vector2> points, float alpha, int iterations) {
        
        Vector2 theta = new Vector2(0.1f, 0.1f);
        //List<Vector2> scaledPoints = featureScaling(points);
        
        float m = points.size();
        float alphaDivM = alpha / m;
        for (int i = 0; i < iterations; i++) {
            
            float diff0 = 0;
            float diff1 = 0;
            for (Vector2 point : points) {
                float diff = h(point.X, theta) - point.Y;
                diff0 += diff;
                diff1 += diff * point.X;
            }
            
            theta.X = theta.X - (alphaDivM * diff0);
            theta.Y = theta.Y - (alphaDivM * diff1);
        }
        
 
        //theta.X = scaleBackX(theta.X);
        //theta.Y = scaleBackX(theta.Y);
        //theta.Y *= 10;
        
        return theta;
    }
    
    private List<Vector2> featureScaling(List<Vector2> points) {
        List<Vector2> scaledPoints = new ArrayList<>(points.size());
        
        float minX = Float.MAX_VALUE;
        float maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;
        float maxY = Float.MIN_VALUE;
        float sumX = 0;
        float sumY = 0;
        for (Vector2 p : points) {
            sumX += p.X;
            sumY += p.Y;
            if(p.X < minX) {
                minX = p.X;
            }
            if(p.Y < minY) {
                minY = p.Y;
            }
            if(p.X > maxX) {
                maxX = p.X;
            }
            if(p.Y > maxY) {
                maxY = p.Y;
            }
        }
        
        rangeX = maxX - minX;
        rangeY = maxY - minY;
        avgX = sumX / points.size();
        avgY = sumY / points.size();
        for (Vector2 p : points) {
            scaledPoints.add(new Vector2(scaleX(p.X), scaleY(p.Y)));
        }
        
        return scaledPoints;
    }
    
    private float scaleX(float x) {
        return (x - avgX) / rangeX;
    }
    
    private float scaleY(float y) {
        return (y - avgY) / rangeY;
    }
    
    private float scaleBackX(float x) {
        return (x * rangeX) + avgX;
    }
    
    private float scaleBackY(float y) {
        return (y * rangeY) + avgY;
    }
    
    private float h(float x, Vector2 theta) {
        return theta.X + theta.Y * x;
    }
    
}
