class GymMember {

    private static int membersEnrolled = 0;

    private final String membershipNumber;

    protected int monthlyFee;
    protected int sessionsAttended;

    private int feesPaid;

    public GymMember(int monthlyFee) {

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException(
                "Monthly fee must be positive"
            );
        }

        this.monthlyFee = monthlyFee;
        this.sessionsAttended = 0;
        this.feesPaid = 0;

        membersEnrolled++;

        membershipNumber =
            "GYM-" + (2000 + membersEnrolled);
    }

    public void payFee(int amount) {

        if (amount > 0) {
            feesPaid += amount;
        }
    }

    public void payFee(int amount, String mode) {

        System.out.println(
            "Payment mode: " + mode
        );

        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public String getMembershipNumber() {
        return membershipNumber;
    }

    public static boolean isValidReferralCode(
            String code) {

        if (code == null || code.length() != 4) {
            return false;
        }

        // First character must be G
        if (code.charAt(0) != 'G') {
            return false;
        }

        // Second character must be a digit
        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        // Third character must be a digit
        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        // Fourth character must be uppercase
        if (!Character.isUpperCase(code.charAt(3))) {
            return false;
        }

        return true;
    }

    public static int getMembersEnrolled() {
        return membersEnrolled;
    }
}

class GroupClassMember extends GymMember {

    private String className;

    public GroupClassMember(
            int monthlyFee,
            String className) {

        super(monthlyFee);
        this.className = className;
    }
}

public class Main {

    public static String processWeeklyCheckIn(
            GymMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {

            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
             + nullSkipped + " null skipped | "
             + group + " group | "
             + individual + " individual";
    }

    public static void main(String[] args) {

        GymMember m1 =
            new GymMember(1000);

        System.out.println(
            m1.getMembershipNumber()
        );

        System.out.println(
            GymMember.getMembersEnrolled()
        );

        System.out.println(
            GymMember.isValidReferralCode("G45B")
        );

        System.out.println(
            GymMember.isValidReferralCode("G4B")
        );

        System.out.println(
            GymMember.isValidReferralCode("X45B")
        );

        m1.payFee(500);

        m1.payFee(500, "UPI");

        System.out.println(
            m1.getFeesPaid()
        );

        GymMember[] members = {

            new GroupClassMember(
                1500,
                "Zumba"
            ),

            null,

            new GymMember(1000)
        };

        System.out.println(
            processWeeklyCheckIn(members)
        );
    }
}