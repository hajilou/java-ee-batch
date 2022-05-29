package samples.enums;

public enum Type {
    REAL(0),
    LEGAL(1);

    public final int order;

    Type(int order) {
        this.order = order;
    }
}
