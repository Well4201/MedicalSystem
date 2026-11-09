// Added from Payment-and-testing.
public class Payment {

    private String patientName;
    private double total;
    private double paid;
    private String paymentPlan;

    public Payment(String patientName, double total) {
        this.patientName = patientName;
        this.total = total;
        this.paid = 0;
        this.paymentPlan = "Not selected";
    }

    public static double getFee(int severity) {
        if (severity == 3) {
            return 1000;
        } else if (severity == 2) {
            return 600;
        }
        return 300;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public String getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(String paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

    public void payFull() {
        paid = total;
        paymentPlan = "Pay in full";
    }

    public boolean payInstallment(double amount) {
        if (amount <= 0 || amount > getBalance()) {
            return false;
        }
        paid = paid + amount;
        paymentPlan = "Pay in installments";
        return true;
    }

    public double getBalance() {
        return Math.round((total - paid) * 100) / 100.0;
    }

    public boolean isSettled() {
        return getBalance() == 0;
    }

    public void printReceipt() {
        System.out.println("\n====== RECEIPT ======");
        System.out.println("Patient : " + patientName);
        System.out.println("Plan    : " + paymentPlan);
        System.out.printf("Total   : %.2f%n", total);
        System.out.printf("Paid    : %.2f%n", paid);
        System.out.printf("Balance : %.2f%n", getBalance());
        System.out.println("=====================");
    }
}
