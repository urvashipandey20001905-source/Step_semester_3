abstract class Drone {
    protected final String id;

    public Drone(String id) {
        this.id = id;
    }

    public abstract String fly();
}

interface Trackable {
    String getLocation();
}

class DeliveryDrone extends Drone implements Trackable {
    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return "Delivery drone " + id + " is flying";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {
    public ScoutDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return "Scout drone " + id + " is flying";
    }
}

class GroundRobot implements Trackable {
    private final String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class TrackingSystem {
    public static String getLocationIfTrackable(Object object) {
        if (object instanceof Trackable) {
            Trackable trackable = (Trackable) object;
            return trackable.getLocation();
        }

        return "Tracking not available";
    }
}

abstract class DeliveryNote {
    public abstract String confirmDelivery();

    public String confirmDelivery(String signature) {
        return confirmDelivery() + ", signed by " + signature;
    }
}

class ParcelNote extends DeliveryNote {
    private final String trackingId;

    public ParcelNote(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}

class LetterNote extends DeliveryNote {
    private final String trackingId;

    public LetterNote(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Letter " + trackingId + " delivered";
    }
}

public class Main {
    static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery());
        }
    }

    public static void main(String[] args) {
        DeliveryDrone deliveryDrone = new DeliveryDrone("DR-1");
        ScoutDrone scoutDrone = new ScoutDrone("SC-1");
        GroundRobot groundRobot = new GroundRobot("GR-1");

        System.out.println(TrackingSystem.getLocationIfTrackable(deliveryDrone));
        System.out.println(TrackingSystem.getLocationIfTrackable(scoutDrone));
        System.out.println(TrackingSystem.getLocationIfTrackable(groundRobot));

        ParcelNote parcel = new ParcelNote("TRK-1");
        System.out.println(parcel.confirmDelivery());
        System.out.println(parcel.confirmDelivery("J. Smith"));

        DeliveryNote[] notes = {
            parcel,
            new LetterNote("TRK-2")
        };
        logAll(notes);
    }
}