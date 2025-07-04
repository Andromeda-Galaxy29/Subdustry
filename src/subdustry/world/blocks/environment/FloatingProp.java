package subdustry.world.blocks.environment;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;
import subdustry.graphics.Pseudo3D;

/** A prop that uses pseudo 3D to make it look like it's floating above ground */
public class FloatingProp extends Prop {

    public float shadowLayer = Layer.blockProp + 1;

    public float rotationScl = 120, rotationMag = 5;
    public float minHeight = 0.15f, maxHeight = 0.25f;

    public FloatingProp(String name) {
        super(name);
        breakable = alwaysReplace = false;
        solid = false;
        layer = Layer.light;
        customShadow = true;
        clipSize = 100;
    }

    public float getHeight(Tile tile){
        return Mathf.randomSeed(tile.x * tile.y, minHeight, maxHeight);
    }

    @Override
    public void drawShadow(Tile tile) {
        Draw.z(shadowLayer);
        super.drawShadow(tile);
    }

    @Override
    public void drawBase(Tile tile) {
        float x = tile.drawx(), y = tile.drawy();
        float angle = Mathf.sin(Time.time, rotationScl, rotationMag);
        float height = getHeight(tile);
        TextureRegion drawRegion = variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region;

        Draw.z(layer);
        Pseudo3D.rect(drawRegion, x, y, height, angle * Draw.scl);
    }
}
