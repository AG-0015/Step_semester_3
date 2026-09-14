import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswerHash;

    public LibraryMember() {
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (this.membershipId == null) {
            this.membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    public void setSecurityAnswer(String answer) {

        if (answer == null) {
            return;
        }

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            answer.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder result = new StringBuilder();

            for (byte b : hash) {
                result.append(String.format("%02x", b));
            }

            securityAnswerHash = result.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        LibraryMember member = new LibraryMember();

        member.setMembershipId("LM1001");
        member.setMembershipId("LM2002");

        System.out.println(
                "Membership ID: "
                        + member.getMembershipId()
        );

        member.setName("Rahul");

        System.out.println(
                "Name: " + member.getName()
        );

        member.setPremiumMember(true);

        System.out.println(
                "Premium: "
                        + member.isPremiumMember()
        );

        member.setSecurityAnswer("blue");
    }
}