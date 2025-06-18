package subdustry.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.Layer;
import mindustry.world.*;
import subdustry.graphics.Pseudo3D;

public class Kelp extends FloatingProp {

    public float bottomLayer = Layer.blockProp + 2;
    public float midLayer = Layer.light - 1;

    public int segments = 10;

    public TextureRegion bottomRegion;
    public TextureRegion stemRegion;
    public TextureRegion rootRegion;
    public TextureRegion leavesRegion;
    public TextureRegion seedsRegion;

    public Kelp(String name) {
        super(name);
        variants = 0;
        layer = Layer.light;
        rotationScl = 60;
        rotationMag = 30;
        minHeight = 0.2f;
        maxHeight = 0.5f;
    }

    @Override
    public void load(){
        super.load();
        bottomRegion = Core.atlas.find(name+"-bottom");
        stemRegion = Core.atlas.find(name+"-stem");
        rootRegion = Core.atlas.find(name+"-root");
        leavesRegion = Core.atlas.find(name+"-leaves");
        seedsRegion = Core.atlas.find(name+"-seeds");
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{seedsRegion, bottomRegion, region};
    }

    @Override
    public void drawBase(Tile tile) {
        float x = tile.drawx(), y = tile.drawy();
        float angle = Mathf.sin(Time.time, rotationScl, rotationMag);
        float height = getHeight(tile);

        Draw.z(bottomLayer);
        Draw.rect(rootRegion, x, y);

        for(int i = 0; i < segments; i++){
            float leavesRotation = Mathf.randomSeed(i * tile.x * tile.y, 0, 360);
            float leavesRotationMult = Mathf.randomSeed(i * tile.x * tile.y, -2f, 2f);
            float currentHeight = (height / segments) * i;
            float nextHeight = (height / segments) * (i + 1);

            //Draws the leaves
            if(i != 0) { //Don't draw leaves at the ground
                Draw.z(midLayer);
                Pseudo3D.rect(leavesRegion, x, y, currentHeight, leavesRotation + angle * Draw.scl * leavesRotationMult);
            }

            //Draws the stem
            Draw.z(midLayer);
            Lines.stroke(stemRegion.height / 4f);
            Pseudo3D.line(stemRegion, x, y, currentHeight, x, y, nextHeight, false);
        }

        //Draws the top part of the kelp
        Draw.z(layer);
        Pseudo3D.rect(seedsRegion, x, y, height - 0.075f, -angle * Draw.scl);
        Pseudo3D.rect(bottomRegion, x, y, height - 0.05f, angle * Draw.scl);
        Pseudo3D.rect(bottomRegion, x, y, height - 0.025f, -angle * Draw.scl);

        super.drawBase(tile);
    }
}
