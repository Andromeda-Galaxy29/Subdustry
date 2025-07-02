package subdustry.world.blocks.environment;

import arc.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class SCliff extends Prop {

    /** 0, 1, 2, 3 - straight segments;
     * 4, 5, 6, 7 - outer corners;
     * 8, 9, 10, 11 - inner corners */
    public TextureRegion[] regions;
    public TextureRegion[] capRegions;

    public SCliff(String name) {
        super(name);
        breakable = alwaysReplace = false;
        cacheLayer = CacheLayer.walls;
        solid = true;
        fillsTile = false;
        hasShadow = false;
        variants = 0;
        saveData = true;
    }

    @Override
    public void load() {
        super.load();
        regions = new TextureRegion[12];
        for(int i = 0; i < 12; i++){
            regions[i] = Core.atlas.find(name+i);
        }

        capRegions = new TextureRegion[2];
        for(int i = 0; i < 2; i++){
            capRegions[i] = Core.atlas.find(name+"-cap"+i);
        }
    }

    @Override
    public int minimapColor(Tile tile){
        return getColor(tile).rgba();
    }

    @Override
    public void drawBase(Tile tile) {
        Draw.color(getColor(tile));
        if(tile.data == 0){
            Draw.z(Layer.floor + 1f);
            Draw.rect(region, tile.worldx(), tile.worldy());
        }else{
            Draw.z(Layer.floor + 1f);
            Draw.rect(regions[tile.data - 1], tile.worldx(), tile.worldy());
        }
        drawCap(tile);
        Draw.reset();
    }

    public Color getColor(Tile tile){
        if(tile.floor() instanceof SFloor sFloor){
            return sFloor.cliffColor;
        }else{
            return Tmp.c1.set(tile.floor().mapColor).mul(1.6f);
        }
    }

    public void drawCap(Tile tile) {
        Draw.z(Layer.floor + 1f);
        if(shouldDrawCapAt(tile, tile.nearby(1, 0))){
            if(tile.data == 4 || tile.data == 8 || tile.data == 11){
                Draw.rect(capRegions[0], tile.worldx() + Vars.tilesize, tile.worldy());
            }
            if(tile.data == 2 || tile.data == 7 || tile.data == 12){
                Draw.rect(capRegions[1], tile.worldx() + Vars.tilesize, tile.worldy());
            }
        }
        if(shouldDrawCapAt(tile, tile.nearby(0, -1))){
            if(tile.data == 1 || tile.data == 5 || tile.data == 12){
                Draw.rect(capRegions[0], tile.worldx(), tile.worldy() - Vars.tilesize, -90);
            }
            if(tile.data == 3 || tile.data == 8 || tile.data == 9){
                Draw.rect(capRegions[1], tile.worldx(), tile.worldy() - Vars.tilesize, -90);
            }
        }
        if(shouldDrawCapAt(tile, tile.nearby(-1, 0))){
            if(tile.data == 4 || tile.data == 5 || tile.data == 10){
                Draw.rect(capRegions[0], tile.worldx() - Vars.tilesize, tile.worldy(), 180);
            }
            if(tile.data == 2 || tile.data == 6 || tile.data == 9){
                Draw.rect(capRegions[1], tile.worldx() - Vars.tilesize, tile.worldy(), 180);
            }
        }
        if(shouldDrawCapAt(tile, tile.nearby(0, 1))){
            if(tile.data == 1 || tile.data == 6 || tile.data == 11){
                Draw.rect(capRegions[0], tile.worldx(), tile.worldy() + Vars.tilesize, 90);
            }
            if(tile.data == 3 || tile.data == 7 || tile.data == 10){
                Draw.rect(capRegions[1], tile.worldx(), tile.worldy() + Vars.tilesize, 90);
            }
        }
    }

    public boolean shouldDrawCapAt(Tile tile, Tile other){
        return !(other.block() instanceof SCliff) || tile.floor() != other.floor();
    }

    public void process(Tile tile) {
        //Inner corners
        if(helperAt(tile, 1, 0) && helperAt(tile, 0, 1)){
            tile.data = (byte) 9;
        }else if(helperAt(tile, 1, 0) && helperAt(tile, 0, -1)){
            tile.data = (byte) 10;
        }else if(helperAt(tile, -1, 0) && helperAt(tile, 0, -1)){
            tile.data = (byte) 11;
        }else if(helperAt(tile, -1, 0) && helperAt(tile, 0, 1)){
            tile.data = (byte) 12;
        }else
        //Straight segments
        if(helperAt(tile, -1, 0)){
            tile.data = (byte) 1;
        }else if(helperAt(tile, 0, 1)){
            tile.data = (byte) 2;
        }else if(helperAt(tile, 1, 0)){
            tile.data = (byte) 3;
        }else if(helperAt(tile, 0, -1)){
            tile.data = (byte) 4;
        }else
        //Outer corners
        if(helperAt(tile, -1, -1)){
            tile.data = (byte) 5;
        }else if(helperAt(tile, -1, 1)){
            tile.data = (byte) 6;
        }else if(helperAt(tile, 1, 1)){
            tile.data = (byte) 7;
        }else if(helperAt(tile, 1, -1)){
            tile.data = (byte) 8;
        }
    }

    public boolean helperAt(Tile tile, int xOffset, int yOffset){
        return tile.nearby(xOffset, yOffset).block() instanceof SCliffHelper;
    }
}
