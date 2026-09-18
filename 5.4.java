class MovieBookingProfile {

    private String name;
    private boolean confirmed;
    private String otp;

    // No-argument constructor
    public MovieBookingProfile() {
        this.name = "";
        this.confirmed = false;
    }

    // Convenience constructor
    public MovieBookingProfile(String name) {
        this();
        this.name = name;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Boolean getter
    public boolean isConfirmed() {
        return confirmed;
    }

    // Setter for confirmed
    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    // Write-only OTP
    public void setOtp(String otp) {

        if (otp != null &&
            otp.matches("\\d{4,6}")) {

            this.otp = otp;
        }
    }
}

public class Main {

    public static void main(String[] args) {

        MovieBookingProfile p =
            new MovieBookingProfile("Rahul Dev");

        System.out.println(p.getName());

        p.setConfirmed(true);

        System.out.println(p.isConfirmed());

        p.setOtp("4471");

        System.out.println("OTP set successfully");
    }
}