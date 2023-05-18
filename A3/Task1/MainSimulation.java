import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{


	public static void run() {
		
	}
    public static void main(String[] args) throws IOException {

		//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
		// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
		// signal list in the main loop below.

		Signal actSignal;
		new SignalList();

		Properties conf = new Properties();

		try {
			String path = "configs/2000.conf";
			FileInputStream file = new FileInputStream(path);
			conf.load(file);
			file.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		int n = Integer.parseInt(conf.getProperty("n"));
		Double T_p = Double.parseDouble(conf.getProperty("T_p"));
		Double t_s = Double.parseDouble(conf.getProperty("t_s"));
		Double r = Double.parseDouble(conf.getProperty("r")) * 1000;
		r = 11000.0;
		
		//Used for uniform sleep in Strat2
		double lb = 1000, ub = 10000;

		Gateway gateway = new Gateway();
		Sensor[] sensors = new Sensor[n];
		for (int i = 0; i < n; i++) {
			String[] point = conf.getProperty("point_" + Integer.toString(i)).split(" ");
			Sensor sensor = new Sensor(Double.parseDouble(point[0]), Double.parseDouble(point[0]), r, t_s, T_p, gateway, lb, ub);
			SignalList.SendSignal(SENSE_CHANNEL, null, sensor, time + sensor.expDistribution());
			sensors[i] = sensor;
		}

		SignalList.SendSignal(MEASURE, null, gateway, time+10);

		System.out.println("lb = " + lb + ", ub = " + ub);
		System.out.println("n = " + n + ", T_p = " +  T_p + ", t_s = " + t_s + ", Radius = " + r + "\n");

    	while (time < 10000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}
		String througput = String.format("%.4f", gateway.succSignals/time);
    	System.out.println("Througput: " + througput);
		double lambda = (gateway.totSignals/time);
		double T_put = 1 * lambda * Math.exp(-2*lambda);
		double packetLoss = 1 - (gateway.succSignals / gateway.totSignals);
		System.out.println("T_put = " + String.format("%.4f", T_put));
		System.out.println("Packet loss: " + String.format("%.4f", packetLoss));
		System.out.println("Confidence interval: " + gateway.confInterval);
		System.out.println(time + " " + gateway.succSignals + " " + gateway.totSignals);
    
		SimpleFileWriter fw = new SimpleFileWriter("vary_r", true);
		fw.println(througput + " " + packetLoss +  " " + gateway.confInterval);
		fw.close();
	}	
}