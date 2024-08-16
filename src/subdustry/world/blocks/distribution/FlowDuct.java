package subdustry.world.blocks.distribution;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.blocks.distribution.*;

public class FlowDuct extends OverflowDuct {
    public TextureRegion invertedRegion;

    public FlowDuct(String name) {
        super(name);
        configurable = true;
        config(Boolean.class, (FlowDuctBuild entity, Boolean b) -> entity.inverted = b);
    }

    @Override
    public void load(){
        super.load();
        invertedRegion = Core.atlas.find(name+"-inverted");
    }

    public class FlowDuctBuild extends OverflowDuctBuild{
        public boolean inverted = false;

        @Override
        public void draw(){
            Draw.rect(region, x, y);
            Draw.rect(topRegion, x, y, rotdeg());
            if(!inverted) return;
            Draw.rect(invertedRegion, x, y, rotdeg());
        }

        @Override
        @Nullable
        public Building target(){
            if(current == null) return null;

            if(inverted){
                Building l = left(), r = right();
                boolean lc = l != null && l.team == team && l.acceptItem(this, current),
                        rc = r != null && r.team == team && r.acceptItem(this, current);

                if(lc && !rc){
                    return l;
                }else if(rc && !lc){
                    return r;
                }else if(lc && rc){
                    return cdump == 0 ? l : r;
                }
            }

            Building front = front();
            if(front != null && front.team == team && front.acceptItem(this, current)){
                return front;
            }

            if(inverted) return null;

            for(int i = -1; i <= 1; i++){
                int dir = Mathf.mod(rotation + (((i + cdump + 1) % 3) - 1), 4);
                if(dir == rotation) continue;
                Building other = nearby(dir);
                if(other != null && other.team == team && other.acceptItem(this, current)){
                    return other;
                }
            }

            return null;
        }

        @Override
        public boolean configTapped(){
            configure(!inverted);
            return false;
        }

        @Override
        public Boolean config(){
            return inverted;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.bool(inverted);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            inverted = read.bool();
        }
    }
}
