package subdustry.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import subdustry.graphics.*;
import subdustry.world.blocks.environment.Kelp;

import static mindustry.Vars.tilesize;

public class KelpCollector extends Block {
    public TextureRegion baseRegion;
    public TextureRegion leftArmRegion;
    public TextureRegion rightArmRegion;
    public TextureRegion itemRegion;

    public TextureRegion outlineRegion;
    public TextureRegion leftArmOutlineRegion;
    public TextureRegion rightArmOutlineRegion;

    public TextureRegion cableRegion;
    public TextureRegion cableCapRegion;

    public TextureRegion previewRegion;

    public float reload = 300f;
    public int outputAmount = 1;
    public float armRotation = 10;
    public Interp interp = Interp.smooth;

    public KelpCollector(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 15;
        acceptsItems = false;
        envEnabled = Env.underwater;
    }

    @Override
    public void load(){
        super.load();
        baseRegion = Core.atlas.find(name+"-base");
        leftArmRegion = Core.atlas.find(name+"-arm-l");
        rightArmRegion = Core.atlas.find(name+"-arm-r");
        itemRegion = Core.atlas.find(name+"-item");

        outlineRegion = Core.atlas.find(name+"-outline");
        leftArmOutlineRegion = Core.atlas.find(name+"-arm-l-outline");
        rightArmOutlineRegion = Core.atlas.find(name+"-arm-r-outline");

        cableRegion = Core.atlas.find(name+"-cable");
        cableCapRegion = Core.atlas.find(name+"-cable-cap");

        previewRegion = Core.atlas.find(name+"-preview");
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.productionTime, reload / 60f, StatUnit.seconds);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("collect-progress", (KelpCollectorBuild e) ->
                new Bar(() -> Core.bundle.get("bar.collect-progress"), () -> Color.valueOf("#79b647"), () -> e.progress));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for(int i = 0; i<4; i++){
            for(int j = 0; j < size; j++) {
                nearbySide(tile.x, tile.y, i, j, Tmp.p1);
                if(Vars.world.tile(Tmp.p1.x, Tmp.p1.y).block() instanceof Kelp){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        Tile target = null;
        for(int i = 0; i<4 && target == null; i++){
            for(int j = 0; j < size && target == null; j++) {
                nearbySide(x, y, i, j, Tmp.p1);
                if(Vars.world.tile(Tmp.p1.x, Tmp.p1.y).block() instanceof Kelp){
                    target = Vars.world.tile(Tmp.p1.x, Tmp.p1.y);
                }
            }
        }

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        if(target != null && target.block().itemDrop != null) {
            Draw.color(target.block().itemDrop.color);
            Draw.rect(itemRegion, x, y);
        }
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{baseRegion, previewRegion};
    }

    @Override
    public boolean outputsItems(){
        return true;
    }

    public class KelpCollectorBuild extends Building {
        public float progress = 0f;
        float armOffset = 0;
        public Tile target = null;

        @Override
        public boolean shouldConsume() {
            Seq<ItemStack> allItems = new Seq<>();
            items.each((item, amount) -> {
                allItems.add(new ItemStack(item, amount));
            });
            for (var item : allItems) {
                if (item.amount > itemCapacity) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void updateTile(){
            for(int i = 0; i<4 && target == null; i++){
                for(int j = 0; j < size && target == null; j++) {
                    nearbySide(tileX(), tileY(), i, j, Tmp.p1);
                    if(Vars.world.tile(Tmp.p1.x, Tmp.p1.y).block() instanceof Kelp){
                        target = Vars.world.tile(Tmp.p1.x, Tmp.p1.y);
                    }
                }
            }

            if(efficiency > 0 && target != null) {
                progress += getProgressIncrease(reload);
            }
            armOffset = Mathf.lerp(armOffset, progress>0.45 ? armRotation : -armRotation, 0.08f);

            if(progress >= 1f){
                consume();
                if(items.total() <= itemCapacity - outputAmount && target != null && target.block().itemDrop != null){
                    items.add(target.block().itemDrop, outputAmount);
                    produced(target.block().itemDrop, outputAmount);
                }
                progress -= 1;
            }

            dumpOutputs();
        }

        public void dumpOutputs(){
            if(timer(timerDump, dumpTime / timeScale)){
                items.each((i, a) -> {
                    dump(i);
                });
            }
        }

        @Override
        public void draw(){
            Draw.rect(baseRegion, x, y);

            float height = 0;
            float angle = 0;
            if (target != null && target.block() instanceof Kelp) {
                height = (((Kelp) target.block()).getLength(target) - 0.075f) * interp.apply(Interp.slope.apply(progress));
                angle = angleTo(target.x * Vars.tilesize, target.y * Vars.tilesize);
            }

            //Shadow
            Drawf.shadow(previewRegion, x - 1, y - 1, angle - 90);

            //Cable cap
            Draw.z(Layer.blockOver);
            Draw.rect(cableCapRegion, x, y, angleTo(Pseudo3D.xHeight(x, height), Pseudo3D.yHeight(y, height)));

            if (target != null && target.block() instanceof Kelp){
                Draw.z(progress < 0.05 ? Layer.turret : ((Kelp) target.block()).midLayer);
            }else{
                Draw.z(Layer.turret);
            }

            //Cable
            Lines.stroke(cableRegion.height / 4f);
            Pseudo3D.line(cableRegion, x, y, 0, x, y, height, false);

            //Outlines
            Pseudo3D.rect(leftArmOutlineRegion, x, y, height, angle - 90 - armOffset);
            Pseudo3D.rect(rightArmOutlineRegion, x, y, height, angle - 90 + armOffset);
            Pseudo3D.rect(outlineRegion, x, y, height, angle - 90);

            //Claw
            Pseudo3D.rect(leftArmRegion, x, y, height, angle - 90 - armOffset);
            Pseudo3D.rect(rightArmRegion, x, y, height, angle - 90 + armOffset);
            Pseudo3D.rect(region, x, y, height, angle - 90);

            //Item
            if(target != null && target.block().itemDrop != null && progress > 0.5f) {
                Draw.color(target.block().itemDrop.color);
                Pseudo3D.rect(itemRegion, x, y, height, angle - 90);
            }
        }
    }
}
