abstract class ClassroomDevice {
    public abstract String operate();
}

interface Chargeable {
    String charge();
    String charge(int minutes);
}

class Tablet extends ClassroomDevice implements Chargeable {
    private final String assetTag;

    public Tablet(String assetTag) {
        this.assetTag = assetTag;
    }

    @Override
    public String operate() {
        return "Tablet " + assetTag + " displaying lesson";
    }

    @Override
    public String charge() {
        return assetTag + " charging";
    }

    @Override
    public String charge(int minutes) {
        return assetTag + " charging for " + minutes + " minutes";
    }
}

abstract class KitchenTool {
    private int speedLevel;

    public KitchenTool() {
        speedLevel = 1;
    }

    public abstract String prepare();

    public int getSpeedLevel() {
        return speedLevel;
    }

    public void setSpeedLevel(int speedLevel) {
        if (speedLevel >= 1 && speedLevel <= 5) {
            this.speedLevel = speedLevel;
        }
    }
}

interface Washable {
    String clean();
}

class Blender extends KitchenTool implements Washable {
    public Blender() {
        super();
    }

    @Override
    public String prepare() {
        return "Blending at speed " + getSpeedLevel();
    }

    @Override
    public String clean() {
        return "Blender rinsed and dried";
    }
}

public class Main {
    public static void main(String[] args) {
        Tablet tablet = new Tablet("TAB-5");
        System.out.println(tablet.operate());
        System.out.println(tablet.charge());
        System.out.println(tablet.charge(30));

        Blender blender = new Blender();
        blender.setSpeedLevel(3);
        System.out.println(blender.getSpeedLevel());

        blender.setSpeedLevel(9); // Invalid; speed stays at 3.
        System.out.println(blender.getSpeedLevel());

        System.out.println(blender.prepare());
        System.out.println(blender.clean());
    }
}