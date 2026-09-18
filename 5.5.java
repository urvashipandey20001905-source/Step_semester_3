final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {

        this.memberId = memberId;

        // Defensive copy
        this.bookIds = bookIds.clone();
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {

        // Defensive copy
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {

        String[] newBookIds = bookIds.clone();

        if (index >= 0 && index < newBookIds.length) {
            newBookIds[index] = newId;
        }

        return new LoanReceipt(memberId, newBookIds);
    }
}


// Subclass for reference-only books
class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}


// Nightly processor
class CirculationLedger {

    private static String branchCode;

    // Static initialization block
    static {
        branchCode = "PT-LIB-001";
    }

    public static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        LoanReceipt r = new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "BK-101"}
        );

        // Test defensive copying
        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(r.getBookIds()[0]);

        // Test wither
        LoanReceipt corrected =
                r.withCorrectedBookId(1, "BK-102");

        System.out.println(r.getBookIds()[0]);
        System.out.println(r.getBookIds()[1]);

        System.out.println(corrected.getBookIds()[0]);
        System.out.println(corrected.getBookIds()[1]);

        // Test nightly processing
        LoanReceipt[] receipts = {

            new ReferenceOnlyLoanReceipt(
                    "LIB-001",
                    new String[]{"BK-200"},
                    "Reading Room 3"
            ),

            null,

            new LoanReceipt(
                    "LIB-002",
                    new String[]{"BK-201"}
            )
        };

        System.out.println(
                CirculationLedger.processNightlyCirculation(receipts)
        );
    }
}