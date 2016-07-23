package linearregression1var;

import java.util.List;

/**
 * Linear regression for one variable with gradient descend
 * 
 * @author Matias Leone
 */
public class LinearRegression {
    
    public LinearRegression() {
        
    }
    
    public Vector2 Vector2(List<Vector2> points, float alpha, int iterations) {
        
        Vector2 theta = new Vector2(0, 0);
        
        int m = points.size();
        for (int i = 0; i < iterations; i++) {
            
            float diff0 = 0;
            float diff1 = 0;
            for (Vector2 point : points) {
                float diff = h(point.X, theta) - point.Y;
                diff0 += diff;
                diff1 += diff * point.X;
            }
            
            theta.X = theta.X - alpha * (1/m) * diff0;
            theta.Y = theta.Y - alpha * (1/m) * diff1;
        }
        
        
        return theta;
    }
    
    private float h(float x, Vector2 theta) {
        return theta.X + theta.Y * x;
    }
    
}
