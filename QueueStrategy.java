// every queue mode (strategy) follows this
public interface QueueStrategy {

    // true if patient a should be seen before patient b
    boolean goesBefore(Patient a, Patient b);

    String getName();
}
