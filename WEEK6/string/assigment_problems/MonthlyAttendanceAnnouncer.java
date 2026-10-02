class GymMember {
    protected String memberId;
    protected int monthlyFee;
    protected int sessionsAttended;

    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().isEmpty()
                || memberId.length() < 4) {
            throw new IllegalArgumentException("Invalid member ID");
        }

        if (monthlyFee <= 0) {
            throw new IllegalArgumentException("Monthly fee must be positive");
        }

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
        this.sessionsAttended = 0;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    public void displayInfo() {
        System.out.print(
                "Standard | Sessions: " + sessionsAttended
        );
    }
}


class PremiumMember extends GymMember {
    protected String trainerName;

    public PremiumMember(
            String memberId,
            int monthlyFee,
            String trainerName
    ) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    public void displayInfo() {
        System.out.print(
                "Premium | Trainer: " + trainerName
                        + " | Sessions: " + sessionsAttended
        );
    }

    public String getTrainerName() {
        return trainerName;
    }
}


public class MonthlyAttendanceAnnouncer {

    public static String batchPrint(GymMember[] members) {
        StringBuilder announcement = new StringBuilder();

        for (GymMember member : members) {

            member.displayInfo();

            announcement.append(
                    getDisplayText(member)
            );

            if (member instanceof PremiumMember) {
                PremiumMember premium =
                        (PremiumMember) member;

                announcement.append(
                        " [Trainer via downcast: "
                                + premium.getTrainerName()
                                + "]"
                );
            }

            announcement.append(" | ");
        }

        return announcement.toString();
    }

    private static String getDisplayText(GymMember member) {

        if (member instanceof PremiumMember) {
            PremiumMember premium =
                    (PremiumMember) member;

            return "Premium | Trainer: "
                    + premium.getTrainerName()
                    + " | Sessions: "
                    + premium.getSessionsAttended();
        }

        return "Standard | Sessions: "
                + member.getSessionsAttended();
    }


    public static void main(String[] args) {

        GymMember standard =
                new GymMember("MEM6", 1000);

        PremiumMember premium =
                new PremiumMember(
                        "MEM7",
                        2000,
                        "Coach Riya"
                );

        String result = batchPrint(
                new GymMember[]{standard, premium}
        );

        System.out.println(result);
    }
}