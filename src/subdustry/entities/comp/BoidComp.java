package subdustry.entities.comp;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.entities.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.*;
import subdustry.gen.*;
import subdustry.graphics.*;
import subdustry.world.blocks.environment.*;

import static mindustry.Vars.*;

@EntityComponent(base = true)
@EntityDef(Boidc.class)
abstract class BoidComp implements Entityc, Drawc, Velc, Rotc, Syncc {

    //TODO: Make a type to store these values
    float layer = 0f;
    float height = 0f;
    String name;

    float viewRadius = 5 * tilesize;
    float separationRadius = 2 * tilesize;

    float minVel = 0.5f;
    float maxVel = 1.5f;

    float mapEdgeAvoidMult = 0.2f;
    float otherBoidsAvoidMult = 0.05f;
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
        Pseudo3D.rect(Core.atlas.find(name), x, y, height, rotation);
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
            avoidOtherBoids();
            if(height == 0){
                avoidBlocks();
                avoidUnits();
            }

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

    public void avoidOtherBoids(){
        Tmp.v1.setZero();

        Groups.all.count(entity -> {
            if(!isVisible(entity)) return false;
            Boid other = (Boid) entity;
            if(name == other.name) return false;

            Tmp.v1.add(getForceAwayFrom(other.x, other.y, viewRadius));

            return true;
        });

        velAddNet(Tmp.v1.scl(otherBoidsAvoidMult));
    }

    public void avoidBlocks(){
        Tmp.v1.setZero();

        //Check only the bounding box of the view radius for tiles for optimisation purposes
        for (int ix = tileX() - Mathf.ceil(viewRadius / tilesize); ix <= tileX() + Mathf.ceil(viewRadius / tilesize); ix++) {
            for (int iy = tileY() - Mathf.ceil(viewRadius / tilesize); iy <= tileY() + Mathf.ceil(viewRadius / tilesize); iy++) {
                Tile tile = world.tile(ix, iy);
                if(tile != null && dst(tile.worldx(), tile.worldy()) <= viewRadius && (isEnvironmentWall(tile) || isPlayerBuild(tile))){
                    Tmp.v1.add(getForceAwayFrom(tile.worldx(), tile.worldy(), viewRadius));
                }
            }
        }

        velAddNet(Tmp.v1.scl(blockAvoidMult));
    }

    public void avoidUnits(){
        Tmp.v1.setZero();

        Units.nearby(null, x, y, viewRadius, unit -> {
            Tmp.v1.add(getForceAwayFrom(unit.x, unit.y, viewRadius));
        });

        velAddNet(Tmp.v1.scl(unitAvoidMult));
    }

    public void separation() {
        Tmp.v1.setZero();

        //I have no idea why, but EntityGroup.each freezes the game. So using EntityGroup.count instead
        Groups.all.count(entity -> {
            if(!isInSeparationRadius(entity)) return false;
            Boid other = (Boid) entity;
            if(name != other.name) return false;

            Tmp.v1.add(getForceAwayFrom(other.x, other.y, separationRadius));

            return true;
        });

        velAddNet(Tmp.v1.scl(separationMult));
    }

    public void alignment() {
        Tmp.v1.setZero();

        int count = Groups.all.count(entity -> {
            if(!isVisible(entity)) return false;
            Boid other = (Boid) entity;
            if(name != other.name) return false;

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
            if(!isVisible(entity)) return false;
            Boid other = (Boid) entity;
            if(name != other.name) return false;

            Tmp.v1.add(other.x, other.y);

            return true;
        });

        if(count != 0){
            Tmp.v1.scl(1f / count);
            Tmp.v1.sub(x, y);
        }
        velAddNet(Tmp.v1.scl(cohesionMult));
    }

    //Util functions
    public Vec2 getForceAwayFrom(float otherX, float otherY, float maxDistance){
        if(dst(otherX, otherY) > maxDistance){
            return Vec2.ZERO;
        }
        return Tmp.v2.set(x, y).sub(otherX, otherY).nor().scl(maxDistance)
                .add(otherX, otherY).sub(x, y);
    }

    public boolean isEnvironmentWall(Tile tile){
        return tile.block().solid && !tile.block().synthetic() && !(tile.block() instanceof SCliff) && !(tile.block() instanceof ShapedProp);
    }

    public boolean isPlayerBuild(Tile tile){
        return tile.block().solid && tile.block().synthetic() && tile.build != null && tile.build.team != Team.derelict;
    }

    public boolean isAnotherBoid(Entityc other){
        return other instanceof Boid && id != ((Boid) other).id;
    }

    public boolean isVisible(Entityc other){
        return isAnotherBoid(other) && height == ((Boid) other).height && dst((Boid) other) <= viewRadius;
    }

    public boolean isInSeparationRadius(Entityc other){
        return isAnotherBoid(other) && height == ((Boid) other).height && dst((Boid) other) <= separationRadius;
    }
}
