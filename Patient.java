public class Patient {

    // counts how many patients have registered
    private static int patientCounter = 0;

    private String patientId;
    private String Name;
    private int Age;
    private String Symptoms;
    // Added for triage in Registration-and-priority.
    private int Severity;
    private boolean isSenior;
    private boolean isPWD;
    private boolean isPregnant;
    private int arrivalOrder;
    private String suggestedSpecialty;
    private Payment payment;
    // Added by Queue-strategies.
    private int ticket;
    private boolean onHold;
    // Added by appointments.
    private Appointment appointment;

    public Patient() {
        assignIdAndOrder();
        this.payment = new Payment(this.Name, Payment.getFee(this.Severity));
    }

    public Patient(String name, int age, String symptoms, int severity) {
        this.Name = name;
        this.Age = age;
        this.Symptoms = symptoms;
        this.Severity = severity;
        this.isSenior = age >= 60;
        assignIdAndOrder();
        this.payment = new Payment(this.Name, Payment.getFee(this.Severity));
    }

    public Patient(String name, int age, String symptoms, int severity,
                   boolean isSenior, boolean isPWD, boolean isPregnant,
                   String suggestedSpecialty) {
        this.Name = name;
        this.Age = age;
        this.Symptoms = symptoms;
        this.Severity = severity;
        this.isSenior = isSenior;
        this.isPWD = isPWD;
        this.isPregnant = isPregnant;
        this.suggestedSpecialty = suggestedSpecialty;
        assignIdAndOrder();
        this.payment = new Payment(this.Name, Payment.getFee(this.Severity));
    }

    private void assignIdAndOrder() {
        patientCounter++;
        this.arrivalOrder = patientCounter;
        this.patientId = String.format("P%03d", patientCounter);
    }

    // yes or no if the patient is a priority
    public boolean isPriority() {
        return isSenior || isPWD || isPregnant;
    }

    // getters
    public String getPatientId() { return patientId; }
    public String getName() { return Name; }
    public int getAge() { return Age; }
    public String getSymptoms() { return Symptoms; }
    public int getSeverity() { return Severity; }
    public boolean isSenior() { return isSenior; }
    public boolean isPWD() { return isPWD; }
    public boolean isPregnant() { return isPregnant; }
    public int getArrivalOrder() { return arrivalOrder; }
    public String getSuggestedSpecialty() { return suggestedSpecialty; }
    public Payment getPayment() { return payment; }
    public int getTicket() { return ticket; }
    public boolean isOnHold() { return onHold; }
    public Appointment getAppointment() { return appointment; }

    // setters
    public void setName(String name) {
        this.Name = name;
        if (this.payment != null) {
            this.payment.setPatientName(name);
        }
    }
    public void setAge(int age) { this.Age = age; }
    public void setSymptoms(String symptoms) { this.Symptoms = symptoms; }
    public void setSeverity(int severity) {
        this.Severity = severity;
        if (this.payment != null) {
            this.payment.setTotal(Payment.getFee(severity));
        }
    }
    public void setSenior(boolean isSenior) { this.isSenior = isSenior; }
    public void setPWD(boolean isPWD) { this.isPWD = isPWD; }
    public void setPregnant(boolean isPregnant) { this.isPregnant = isPregnant; }
    public void setSuggestedSpecialty(String specialty) { this.suggestedSpecialty = specialty; }
    public void setPayment(Payment payment) { this.payment = payment; }
    public void setTicket(int ticket) { this.ticket = ticket; }
    public void setOnHold(boolean onHold) { this.onHold = onHold; }
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }
}
