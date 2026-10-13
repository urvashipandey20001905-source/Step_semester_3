import java.util.*;

interface WashOption {
    String name();
    int durationMinutes();
    double charge();
}

class QuickWash implements WashOption {
    public String name() { return "Quick"; }
    public int durationMinutes() { return 30; }
    public double charge() { return 20; }
}

class NormalWash implements WashOption {
    public String name() { return "Normal"; }
    public int durationMinutes() { return 45; }
    public double charge() { return 30; }
}

class HeavyWash implements WashOption {
    public String name() { return "Heavy"; }
    public int durationMinutes() { return 60; }
    public double charge() { return 45; }
}

class Student {
    private final String name;
    Student(String name) { this.name = name; }
    String getName() { return name; }
}

class WashingMachine {
    private final String id;
    private WashCycle activeCycle;

    WashingMachine(String id) { this.id = id; }
    String getId() { return id; }
    boolean isFree() { return activeCycle == null; }

    void start(WashCycle cycle) {
        if (!isFree()) throw new IllegalStateException("Machine is busy");
        activeCycle = cycle;
    }

    void completeCycle() {
        if (isFree()) {
            System.out.println(id + " has no active cycle.");
            return;
        }
        activeCycle = null;
        System.out.println(id + " cycle completed. " + id + " is now free.");
    }
}

class WashCycle {
    private final Student student;
    private final WashingMachine machine;
    private final WashOption option;

    WashCycle(Student student, WashingMachine machine, WashOption option) {
        this.student = student;
        this.machine = machine;
        this.option = option;
    }

    Student getStudent() { return student; }
    WashingMachine getMachine() { return machine; }
    WashOption getOption() { return option; }
}

class LaundrySystem {
    public boolean startWash(Student student, WashingMachine machine, WashOption option) {
        if (!machine.isFree()) {
            System.out.println("Machine " + machine.getId() + " is currently busy.");
            return false;
        }

        machine.start(new WashCycle(student, machine, option));
        System.out.printf("%s wash started on %s for %s (%d min). Charge: ₹%.2f.%n",
                option.name(), machine.getId(), student.getName(),
                option.durationMinutes(), option.charge());
        return true;
    }

    public void completeWash(WashingMachine machine) {
        machine.completeCycle();
    }
}

public class Main {
    public static void main(String[] args) {
        LaundrySystem laundry = new LaundrySystem();
        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        laundry.startWash(new Student("Asha"), m1, new QuickWash());
        laundry.startWash(new Student("Ravi"), m1, new HeavyWash());
        laundry.startWash(new Student("Ravi"), m2, new HeavyWash());
        laundry.completeWash(m1);
        laundry.startWash(new Student("Neha"), m1, new NormalWash());
    }
}