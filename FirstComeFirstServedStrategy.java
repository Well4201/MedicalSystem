// just the order they joined, severity doesnt matter
public class FirstComeFirstServedStrategy implements QueueStrategy {

    @Override
    public boolean goesBefore(Patient a, Patient b) {
        return a.ticket < b.ticket;
    }

    @Override
    public String getName() {
        return "First Come First Served";
    }
}
