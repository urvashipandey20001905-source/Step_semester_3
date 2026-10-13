abstract class GardenTool {
    public abstract String use();
}

class CuttingTool extends GardenTool {
    public CuttingTool() {
        super();
    }

    @Override
    public String use() {
        return "Using the tool in the garden, blade sharpened first";
    }
}

class Pruner extends CuttingTool {
    public Pruner() {
        super();
    }

    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}

abstract class Instrument {
    public Instrument() {
    }

    public abstract String play();
}

class StringInstrument extends Instrument {
    public StringInstrument() {
        super();
    }

    @Override
    public String play() {
        return "Strumming the strings";
    }
}

class Violin extends StringInstrument {
    public Violin() {
        super();
    }

    @Override
    public String play() {
        return super.play() + ", with a bow drawn across four strings";
    }
}

public class Main {
    public static void main(String[] args) {
        CuttingTool cuttingTool = new CuttingTool();
        System.out.println(cuttingTool.use());

        Pruner pruner = new Pruner();
        System.out.println(pruner.use());

        StringInstrument stringInstrument = new StringInstrument();
        System.out.println(stringInstrument.play());

        Violin violin = new Violin();
        System.out.println(violin.play());
    }
}