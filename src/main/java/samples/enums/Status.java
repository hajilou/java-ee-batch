package samples.enums;

public enum Status {
    RECEIVED(0),
    NOT_DELIVERED(1),
    DELIVERED(2),
    EXPIRED(3);

    public final int order;

    Status(int order) {
        this.order = order;
    }
}
