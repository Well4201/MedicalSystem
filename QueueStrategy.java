public interface QueueStrategy {
    boolean goesBefore(Patient a, Patient b);

    String getName();
}
