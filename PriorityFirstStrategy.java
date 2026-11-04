// most severe first (3, then 2, then 1). same severity = who came first
public class PriorityFirstStrategy implements QueueStrategy {

    @Override
    public boolean goesBefore(Patient a, Patient b) {
        if (a.Severity != b.Severity) {
            return a.Severity > b.Severity;
        }
        return a.ticket < b.ticket;
    }

    @Override
    public String getName() {
        return "Priority First";
    }
}
