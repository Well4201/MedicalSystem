// severity 3 (emergency) jumps the line, everybody else is first come first served
public class EmergencyFirstStrategy implements QueueStrategy {

    @Override
    public boolean goesBefore(Patient a, Patient b) {
        boolean aEmergency = a.Severity == 3;
        boolean bEmergency = b.Severity == 3;

        if (aEmergency != bEmergency) {
            return aEmergency;
        }
        return a.ticket < b.ticket;
    }

    @Override
    public String getName() {
        return "Emergency First";
    }
}
