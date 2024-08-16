package subdustry.world.blocks.environment;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.Vars;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

/**A prop that rotates and connects to static walls when placed near one*/
public class WallProp extends Prop {

    public WallProp(String name){
        super(name);
    }

    @Override
    public void drawBase(Tile tile){
        Draw.z(layer);
        float rotation = 0;
        for(int i = 0; i < 4; i++){
            Point2 r = Geometry.d4[i];
            Block b = Vars.world.tile(tile.x + r.x, tile.y + r.y).block();
            if(b != null && b instanceof StaticWall){
                rotation = (i + 1) * 90;
                break;
            };
        }
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx(), tile.worldy(), rotation);
    }
}
