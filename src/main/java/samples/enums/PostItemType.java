package samples.enums;

public enum PostItemType {
    LETTER(0),
    PACKAGE(1);

    public final int order;

    PostItemType(int order) {
        this.order = order;
    }
}
