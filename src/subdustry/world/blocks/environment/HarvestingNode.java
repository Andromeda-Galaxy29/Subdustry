package subdustry.world.blocks.environment;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.core.GameState;
import mindustry.entities.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;

public class HarvestingNode extends Block {
    public float layer = Layer.blockProp;
    public Seq<Item> drops = new Seq<>();
    public int minDropAmount = 8;
    public int maxDropAmount = 10;
    public int minGrowTime = 160;
    public int maxGrowTime = 180;
    public Color color;

    public TextureRegion brokenRegion;
    public TextureRegion growingRegion;

    private Seq<ItemStack> dropPool = new Seq<>(); //TODO: This may not work in multiplayer

    public HarvestingNode(String name){
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
        config(Boolean.class, (HarvestingNodeBuild build, Boolean b) -> {
            if(b) build.mine(Vars.player.team());
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

    public class HarvestingNodeBuild extends Building{
        public boolean broken = false;
        public float progress = 0;
        public int growTime = maxGrowTime;

        /** Mines the outcrop and deposits the items to the building*/
        public void mine(Building b){
            if (broken) return;

            broken = true;
            growTime = Mathf.random(minGrowTime, maxGrowTime);

            Fx.breakProp.wrap(color).at(x, y);
            Sounds.rockBreak.at(x, y);

            if(b == null){
                return;
            }

            if(dropPool.isEmpty()){
                for(Item item : drops){
                    for (int amount = minDropAmount; amount <= maxDropAmount; amount++){
                        for(int i = 0; i<4; i++){
                            dropPool.add(new ItemStack(item, amount));
                        }
                    }
                }
            }

            int i = Mathf.random(0, dropPool.size-1);
            ItemStack stack = dropPool.get(i);
            dropPool.remove(i);

            new Effect(12f, e -> {
                if(!(e.data instanceof Position)) return;
                Position to = (Position) e.data;
                Tmp.v1.set(e.x, e.y).interpolate(Tmp.v2.set(to), e.fin(), Interp.pow3)
                        .add(Tmp.v2.sub(e.x, e.y).nor().rotate90(1).scl(Mathf.randomSeedRange(e.id, 1f) * e.fslope() * 10f));
                float x = Tmp.v1.x, y = Tmp.v1.y;
                float size = 1f;

                Draw.color(e.color);
                Fill.circle(x, y, e.fslope() * 3f * size);
            }).at(x, y, stack.amount, stack.item.color, b);

            b.items.add(stack.item, stack.amount);
        }

        /** Mines the outcrop and deposits the items to the team's core*/
        public void mine(Team playerTeam){
            mine(playerTeam.core());
        }

        @Override
        public void updateTile(){
            if(team() != Team.derelict){
                changeTeam(Team.derelict);
            }

            if(broken){
                progress += getProgressIncrease(growTime);
            }

            if (progress >= 1f){
                broken = false;
                progress -= 1f;
            }
        }

        @Override
        public float edelta() {
            return delta();
        }

        @Override
        public boolean interactable(Team team){
            return true;
        }

        @Override
        public void draw(){
            Draw.z(layer);
            if(!broken){
                super.draw();
            }else{
                if (progress < 0.5f){
                    Draw.rect(((HarvestingNode)block).brokenRegion, x, y, drawrot());
                }else{
                    Draw.rect(((HarvestingNode)block).growingRegion, x, y, drawrot());
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
            if (broken || Vars.state.is(GameState.State.paused)) return false;
            configure(true);
            return false;
        }
    }
}
