
public class FirstComeFirstServedStrategy implements QueueStrategy {
    public boolean goesBefore(Patient a, Patient b) {
        return a.ticket < b.ticket;
    }
    public String getName() {
        return "First Come First Served";
    }
}
