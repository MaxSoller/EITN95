import java.util.Random;

public class Gateway extends Proc{
    
    public int totSignals, succSignals, failSignals, nbrMeasurments;
    public Proc sendTo;
    private Random rand = new Random();

    public void TreatSignal(Signal x) {
        switch (x.signalType) {
            case RECEIVING:
                break;
            case RECEIVED:
                break;
            case MEASURE:
                break;
        }
    }
    
}
