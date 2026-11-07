
// Added from Queue-strategies.
public class EmergencyFirstStrategy implements QueueStrategy {
    public boolean goesBefore(Patient a, Patient b) {
        boolean aEmergency = a.getSeverity() == 3;
        boolean bEmergency = b.getSeverity() == 3;

        if (aEmergency != bEmergency) {
            return aEmergency;
        }
        return a.getTicket() < b.getTicket();
    }
    public String getName() {
        return "Emergency First";
    }
}
