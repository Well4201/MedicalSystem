public class Doctor {
    private String password;
    private String username;
    private String[] patients = new String[1];
    private int expertiseChoice = 1;

    public String expertise() {
        switch (expertiseChoice) {
            case 1:
                return "Eyes Specialist";
            case 2:
                return "Dental Specialist";
            case 3:
                return "Physical Examiner";
            default:
                throw new IllegalStateException("Invalid doctor expertise choice.");
        }
    }

    public void setExpertiseChoice(int expertiseChoice) {
        if (expertiseChoice < 1 || expertiseChoice > 3) {
            throw new IllegalArgumentException("Expertise choice must be 1, 2, or 3.");
        }
        this.expertiseChoice = expertiseChoice;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}