package subdustry.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.Layer;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import subdustry.graphics.Pseudo3D;

public class Kelp extends Prop {

    public float shadowLayer = Layer.blockProp + 1;
    public float topLayer = Layer.light;
    public float midLayer = Layer.light - 1;

    public float rotationScl = 60, rotationMag = 30;
    public float minLength = 0.2f, maxLength = 0.5f;
    /** Amount of segments in the stem */
    public int segments = 10;

    public TextureRegion bottomRegion;
    public TextureRegion stemRegion;
    public TextureRegion rootRegion;
    public TextureRegion leavesRegion;
    public TextureRegion seedsRegion;

    public Kelp(String name) {
        super(name);
        variants = 0;
        breakable = alwaysReplace = false;
        solid = false;
        layer = Layer.blockProp + 2;
        hasShadow = true;
        clipSize = 100;
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

    public float getLength(Tile tile){
        return Mathf.randomSeed(tile.x * tile.y, minLength, maxLength);
    }

    @Override
    public void drawBase(Tile tile) {
        float x = tile.worldx(), y = tile.worldy();
        float angle = Mathf.sin(Time.time, rotationScl, rotationMag);
        float length = getLength(tile);

        Draw.z(shadowLayer);
        Draw.rect(customShadowRegion, x, y);

        Draw.z(layer);
        Draw.rect(rootRegion, x, y);

        for(int i = 0; i < segments; i++){
            float leavesRotation = Mathf.randomSeed(i * tile.x * tile.y, 0, 360);
            float leavesRotationMult = Mathf.randomSeed(i * tile.x * tile.y, -2f, 2f);
            float currentHeight = (length / segments) * i;
            float nextHeight = (length / segments) * (i + 1);

            //Draws the leaves
            if(i != 0) { //Don't draw leaves at the ground
                Draw.z(midLayer);
                Pseudo3D.rect(leavesRegion, x, y, currentHeight, leavesRotation + angle * Draw.scl * leavesRotationMult);
            }

            //Draws the stem
            Draw.z(midLayer + Pseudo3D.layerOffset(x, y));
            Draw.alpha(Pseudo3D.heightFade(currentHeight));
            Lines.stroke(stemRegion.height / 4f);
            Lines.line(
                    stemRegion,
                    Pseudo3D.xHeight(x, currentHeight), Pseudo3D.yHeight(y, currentHeight),
                    Pseudo3D.xHeight(x, nextHeight), Pseudo3D.yHeight(y, nextHeight),
                    false
            );
            Draw.reset();
        }

        //Draws the top part of the kelp
        Draw.z(topLayer);
        Pseudo3D.rect(seedsRegion, x, y, length - 0.075f, -angle * Draw.scl);
        Pseudo3D.rect(bottomRegion, x, y, length - 0.05f, angle * Draw.scl);
        Pseudo3D.rect(bottomRegion, x, y, length - 0.025f, -angle * Draw.scl);
        Pseudo3D.rect(region, x, y, length, angle * Draw.scl);
    }
}
