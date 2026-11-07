public class QueueManager {

    private Patient[] queue = new Patient[1];
    private int size = 0;
    private int nextTicket = 1;
    private QueueStrategy strategy;

    public QueueManager(QueueStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(QueueStrategy strategy) {
        this.strategy = strategy;
        sortQueue();
    }

    public String getStrategyName() {
        return strategy.getName();
    }

    public void enqueue(Patient p) {
        if (size == queue.length) {
            Patient[] temp = new Patient[queue.length + 1];
            for (int i = 0; i < queue.length; i++) {
                temp[i] = queue[i];
            }
            queue = temp;
        }
        p.ticket = nextTicket;
        nextTicket++;
        p.onHold = false;
        queue[size] = p;
        size++;
        sortQueue();
    }

    public Patient dequeue() {
        if (isEmpty()) {
            return null;
        }
        Patient front = queue[0];
        removeAt(0);
        return front;
    }

    public boolean remove(Patient p) {
        int index = findIndex(p);
        if (index == -1) {
            return false;
        }
        removeAt(index);
        return true;
    }
    public void hold(Patient p) {
        if (findIndex(p) == -1) {
            return;
        }
        p.onHold = true;
        p.ticket = nextTicket;
        nextTicket++;
        sortQueue();
    }

    public Patient peek() {
        if (isEmpty()) {
            return null;
        }
        return queue[0];
    }

    
    public Patient getPatient(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return queue[index];
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
            String note = "";
            if (queue[i].onHold) {
                note = " [on hold]";
            }
            System.out.println((i + 1) + ". " + queue[i].Name + " (Age: " + queue[i].Age + ") - "
                    + queue[i].Symptoms + " (Severity: " + queue[i].Severity + ")" + note);
        }
    }

    private int findIndex(Patient p) {
        for (int i = 0; i < size; i++) {
            if (queue[i] == p) {
                return i;
            }
        }
        return -1;
    }

    private void removeAt(int index) {
        queue[index].onHold = false;
        for (int i = index; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }
        queue[size - 1] = null;
        size--;
    }

    private boolean before(Patient a, Patient b) {
        if (a.onHold != b.onHold) {
            return !a.onHold;
        }
        if (a.onHold) {
            return a.ticket < b.ticket;   // held patients: first held, first back in
        }
        return strategy.goesBefore(a, b);
    }
    private void sortQueue() {
        for (int i = 1; i < size; i++) {
            Patient key = queue[i];
            int j = i - 1;
            while (j >= 0 && before(key, queue[j])) {
                queue[j + 1] = queue[j];
                j--;
            }
            queue[j + 1] = key;
        }
    }
}
