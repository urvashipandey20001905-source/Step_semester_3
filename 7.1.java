interface Ringable {
    String ring();
}

class AlarmClock implements Ringable {
    private final String time;

    public AlarmClock(String time) {
        this.time = time;
    }

    @Override
    public String ring() {
        return "Alarm ringing for " + time;
    }
}

class Doorbell implements Ringable {
    private final String location;

    public Doorbell(String location) {
        this.location = location;
    }

    @Override
    public String ring() {
        return "Doorbell ringing at " + location;
    }
}

class RingingSystem {
    public static void ringAll(Ringable[] devices) {
        for (Ringable device : devices) {
            System.out.println(device.ring());
        }
    }
}

abstract class Toy {
    private static int counter = 1000;
    private final String toyId;
    protected String name;

    public Toy(String name) {
        this.name = name;
        counter++;
        toyId = "TOY-" + counter;
    }

    public abstract String makeSound();

    public String getToyId() {
        return toyId;
    }
}

class ToyCar extends Toy {
    public ToyCar(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Vroom vroom!";
    }
}

class ToyRobot extends Toy {
    public ToyRobot(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Beep boop!";
    }
}

public class Main {
    public static void main(String[] args) {
        AlarmClock alarm = new AlarmClock("7:00 AM");
        Doorbell doorbell = new Doorbell("Front Door");
        RingingSystem.ringAll(new Ringable[] { alarm, doorbell });

        ToyCar car = new ToyCar("Speedster");
        System.out.println(car.makeSound());
        System.out.println(car.getToyId());

        ToyRobot robot = new ToyRobot("Bolt");
        System.out.println(robot.makeSound());
        System.out.println(robot.getToyId());
    }
}