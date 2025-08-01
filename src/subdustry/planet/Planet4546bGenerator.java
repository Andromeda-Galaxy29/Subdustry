package subdustry.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.noise.*;
import mindustry.maps.generators.*;

import static mindustry.Vars.*;

public class Planet4546bGenerator extends PlanetGenerator {
    Color c1 = Color.valueOf("5682c7"), c2 = Color.valueOf("35528b"), out = new Color();

    @Override
    public float getHeight(Vec3 position){
        return 0;
    }

    @Override
    public Color getColor(Vec3 position){
        float depth = Simplex.noise3d(seed, 2, 0.56, 1.7f, position.x, position.y, position.z) / 2f;
        return c1.write(out).lerp(c2, Mathf.clamp(Mathf.round(depth, 0.15f))).a(0.2f);
    }

}
