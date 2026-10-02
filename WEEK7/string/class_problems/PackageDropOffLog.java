abstract class DeliveryNote {

    public DeliveryNote() {
    }

    public abstract String confirmDelivery();

    public String confirmDelivery(String signature) {
        return confirmDelivery()
                + ", signed by " + signature;
    }
}


class ParcelNote extends DeliveryNote {

    private String trackingId;

    public ParcelNote(String trackingId) {
        super();
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}


class LetterNote extends DeliveryNote {

    private String trackingId;

    public LetterNote(String trackingId) {
        super();
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Letter " + trackingId + " delivered";
    }
}


public class PackageDropOffLog {

    public static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery());
        }
    }

    public static void main(String[] args) {

        ParcelNote p = new ParcelNote("TRK-1");
        LetterNote l = new LetterNote("TRK-2");

        System.out.println(p.confirmDelivery());
        System.out.println(p.confirmDelivery("J. Smith"));

        DeliveryNote ref = p;

        logAll(new DeliveryNote[]{
                ref,
                l
        });
    }
}