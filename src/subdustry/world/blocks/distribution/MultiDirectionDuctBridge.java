package subdustry.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Log;
import mindustry.core.*;
import mindustry.entities.units.*;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.*;

import static mindustry.Vars.*;

/**ItemBridge but with duct bridge visuals. More functionality might be added*/
public class MultiDirectionDuctBridge extends BufferedItemBridge {
    public TextureRegion bridgeBotRegion;

    public MultiDirectionDuctBridge(String name){
        super(name);
    }

    @Override
    public void load(){
        super.load();
        bridgeBotRegion = Core.atlas.find(name+"-bridge-bottom");
    }

    @Override
    public void drawBridge(BuildPlan req, float ox, float oy, float flip){
        drawBridge(req.drawx(), req.drawy(), ox, oy);
    }

    public void drawBridge(float x1, float y1, float x2, float y2){
        int rotation = Math.round(Angles.angle(x1, y1, x2, y2) / 90f);

        Draw.alpha(Renderer.bridgeOpacity);
        float
                angle = Angles.angle(x1, y1, x2, y2),
                cx = (x1 + x2)/2f,
                cy = (y1 + y2)/2f,
                len = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)) - size * tilesize;

        Draw.rect(bridgeRegion, cx, cy, len, bridgeRegion.height * bridgeRegion.scl(), angle);
        if(bridgeBotRegion.found()){
            Draw.color(0.4f, 0.4f, 0.4f, 0.4f * Renderer.bridgeOpacity);
            Draw.rect(bridgeBotRegion, cx, cy, len, bridgeBotRegion.height * bridgeBotRegion.scl(), angle);
            Draw.reset();
        }
        Draw.alpha(Renderer.bridgeOpacity);

        for(float i = 6f; i <= len + size * tilesize - 5f; i += 5f){
            Draw.rect(arrowRegion, x1 + Geometry.d4x(rotation) * i, y1 + Geometry.d4y(rotation) * i, angle);
        }

        Draw.reset();
    }

    public class MultiDirectionDuctBridgeBuild extends BufferedItemBridgeBuild {
        @Override
        public void draw(){
            Draw.rect(block.region, x, y);

            Tile other = world.tile(link);
            if(!linkValid(tile, other)) return;

            Draw.z(Layer.power - 1);
            drawBridge(x, y, other.drawx(), other.drawy());
        }
    }

}
