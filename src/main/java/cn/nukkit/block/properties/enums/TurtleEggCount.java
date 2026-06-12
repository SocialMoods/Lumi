package cn.nukkit.block.properties.enums;

public enum TurtleEggCount {
    ONE_EGG,
    TWO_EGG,
    THREE_EGG,
    FOUR_EGG;

    public TurtleEggCount before() {
        return TurtleEggCount.values()[this.ordinal() - 1];
    }

    public TurtleEggCount next() {
        return TurtleEggCount.values()[this.ordinal() + 1];
    }
}