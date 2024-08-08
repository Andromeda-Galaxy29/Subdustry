package subdustry.blocks.environment;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.CoreBlock;

public class Outcrop extends Block {
    public float layer = Layer.blockProp;
    public Seq<Item> drops = new Seq<Item>();
    public int minDropAmount = 3;
    public int maxDropAmount = 5;
    public int minGrowTime = 80;
    public int maxGrowTime = 120;
    public Color color;

    public TextureRegion brokenRegion;
    public TextureRegion growingRegion;

    public Outcrop(String name){
        super(name);
        breakable = false;
        destructible = false;
        configurable = true;
        saveConfig = false;
        sync = true;
        update = true;
        drawTeamOverlay = false;
        inEditor = true;
        category = Category.effect;
        config(Boolean.class, (OutcropBuild build, Boolean b) -> {
            if(b && !build.broken) build.mine(Vars.player.team());
        });
    }

    @Override
    public void load(){
        super.load();
        brokenRegion = Core.atlas.find(name+"-broken");
        growingRegion = Core.atlas.find(name+"-growing");
    }

    @Override
    public boolean canBreak(Tile tile){
        return Vars.state.isEditor();
    }

    public class OutcropBuild extends Building{
        public boolean broken = false;
        public int timer = 0;
        public int growTime = maxGrowTime;

        public void mine(Team playerTeam){
            broken = true;

            Fx.breakProp.wrap(color).at(x, y);
            Sounds.rockBreak.at(x, y);

            Item item = drops.get(Mathf.random(0, drops.size-1));
            Log.info(Mathf.random(0, drops.size-1));
            int amount = Mathf.random(minDropAmount, maxDropAmount);
            CoreBlock.CoreBuild core = playerTeam.core();

            if(core == null){
                Log.warn("Player breaking outcrop has no core!");
                return;
            }

            new Effect(12f, e -> {
                if(!(e.data instanceof Position to)) return;
                Tmp.v1.set(e.x, e.y).interpolate(Tmp.v2.set(to), e.fin(), Interp.pow3)
                        .add(Tmp.v2.sub(e.x, e.y).nor().rotate90(1).scl(Mathf.randomSeedRange(e.id, 1f) * e.fslope() * 10f));
                float x = Tmp.v1.x, y = Tmp.v1.y;
                float size = 1f;

                Draw.color(e.color);
                Fill.circle(x, y, e.fslope() * 3f * size);
            }).at(x, y, amount, item.color, core);

            core.items.add(item, amount);
        }

        @Override
        public void updateTile(){
            if(broken){
                timer += 1;
            }

            if (timer >= growTime){
                broken = false;
                timer = 0;
                growTime = Mathf.random(minGrowTime, maxGrowTime);
            }
        }

        @Override
        public void draw(){
            Draw.z(layer);
            if(!broken){
                Draw.rect(block.region, x, y, drawrot());
            }else{
                if (timer < growTime / 2.0){
                    Draw.rect(((Outcrop)block).brokenRegion, x, y, drawrot());
                }else{
                    Draw.rect(((Outcrop)block).growingRegion, x, y, drawrot());
                }
            }
        }

        @Override
        public boolean collide(Bullet other){
            return false;
        }

        @Override
        public void damage(float damage){
            // No damage
        }

        @Override
        public boolean configTapped(){
            if (broken) return false;
            configure(true);
            return false;
        }
    }
}
