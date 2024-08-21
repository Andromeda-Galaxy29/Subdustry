package subdustry.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.Layer;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class Kelp extends Prop {
    public TextureRegion bottomRegion;
    public float shadowOffset = -3f;
    public float shadowLayer = Layer.power - 1;
    public float scl = 60, mag = 30;

    static Rand rand = new Rand();

    public Kelp(String name) {
        super(name);
        variants = 0;
        breakable = alwaysReplace = false;
        solid = false;
        layer = Layer.power + 1;
        hasShadow = true;
    }

    @Override
    public void load(){
        super.load();
        bottomRegion = Core.atlas.find(name+"-bottom");
    }

    @Override
    public void drawBase(Tile tile) {
        float angle = Mathf.sin(Time.time, scl, mag);

        Draw.z(shadowLayer);
        Draw.rect(customShadowRegion, tile.worldx() + shadowOffset, tile.worldy() + shadowOffset);

        Draw.z(layer);
        Draw.rect(bottomRegion, tile.worldx(), tile.worldy(), -angle * Draw.scl);
        Draw.rect(region, tile.worldx(), tile.worldy(), angle * Draw.scl);
    }
}
