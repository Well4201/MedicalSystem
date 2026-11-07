// Added from Queue-strategies.
public class PriorityFirstStrategy implements QueueStrategy {

    
    public boolean goesBefore(Patient a, Patient b) {
        if (a.getSeverity() != b.getSeverity()) {
            return a.getSeverity() > b.getSeverity();
        }
        return a.getTicket() < b.getTicket();
    }
    public String getName() {
        return "Priority First";
    }
}
