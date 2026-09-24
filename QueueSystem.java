public class QueueSystem {

    private Patient[] queue = new Patient[1];
    private int size = 0;

    public void enqueue(Patient p) {
        if (size == queue.length) {
            Patient[] temp = new Patient[queue.length + 1];
            for (int i = 0; i < queue.length; i++) {
                temp[i] = queue[i];
            }
            queue = temp;
        }
        int insertAt = size;
        while (insertAt > 0 && queue[insertAt - 1].Severity < p.Severity) {
            queue[insertAt] = queue[insertAt - 1];
            insertAt--;
        }
        queue[insertAt] = p;
        size++;
    }

    public Patient dequeue() {
        if (isEmpty()) {
            return null;
        }
        Patient front = queue[0];
        for (int i = 0; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }
        queue[size - 1] = null;
        size--;
        return front;
    }

    public Patient peek() {
        if (isEmpty()) {
            return null;
        }
        return queue[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void printQueueCount() {
        System.out.println("There are " + size + " patient(s) currently waiting.");
    }

    public void printQueue() {
        if (isEmpty()) {
            System.out.println("The queue is currently empty.");
            return;
        }
        System.out.println("Current patient queue:");
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + queue[i].Name + " (Age: " + queue[i].Age + ") - "
                    + queue[i].Symptoms + " (Severity: " + queue[i].Severity + ")");
        }
    }
}