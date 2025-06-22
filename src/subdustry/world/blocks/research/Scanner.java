package subdustry.world.blocks.research;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.logic.*;
import mindustry.ui.*;
import mindustry.world.blocks.payloads.*;
import mindustry.world.meta.BuildVisibility;

import static mindustry.Vars.*;

public class Scanner extends PayloadBlock {
    public float maxPayloadSize = 3;
    public float scanTime = 240f;
    public Effect scanEffect = new Effect(12, e -> {
        Draw.color(Pal.techBlue);
        Lines.stroke(3f - e.fin() * 2f);
        Lines.square(e.x, e.y, tilesize / 2f * e.rotation + e.fin() * 3f);

        Angles.randLenVectors(e.id, 3 + (int)(e.rotation * 3), e.rotation * 2f + (tilesize * e.rotation) * e.finpow(), (x, y) -> {
            Fill.poly(e.x + x, e.y + y, 3, 1f + e.fout() * (3f + e.rotation));
        });
    });

    public Scanner(String name) {
        super(name);

        outputsPayload = false;
        acceptsPayload = true;
        update = true;
        rotate = false;
        solid = true;
        size = 3;
        payloadSpeed = 1f;
        hasPower = true;
        buildVisibility = BuildVisibility.campaignOnly;
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("scan-progress", (ScannerBuild e) -> new Bar("bar.scan-progress", Pal.techBlue, () -> e.progress));
    }

    public class ScannerBuild extends PayloadBlockBuild<Payload> {
        public @Nullable Payload scanning;
        public float progress;
        public float time, speedScl;

        @Override
        public void draw() {
            Draw.rect(region, x, y);

            //draw input
            for (int i = 0; i < 4; i++) {
                if (blends(i)) {
                    Draw.rect(inRegion, x, y, (i * 90) - 180);
                }
            }

            Draw.z(Layer.blockOver);
            drawPayload();
            if (scanning != null) {
                scanning.set(x + payVector.x, y + payVector.y, payRotation);

                Draw.z(Layer.blockOver);
                scanning.drawShadow(1f - progress);

                Draw.draw(Layer.blockOver, () -> {
                    Drawf.construct(x, y, scanning.icon(), Pal.techBlue, scanning instanceof BuildPayload ? 0f : payRotation - 90f, 1f - progress, 1f - progress, time);
                    Draw.color(Pal.techBlue);
                    Draw.alpha(1f);

                    Lines.lineAngleCenter(x + Mathf.sin(time, 20f, tilesize / 2f * block.size - 3f), y, 90f, block.size * tilesize - 6f);

                    Draw.reset();
                });
            }
        }

        @Override
        public boolean acceptUnitPayload(Unit unit){
            return false;
        }

        @Override
        public boolean acceptPayload(Building source, Payload payload) {
            if (payload instanceof BuildPayload) {
                if (!(((BuildPayload) payload).build instanceof Fragment.FragmentBuild)) {
                    return false;
                }
            }
            return scanning == null && this.payload == null && super.acceptPayload(source, payload) && payload.fits(maxPayloadSize);
        }

        @Override
        public void updateTile(){
            super.updateTile();

            if(scanning == null){
                progress = 0f;
            }

            payRotation = Angles.moveToward(payRotation, 90f, payloadRotateSpeed * edelta());

            if(scanning != null){
                float shift = edelta() / scanTime;

                progress += shift;
                time += edelta();

                speedScl = Mathf.lerpDelta(speedScl, 1f, 0.1f);

                if(progress >= 1f){
                    scanEffect.at(x, y, scanning.size() / tilesize);

                    if (scanning instanceof BuildPayload && ((BuildPayload) scanning).build instanceof Fragment.FragmentBuild){
                        if(player.team() == team()){
                            if(state.isCampaign()){
                                //TODO: More than one fragment to unlock content
                                ((Fragment) ((BuildPayload) scanning).build.block).content.unlock();
                            }else{
                                ui.hudfrag.showToast(Icon.cancel, Core.bundle.get("ui.scanner-not-campaign"));
                            }
                        }
                    }
                    scanning = null;
                }
            }else if(moveInPayload(false) && payload != null){
                scanning = payload;
                payload = null;
                progress = 0f;
            }
        }

        @Override
        public double sense(LAccess sensor){
            if(sensor == LAccess.progress) return progress;
            return super.sense(sensor);
        }

        @Override
        public boolean shouldConsume(){
            return scanning != null && enabled;
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(progress);
            Payload.write(scanning, write);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            progress = read.f();
            scanning = Payload.read(read);
        }
    }
}
