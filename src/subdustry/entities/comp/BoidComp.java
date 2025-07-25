package subdustry.entities.comp;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import ent.anno.Annotations.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.world.*;
import subdustry.gen.*;
import subdustry.type.*;
import subdustry.world.blocks.environment.*;

import static mindustry.Vars.*;

@EntityComponent(base = true)
@EntityDef(Boidc.class)
abstract class BoidComp implements Drawc, Velc, Rotc, Syncc {

    String typeName;

    @Import int id;
    @Import float x, y;
    @Import float rotation;
    @Import Vec2 vel;

    @Override
    public void draw(){
        type().draw(self());
    }

    @Override
    @Replace
    public float clipSize() {
        return type().clipSize;
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
            if(type() == null) return;

            avoidMapEdge();
            avoidOtherBoids();
            avoidBlocks();
            avoidUnits();

            separation();
            alignment();
            cohesion();

            vel.clamp(type().minVel, type().maxVel);
            rotation = vel.angle() - 90;
        }
    }

    public void avoidMapEdge(){
        if(x < 0){
            velAddNet(type().mapEdgeAvoidMult, 0);
        }
        if(y < 0){
            velAddNet(0, type().mapEdgeAvoidMult);
        }
        if(x > world.width() * tilesize){
            velAddNet(-type().mapEdgeAvoidMult, 0);
        }
        if(y > world.height() * tilesize){
            velAddNet(0, -type().mapEdgeAvoidMult);
        }
    }

    public void avoidOtherBoids(){
        Tmp.v1.setZero();

        Groups.all.count(entity -> {
            if(!isVisible(entity)) return false;
            Boid other = (Boid) entity;
            if(type() == other.type()) return false;

            Tmp.v1.add(getForceAwayFrom(other.x, other.y, type().viewRadius));

            return true;
        });

        velAddNet(Tmp.v1.scl(type().otherBoidsAvoidMult));
    }

    public void avoidBlocks(){
        Tmp.v1.setZero();

        //Check only the bounding box of the view radius for tiles for optimisation purposes
        for (int ix = tileX() - Mathf.ceil(type().viewRadius / tilesize); ix <= tileX() + Mathf.ceil(type().viewRadius / tilesize); ix++) {
            for (int iy = tileY() - Mathf.ceil(type().viewRadius / tilesize); iy <= tileY() + Mathf.ceil(type().viewRadius / tilesize); iy++) {
                Tile tile = world.tile(ix, iy);
                if(tile != null && dst(tile.worldx(), tile.worldy()) <= type().viewRadius && (isEnvironmentWall(tile) || isPlayerBuild(tile))){
                    Tmp.v1.add(getForceAwayFrom(tile.worldx(), tile.worldy(), type().viewRadius));
                }
            }
        }

        velAddNet(Tmp.v1.scl(type().blockAvoidMult));
    }

    public void avoidUnits(){
        Tmp.v1.setZero();

        Units.nearby(null, x, y, type().viewRadius, unit -> {
            Tmp.v1.add(getForceAwayFrom(unit.x, unit.y, type().viewRadius));
        });

        velAddNet(Tmp.v1.scl(type().unitAvoidMult));
    }

    public void separation() {
        Tmp.v1.setZero();

        //I have no idea why, but EntityGroup.each freezes the game. So using EntityGroup.count instead
        Groups.all.count(entity -> {
            if(!isInSeparationRadius(entity)) return false;
            Boid other = (Boid) entity;
            if(type() != other.type()) return false;

            Tmp.v1.add(getForceAwayFrom(other.x, other.y, type().separationRadius));

            return true;
        });

        velAddNet(Tmp.v1.scl(type().separationMult));
    }

    public void alignment() {
        Tmp.v1.setZero();

        int count = Groups.all.count(entity -> {
            if(!isVisible(entity)) return false;
            Boid other = (Boid) entity;
            if(type() != other.type()) return false;

            Tmp.v1.add(other.vel);

            return true;
        });

        if(count != 0){
            Tmp.v1.scl(1f / count);
        }
        velAddNet(Tmp.v1.scl(type().alignmentMult));
    }

    public void cohesion() {
        Tmp.v1.setZero();

        int count = Groups.all.count(entity -> {
            if(!isVisible(entity)) return false;
            Boid other = (Boid) entity;
            if(type() != other.type()) return false;

            Tmp.v1.add(other.x, other.y);

            return true;
        });

        if(count != 0){
            Tmp.v1.scl(1f / count);
            Tmp.v1.sub(x, y);
        }
        velAddNet(Tmp.v1.scl(type().cohesionMult));
    }

    //Type
    public BoidType type(){
        return content.getByName(BoidType.ctype, typeName);
    }

    public void setType(BoidType type){
        typeName = type.name;
    }

    //Util functions
    public Vec2 getForceAwayFrom(float otherX, float otherY, float maxDistance){
        if(dst(otherX, otherY) > maxDistance){
            return Vec2.ZERO;
        }
        return Tmp.v2.set(x, y).sub(otherX, otherY).nor().scl(maxDistance)
                .add(otherX, otherY).sub(x, y);
    }

    public static boolean isEnvironmentWall(Tile tile){
        return tile.block().solid && !tile.block().synthetic() && !(tile.block() instanceof SCliff) && !(tile.block() instanceof ShapedProp);
    }

    public static boolean isPlayerBuild(Tile tile){
        return tile.block().solid && tile.block().synthetic() && tile.build != null && tile.build.team != Team.derelict;
    }

    public boolean isAnotherBoid(Entityc other){
        return other instanceof Boid && id != ((Boid) other).id;
    }

    public boolean isVisible(Entityc other){
        return isAnotherBoid(other) && dst((Boid) other) <= type().viewRadius;
    }

    public boolean isInSeparationRadius(Entityc other){
        return isAnotherBoid(other) && dst((Boid) other) <= type().separationRadius;
    }
}
