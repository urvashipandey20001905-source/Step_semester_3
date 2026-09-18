import java.util.Arrays;

class GymMember {

    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    private int[] lateFeeHistory;
    private int feeCount;

    public GymMember(String memberId, int monthlyFee) {

        if (memberId == null || memberId.trim().isEmpty()
                || memberId.length() < 4) {
            throw new IllegalArgumentException("Invalid Member ID");
        }

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException(
                "Monthly fee must be positive"
            );
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
        this.sessionsAttended = 0;

        lateFeeHistory = new int[10];
        feeCount = 0;
    }

    protected void chargeLateFee(int amount) {

        if (feeCount < lateFeeHistory.length) {
            lateFeeHistory[feeCount] = amount;
            feeCount++;
        }
    }

    public int[] getLateFeeHistory() {

        return Arrays.copyOf(lateFeeHistory, feeCount);
    }

    public int getTotalLateFees() {

        int total = 0;

        for (int i = 0; i < feeCount; i++) {
            total += lateFeeHistory[i];
        }

        return total;
    }
}

class PremiumMember extends GymMember {

    private String trainerName;

    public PremiumMember(String memberId, int monthlyFee,
                          String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    protected void chargeLateFee(int amount) {

        super.chargeLateFee(amount / 2);
    }
}

public class Main {

    public static void main(String[] args) {

        PremiumMember p =
            new PremiumMember(
                "MEM5",
                2000,
                "Coach Riya"
            );

        p.chargeLateFee(200);

        System.out.println(
            p.getTotalLateFees()
        );

        int[] history = p.getLateFeeHistory();

        history[0] = 999;

        System.out.println(
            Arrays.toString(
                p.getLateFeeHistory()
            )
        );
    }
}