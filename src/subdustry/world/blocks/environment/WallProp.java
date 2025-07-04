package subdustry.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.Vars;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

/**A prop that rotates and connects to environment walls or cliffs when placed near one*/
public class WallProp extends Prop {

    public WallProp(String name){
        super(name);
        layer = 33f;
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        float rotation = 0;
        float xOffset = 0;
        float yOffset = 0;
        for(int i = 0; i < 4; i++){
            Point2 r = Geometry.d4[i];
            Block b = Vars.world.tile(tile.x + r.x, tile.y + r.y).block();
            if(b != null){
                if(b instanceof StaticWall || b instanceof SCliff){
                    rotation = (i + 1) * 90;
                    if(b instanceof SCliff){
                        xOffset += r.x * Vars.tilesize / 2f;
                        yOffset += r.y * Vars.tilesize / 2f;
                    }
                    break;
                }

            }
        }
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
                tile.worldx() + xOffset, tile.worldy() + yOffset, rotation);
    }
}
