package subdustry.world.blocks.production;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import subdustry.world.blocks.environment.HarvestingNode;

import static mindustry.Vars.*;

public class Harvester extends Block {
    public int range = 6;
    public int reload = 360;

    public Harvester(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 150;
        acceptsItems = false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.range, range, StatUnit.blocks);
        stats.add(Stat.productionTime, reload/60f, StatUnit.seconds);

        stats.add(Stat.drillTier, table -> {
            table.row();
            table.table(c -> {
                int i = 0;
                for(Block block : content.blocks()){
                    if(!(block instanceof HarvestingNode)) continue;

                    Seq<Item> drops = ((HarvestingNode) block).drops;

                    c.table(Styles.grayPanel, b -> {
                        b.image(block.uiIcon).size(40).pad(10f).left().scaling(Scaling.fit);
                        b.table(info -> {
                            info.left();
                            info.add(block.localizedName).left().row();
                            info.table(dropt -> {
                                for (var drop : drops) {
                                    dropt.image(drop.uiIcon).size(20).left();
                                }
                            }).left();
                        }).grow().padRight(40f);
                    }).growX().pad(5);
                    if(++i % 2 == 0) c.row();
                }
            }).growX().colspan(table.getColumns());
        });
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("collect-progress", (HarvesterBuild e) ->
                new Bar(() -> Core.bundle.get("bar.collect-progress"), () -> Pal.ammo, () -> e.progress));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        Drawf.dashSquare(Pal.ammo, x, y, range * tilesize);

        Vars.indexer.eachBlock(Team.derelict, Tmp.r1.setCentered(x, y, range * tilesize), b -> b instanceof HarvestingNode.HarvestingNodeBuild, t -> {
            Drawf.selected(t, Tmp.c1.set(Pal.ammo).a(Mathf.absin(4f, 1f)));
        });
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    public class HarvesterBuild extends Building {
        public float progress, time, warmup = 0f;
        public Seq<HarvestingNode.HarvestingNodeBuild> targets = new Seq<>();

        @Override
        public boolean shouldConsume() {
            Seq<ItemStack> allItems = new Seq<>();
            items.each((item, amount) -> {
                allItems.add(new ItemStack(item, amount));
            });
            for(var item : allItems){
                if(item.amount > itemCapacity){
                    return false;
                }
            }
            return true;
        }

        @Override
        public void updateTile(){
            targets.clear();
            Vars.indexer.eachBlock(Team.derelict, Tmp.r1.setCentered(x, y, range * tilesize),
                    b -> b instanceof HarvestingNode.HarvestingNodeBuild,
                    b -> targets.add((HarvestingNode.HarvestingNodeBuild)b)
            );

            if(efficiency > 0) {
                progress += getProgressIncrease(reload);
                time += edelta();
            }

            if(progress >= 1f){
                consume();
                for(var target : targets){
                    target.mine(this);
                }
                progress -= 1;
            }

            dumpOutputs();

            warmup = Mathf.lerp(warmup, efficiency>0 ? 1 : 0, 0.08f);
        }

        public void dumpOutputs(){
            if(timer(timerDump, dumpTime / timeScale)){
                items.each((i, a) -> {
                    dump(i);
                });
            }
        }

        @Override
        public void drawSelect(){
            super.drawSelect();

            Drawf.dashSquare(Pal.ammo, x, y, range * tilesize);

            for(var target : targets){
                Drawf.selected(target, Tmp.c1.set(Pal.ammo).a(Mathf.absin(4f, 1f)));
            }
        }

        @Override
        public void draw(){
            super.draw();

            if(warmup <= 0.001f) return;

            Draw.color(Pal.ammo);

            float drawRange = range * Vars.tilesize * warmup;
            float scanX = x + Mathf.sin(time, 30f, (drawRange - 3) / 2f);

            Draw.z(Layer.buildBeam);
            Fill.poly(x, y, 4, 4 * efficiency, Time.time / 0.8f);
            Fill.tri(x, y, scanX, y + (drawRange / 2f), scanX, y - (drawRange / 2f));

            Draw.z(Layer.effect);
            Lines.stroke(1f * warmup);
            Lines.rect(x - (drawRange / 2f), y - (drawRange / 2f), drawRange, drawRange);
            Lines.stroke(2f * warmup);
            Lines.lineAngleCenter(scanX, y, 90, drawRange - 2);

            Draw.reset();
        }

        @Override
        public float progress(){
            return Mathf.clamp(progress);
        }

        @Override
        public float totalProgress(){
            return progress;
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(progress);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            progress = read.f();
        }
    }
}
