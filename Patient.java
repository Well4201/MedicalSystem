public class Patient {

    public String Name;
    public int Age;
    public String Symptoms;
    public int Severity;

    public Patient() {
    }

    public Patient(String name, int age, String symptoms, int severity) {
        this.Name = name;
        this.Age = age;
        this.Symptoms = symptoms;
        this.Severity = severity;
    }
}