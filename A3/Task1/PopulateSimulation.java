import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Random;
import java.awt.geom.Point2D;

public class PopulateSimulation {
    private final static String CONF_PATH = "configs/.config";
    private static Properties conf;
    private static Random rand = new Random();
    
    public static void main(String[] args) {
        try {
            conf = new Properties();
            FileInputStream file = new FileInputStream(CONF_PATH);
            conf.load(file);
            file.close();

            for (String n : conf.getProperty("n").split(" ")) {
                FileWriter fw = new FileWriter("configs/" + n + ".conf");
                fw.write("n=" + n + "\n");
                fw.write("T_p=" + conf.getProperty("T_p") + "\n");
                fw.write("t_s=" + conf.getProperty("t_s") + "\n");
                fw.write("r=" + conf.getProperty("r") + "\n");
                int n_int = Integer.parseInt(n);
                HashSet<Point2D.Double> coordinates = generateCoordinates(n_int);
                Iterator<Point2D.Double> itr = coordinates.iterator();
                int i = 0;
                while(itr.hasNext()) {
                    Point2D.Double point = itr.next();
                    fw.write("point_" + i + "=");
                    fw.write(point.getX() + " " + point.getY() + "\n");
                    i++;
                }
                fw.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static HashSet<Point2D.Double> generateCoordinates(int n) {
        HashSet<Point2D.Double> coordinates = new HashSet<Point2D.Double>();
        while (coordinates.size() < n) {
            double x = rand.nextDouble()*10000;
            double y = rand.nextDouble()*10000;
            Point2D.Double xy = new Point2D.Double(x, y);
            coordinates.add(xy);
        }
        return coordinates;
    }
}
