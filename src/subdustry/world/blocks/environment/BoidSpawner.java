package subdustry.world.blocks.environment;

import arc.math.*;
import arc.scene.ui.layout.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import subdustry.content.*;
import subdustry.gen.*;
import subdustry.type.*;

import static mindustry.Vars.*;

public class BoidSpawner extends Block {
    BoidType boidType = BoidTypes.peeper;
    int maxAmount = 400;
    int defaultAmount = 100;

    public BoidSpawner(String name){
        this(name, BoidTypes.peeper);
    }

    public BoidSpawner(String name, BoidType boidType) {
        super(name);
        this.boidType = boidType;
        this.update = true;

        this.configurable = true;
        this.saveConfig = true;
        this.config(Integer.class, ((build, value) -> ((BoidSpawnerBuild) build).amount = value));

        this.buildVisibility = BuildVisibility.editorOnly;
        this.category = Category.effect;
    }

    public class BoidSpawnerBuild extends Building {
        int amount = defaultAmount;

        @Override
        public void update() {
            int counter = 0;
            while(counter < amount){
                int bx = Mathf.random(0, world.width() - 1);
                int by = Mathf.random(0, world.height() - 1);

                if(Boid.isEnvironmentWall(world.tile(bx, by)) || Boid.isPlayerBuild(world.tile(bx, by))){
                    continue;
                }

                Boid b = Boid.create();
                b.vel.setToRandomDirection();
                b.x = bx * tilesize;
                b.y = by * tilesize;
                b.setType(boidType);
                b.add();

                counter += 1;
            }
            tile.setNet(Blocks.air);
        }

        @Override
        public void buildConfiguration(Table table) {
            table.label(() -> String.valueOf(amount));
            table.row();
            table.slider(1, maxAmount, 10, amount, f -> configure((int) f));
        }

        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (this == other) {
                this.deselect();
                return false;
            } else {
                return true;
            }
        }

        @Override
        public Integer config() {
            return amount;
        }

        public void write(Writes write) {
            super.write(write);
            write.i(amount);
        }

        public void read(Reads read, byte revision) {
            super.read(read, revision);
            this.amount = read.i();
        }
    }
}
