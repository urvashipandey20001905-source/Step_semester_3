abstract class ArtPiece {
    private static int counter = 0;
    private final String pieceId;

    public ArtPiece() {
        pieceId = "AP-" + (++counter);
    }

    public abstract String describe();

    public String getPieceId() {
        return pieceId;
    }
}

class Painting extends ArtPiece {
    private final String title;

    public Painting(String title) {
        super();
        this.title = title;
    }

    @Override
    public String describe() {
        return "Painting: " + title + ", framed on canvas";
    }
}

class Sculpture extends ArtPiece {
    private final String title;

    public Sculpture(String title) {
        super();
        this.title = title;
    }

    @Override
    public String describe() {
        return "Sculpture: " + title + ", carved from stone";
    }
}

interface Printable {
    String printLabel();
}

class PackageBox implements Printable {
    private final String trackingId;

    public PackageBox(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String printLabel() {
        return "Package label: " + trackingId;
    }
}

class Invoice implements Printable {
    private final String invoiceNumber;

    public Invoice(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String printLabel() {
        return "Invoice label: " + invoiceNumber;
    }
}

public class Main {
    static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }

    public static void main(String[] args) {
        Painting painting = new Painting("Sunset Fields");
        Sculpture sculpture = new Sculpture("The Thinker II");

        System.out.println(painting.describe());
        System.out.println(painting.getPieceId());
        System.out.println(sculpture.describe());
        System.out.println(sculpture.getPieceId());

        PackageBox box = new PackageBox("TRK-88");
        Invoice invoice = new Invoice("INV-42");

        printAll(new Printable[] { box, invoice });
    }
}