public class PriorityFirstStrategy implements QueueStrategy {

    
    public boolean goesBefore(Patient a, Patient b) {
        if (a.Severity != b.Severity) {
            return a.Severity > b.Severity;
        }
        return a.ticket < b.ticket;
    }
    public String getName() {
        return "Priority First";
    }
}
