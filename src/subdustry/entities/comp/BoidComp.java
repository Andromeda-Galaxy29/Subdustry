package subdustry.entities.comp;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import subdustry.gen.*;

import static mindustry.Vars.*;

@EntityComponent(base = true)
@EntityDef(Boidc.class)
abstract class BoidComp implements Entityc, Drawc, Velc, Rotc {

    //TODO: Make a type to store these values
    float layer = Layer.flyingUnitLow - 2f;

    float viewRadius = 5 * tilesize;
    float separationRadius = 2 * tilesize;

    float minVel = 0.5f;
    float maxVel = 1.5f;

    float mapEdgeAvoidMult = 0.2f;
    float blockAvoidMult = 0.003f;
    float unitAvoidMult = 0.05f;
    float separationMult = 0.03f;
    float alignmentMult = 0.06f;
    float cohesionMult = 0.003f;

    @Import int id;
    @Import float x, y;
    @Import float rotation;
    @Import Vec2 vel;

    @Override
    public void draw(){
        Draw.z(layer);
        Draw.rect(Core.atlas.find("subdustry-glide"), x, y, rotation);
        Draw.reset();
    }

    @Override
    @Replace
    public void move(float cx, float cy){
        x += cx;
        y += cy;
    }

    @Override
    public void update() {
        if(!net.client() || isLocal()){
            avoidMapEdge();
            avoidBlocks();
            avoidUnits();
            separation();
            alignment();
            cohesion();

            vel.clamp(minVel, maxVel);
            rotation = vel.angle() - 90;
        }
    }

    public void avoidMapEdge(){
        if(x < 0){
            velAddNet(mapEdgeAvoidMult, 0);
        }
        if(y < 0){
            velAddNet(0, mapEdgeAvoidMult);
        }
        if(x > world.width() * tilesize){
            velAddNet(-mapEdgeAvoidMult, 0);
        }
        if(y > world.height() * tilesize){
            velAddNet(0, -mapEdgeAvoidMult);
        }
    }

    public void avoidBlocks(){
        Tmp.v1.setZero();

        //Check only the bounding box of the view radius for tiles for optimisation purposes
        for (int ix = tileX() - Mathf.ceil(viewRadius / tilesize); ix <= tileX() + Mathf.ceil(viewRadius / tilesize); ix++) {
            for (int iy = tileY() - Mathf.ceil(viewRadius / tilesize); iy <= tileY() + Mathf.ceil(viewRadius / tilesize); iy++) {
                Tile tile = world.tile(ix, iy);
                if(tile != null && dst(tile.worldx(), tile.worldy()) <= viewRadius && tile.block().solid){
                    Tmp.v2.set(tile.worldx(), tile.worldy()).sub(x, y).nor().scl(viewRadius);
                    Tmp.v1.add(tile.worldx(), tile.worldy()).sub(x, y).sub(Tmp.v2);
                }
            }
        }

        velAddNet(Tmp.v1.scl(blockAvoidMult));
    }

    public void avoidUnits(){
        Tmp.v1.setZero();

        Units.nearby(null, x, y, viewRadius, unit -> {
            if(dst(unit) > viewRadius) {
                return;
            }
            Tmp.v2.set(unit.x, unit.y).sub(x, y).nor().scl(viewRadius);
            Tmp.v1.add(unit.x, unit.y).sub(x, y).sub(Tmp.v2);
        });

        velAddNet(Tmp.v1.scl(unitAvoidMult));
    }

    public void separation() {
        Tmp.v1.setZero();

        //I have no idea why, but EntityGroup.each freezes the game. So using EntityGroup.count instead
        Groups.all.count(entity -> {
            if(!shouldSeparateFromBoid(entity)) return false;
            Boid other = (Boid) entity;
            Tmp.v2.set(other.x, other.y).sub(x, y).nor().scl(separationRadius);
            Tmp.v1.add(other.x, other.y).sub(x, y).sub(Tmp.v2);
            return true;
        });

        velAddNet(Tmp.v1.scl(separationMult));
    }

    public void alignment() {
        Tmp.v1.setZero();

        int count = Groups.all.count(entity -> {
            if(!isVisibleBoid(entity)) return false;
            Boid other = (Boid) entity;

            Tmp.v1.add(other.vel);

            return true;
        });

        if(count != 0){
            Tmp.v1.scl(1f / count);
        }
        velAddNet(Tmp.v1.scl(alignmentMult));
    }

    public void cohesion() {
        Tmp.v1.setZero();

        int count = Groups.all.count(entity -> {
            if(!isVisibleBoid(entity)) return false;

            Boid other = (Boid) entity;
            Tmp.v1.add(other.x, other.y);

            return true;
        });

        if(count != 0){
            Tmp.v1.scl(1f / count);
            Tmp.v1.sub(x, y);
        }
        velAddNet(Tmp.v1.scl(cohesionMult));
    }

    private boolean isAnotherBoid(Entityc e){
        return e instanceof Boid && id != ((Boid) e).id;
    }

    private boolean isVisibleBoid(Entityc e){
        return isAnotherBoid(e) && dst((Boid) e) <= viewRadius;
    }

    private boolean shouldSeparateFromBoid(Entityc e){
        return isAnotherBoid(e) && dst((Boid) e) <= separationRadius;
    }
}
