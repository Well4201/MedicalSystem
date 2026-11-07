// Added from Queue-strategies.
public interface QueueStrategy {
    boolean goesBefore(Patient a, Patient b);

    String getName();
}
