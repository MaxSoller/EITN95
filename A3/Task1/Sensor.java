import java.util.Random;

public class Sensor extends Proc{
    private double lambda, x, y, radius, T_p;
    private Random rand = new Random();
    public Proc sendTo;


    public Sensor(double x, double y, double T_p, double ts, double r) {
        this.x = x;
        this.y = y;
        this.T_p = T_p;
        this.lambda = ts;
        this.radius = r;
    }

    public void TreatSignal(Signal x) {
        switch(x.signalType) {
            
        }
    }

    public double expDistribution() {
		return - lambda * Math.log(1-rand.nextDouble());
	}
}
