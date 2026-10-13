interface MembershipPlan {
    String name();
    int months();
    double discount();
    default double calculateFee() {
        return 1000.0 * months() * (1.0 - discount());
    }
}

class MonthlyPlan implements MembershipPlan {
    public String name() { return "Monthly"; }
    public int months() { return 1; }
    public double discount() { return 0; }
}
class QuarterlyPlan implements MembershipPlan {
    public String name() { return "Quarterly"; }
    public int months() { return 3; }
    public double discount() { return 0.10; }
}
class AnnualPlan implements MembershipPlan {
    public String name() { return "Annual"; }
    public int months() { return 12; }
    public double discount() { return 0.25; }
}

enum MembershipStatus { ACTIVE, FROZEN, EXPIRED }

class Member {
    private final String name;
    Member(String name) { this.name = name; }
    String getName() { return name; }
}

class Membership {
    private final Member member;
    private final MembershipPlan plan;
    private MembershipStatus status = MembershipStatus.ACTIVE;

    Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
    }

    MembershipStatus getStatus() { return status; }

    void checkIn() {
        if (status == MembershipStatus.ACTIVE) {
            System.out.println(member.getName() + " checked in successfully.");
        } else {
            System.out.println("Check-in denied: " + member.getName()
                    + "'s membership is " + status + ".");
        }
    }

    void freeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot freeze an Expired membership.");
        } else if (status == MembershipStatus.FROZEN) {
            System.out.println(member.getName() + "'s membership is already Frozen.");
        } else {
            status = MembershipStatus.FROZEN;
            System.out.println(member.getName() + "'s membership frozen. Status: " + status + ".");
        }
    }

    void unfreeze() {
        if (status == MembershipStatus.EXPIRED) {
            System.out.println("Cannot unfreeze an Expired membership.");
        } else if (status == MembershipStatus.FROZEN) {
            status = MembershipStatus.ACTIVE;
            System.out.println(member.getName() + "'s membership unfrozen. Status: " + status + ".");
        }
    }

    void expire() {
        status = MembershipStatus.EXPIRED;
        System.out.println(member.getName() + "'s membership expired. Status: " + status + ".");
    }
}

class MembershipDesk {
    Membership buy(Member member, MembershipPlan plan) {
        Membership membership = new Membership(member, plan);
        System.out.printf("%s membership created for %s. Fee: ₹%.2f. Status: Active.%n",
                plan.name(), member.getName(), plan.calculateFee());
        return membership;
    }
}

public class Main {
    public static void main(String[] args) {
        MembershipDesk desk = new MembershipDesk();
        Membership asha = desk.buy(new Member("Asha"), new QuarterlyPlan());
        Membership ravi = desk.buy(new Member("Ravi"), new MonthlyPlan());

        asha.checkIn();
        asha.freeze();
        asha.checkIn();

        ravi.expire();
        ravi.freeze();
    }
}