
// Added from Queue-strategies.
public class FirstComeFirstServedStrategy implements QueueStrategy {
    public boolean goesBefore(Patient a, Patient b) {
        return a.getTicket() < b.getTicket();
    }
    public String getName() {
        return "First Come First Served";
    }
}
