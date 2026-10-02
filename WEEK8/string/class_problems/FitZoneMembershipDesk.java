abstract class Membership {
    protected String memberName;
    protected int monthlyFee;

    public Membership(String memberName, int monthlyFee) {
        this.memberName = memberName;
        this.monthlyFee = monthlyFee;
    }

    public abstract void displayMembership();
}

class BasicMembership extends Membership {
    public BasicMembership(String memberName, int monthlyFee) {
        super(memberName, monthlyFee);
    }

    @Override
    public void displayMembership() {
        System.out.println(
                "Basic Membership | Member: " +
                        memberName +
                        " | Fee: " +
                        monthlyFee
        );
    }
}

class PremiumMembership extends Membership {
    public PremiumMembership(String memberName, int monthlyFee) {
        super(memberName, monthlyFee);
    }

    @Override
    public void displayMembership() {
        System.out.println(
                "Premium Membership | Member: " +
                        memberName +
                        " | Fee: " +
                        monthlyFee
        );
    }
}

class MembershipDesk {
    public void printMembership(Membership membership) {
        membership.displayMembership();
    }
}

public class FitZoneMembershipDesk {
    public static void main(String[] args) {
        MembershipDesk desk = new MembershipDesk();

        Membership basic =
                new BasicMembership("Arun", 1000);

        Membership premium =
                new PremiumMembership("Priya", 2000);

        desk.printMembership(basic);
        desk.printMembership(premium);
    }
}