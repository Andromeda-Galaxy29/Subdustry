package subdustry.type;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.struct.*;
import mindustry.ctype.*;
import mindustry.entities.part.*;
import mindustry.graphics.Layer;
import subdustry.gen.*;
import subdustry.graphics.Pseudo3D;

import static mindustry.Vars.tilesize;

public class BoidType extends MappableContent {
    public static ContentType ctype = ContentType.ammo_UNUSED;

    public float layer = Layer.flyingUnitLow - 2f;
    public float clipSize = 16f;

    public float viewRadius = 4 * tilesize;
    public float separationRadius = 1.5f * tilesize;

    public float minVel = 0.5f;
    public float maxVel = 1.5f;

    public float mapEdgeAvoidMult = 0.2f;
    public float otherBoidsAvoidMult = 0.05f;
    public float blockAvoidMult = 0.003f;
    public float unitAvoidMult = 0.05f;
    public float separationMult = 0.03f;
    public float alignmentMult = 0.06f;
    public float cohesionMult = 0.003f;

    public TextureRegion region;
    public TextureRegion outlineRegion;

    public Seq<DrawPart> parts = new Seq<>();

    public BoidType(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        for(var part : parts){
            part.load(name);
        }

        region = Core.atlas.find(name);
        outlineRegion = Core.atlas.find(name + "-outline");
    }

    public void draw(Boid boid){
        Draw.z(layer);
        Draw.rect(outlineRegion, boid.x, boid.y, boid.rotation);
        Draw.rect(region, boid.x, boid.y, boid.rotation);

        if(parts.size > 0) {
            for (int i = 0; i < parts.size; i++) {
                var part = parts.get(i);
                DrawPart.params.set(0f, 0f, 0f, 0f, 0f, 0f, boid.x, boid.y, boid.rotation);
                part.draw(DrawPart.params);
            }
        }
        Draw.reset();
    }

    @Override
    public ContentType getContentType() {
        //I sure do love mod compatibility issues
        return ctype;
    }
}
