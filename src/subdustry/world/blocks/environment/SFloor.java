package subdustry.world.blocks.environment;

import arc.graphics.Color;
import mindustry.world.blocks.environment.Floor;

public class SFloor extends Floor {
    public Color cliffLightColor;
    public Color cliffDarkColor;

    public SFloor(String name) {
        super(name);
    }

    public SFloor(String name, int variants) {
        super(name, variants);
    }
}
