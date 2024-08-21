package subdustry.world.blocks.power;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.*;
import arc.util.Time;
import mindustry.Vars;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.blocks.power.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

/**Opposite of solar panels. Called a tidal generator because the moon causes tides so it works better during the night*/
public class TidalGenerator extends PowerGenerator {
    /**The light level where the generator has 100% efficiency*/
    public float minLightLevel = 0.31f;
    public TextureRegion topRegion;
    public TextureRegion rotatorRegion;
    public float rotateScl = 30f, rotateMag = 20;

    public TidalGenerator(String name){
        super(name);
        envEnabled = Env.underwater;
    }

    @Override
    public void load(){
        super.load();
        topRegion = Core.atlas.find(name+"-top");
        rotatorRegion = Core.atlas.find(name+"-rotator");
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.remove(generationType);
        stats.add(generationType, powerProduction * 60.0f, StatUnit.powerSecond);
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, rotatorRegion, topRegion};
    }


    public class TidalGeneratorBuild extends GeneratorBuild{
        @Override
        public void updateTile(){
            productionEfficiency = enabled ?
                    state.rules.solarMultiplier * (1f - Mathf.maxZero(Attribute.light.env() +
                            (state.rules.lighting ?
                                    1f - state.rules.ambientLight.a :
                                    1f
                            ))): 0f;
            productionEfficiency = Mathf.clamp(Mathf.map(productionEfficiency, 0, 1-minLightLevel, 0, 1), 0, 1);
        }

        @Override
        public void draw(){
            super.draw();
            float angle = Mathf.sin(Time.time, rotateScl, rotateMag);
            Draw.rect(rotatorRegion, x, y, angle * productionEfficiency);
            Draw.rect(topRegion, x, y);
        }
    }
}