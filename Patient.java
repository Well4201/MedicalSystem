public class Patient {

    // counts how many patients have registered
    private static int patientCounter = 0;

    private String patientId;
    private String Name;
    private int Age;
    private String Symptoms;
    private int Severity;
    private boolean isSenior;
    private boolean isPWD;
    private boolean isPregnant;
    private int arrivalOrder;
    private String suggestedSpecialty;

    public Patient() {
        assignIdAndOrder();
    }

    public Patient(String name, int age, String symptoms, int severity) {
        this.Name = name;
        this.Age = age;
        this.Symptoms = symptoms;
        this.Severity = severity;
        this.isSenior = age >= 60;
        assignIdAndOrder();
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

    // setters
    public void setName(String name) { this.Name = name; }
    public void setAge(int age) { this.Age = age; }
    public void setSymptoms(String symptoms) { this.Symptoms = symptoms; }
    public void setSeverity(int severity) { this.Severity = severity; }
    public void setSenior(boolean isSenior) { this.isSenior = isSenior; }
    public void setPWD(boolean isPWD) { this.isPWD = isPWD; }
    public void setPregnant(boolean isPregnant) { this.isPregnant = isPregnant; }
    public void setSuggestedSpecialty(String specialty) { this.suggestedSpecialty = specialty; }
}
