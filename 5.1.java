class LibraryMember {

    private String membershipPin;
    String branchCode;          // default
    protected double finesOwed;
    public String displayName;

    public LibraryMember() {
    }
}

public class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {

        if (fieldModifier.equals("private")) {
            if (accessorContext.equals("SAME_CLASS")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("default")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("protected")) {
            if (accessorContext.equals("SAME_CLASS") ||
                accessorContext.equals("SAME_PACKAGE")) {
                return "ALLOWED";
            }
            return "DENIED";
        }

        if (fieldModifier.equals("public")) {
            return "ALLOWED";
        }

        return "DENIED";
    }

    static String summarizeByModifier(String[][] attempts) {

        String[] modifiers = {"private", "default", "protected", "public"};

        int[] allowed = new int[4];
        int[] denied = new int[4];

        for (String[] attempt : attempts) {

            String modifier = attempt[0];
            String context = attempt[1];

            String result = classifyAccess(modifier, context);

            for (int i = 0; i < modifiers.length; i++) {

                if (modifier.equals(modifiers[i])) {

                    if (result.equals("ALLOWED")) {
                        allowed[i]++;
                    } else {
                        denied[i]++;
                    }

                    break;
                }
            }
        }

        return "private: " + allowed[0] + " allowed / " + denied[0] + " denied | "
             + "default: " + allowed[1] + " allowed / " + denied[1] + " denied | "
             + "protected: " + allowed[2] + " allowed / " + denied[2] + " denied | "
             + "public: " + allowed[3] + " allowed / " + denied[3] + " denied";
    }

    public static void main(String[] args) {

        System.out.println(
            classifyAccess("private", "SAME_CLASS")
        );

        String[][] attempts = {
            {"private", "SAME_CLASS"},
            {"private", "SAME_PACKAGE"},
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"protected", "SAME_PACKAGE"},
            {"protected", "SAME_CLASS"},
            {"public", "DIFFERENT_PACKAGE"}
        };

        System.out.println(
            summarizeByModifier(attempts)
        );
    }
}