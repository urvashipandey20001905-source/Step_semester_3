public class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;

    // Stores transformed security answer
    private String securityAnswerHash;

    // Required public no-argument constructor
    public LibraryMember() {
    }

    // Write-once property
    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {

        if (membershipId == null) {
            membershipId = id;
        }
    }

    // Normal JavaBean property
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Boolean JavaBean property
    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only property
    public void setSecurityAnswer(String answer) {

        if (answer != null) {
            securityAnswerHash = Integer.toHexString(answer.hashCode());
        }
    }

    public static void main(String[] args) {

        LibraryMember m = new LibraryMember();

        m.setMembershipId("LIB-8841");
        m.setName("Priya Nair");
        m.setPremiumMember(true);

        System.out.println(m.getMembershipId());
        System.out.println(m.getName());
        System.out.println(m.isPremiumMember());

        // Second call is ignored
        m.setMembershipId("FAKE-0000");

        System.out.println(m.getMembershipId());

        m.setSecurityAnswer("BlueMountain");
    }
}