
public class EmergencyFirstStrategy implements QueueStrategy {

    
    public boolean goesBefore(Patient a, Patient b) {
        boolean aEmergency = a.Severity == 3;
        boolean bEmergency = b.Severity == 3;

        if (aEmergency != bEmergency) {
            return aEmergency;
        }
        return a.ticket < b.ticket;
    }

    
    public String getName() {
        return "Emergency First";
    }
}
