package subdustry.world.blocks.research;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import subdustry.content.SubItems;

public class Fragment extends Wall {

    public UnlockableContent content = Blocks.copperWall;

    public Fragment(String name) {
        super(name);
        requirements(Category.effect, ItemStack.with(SubItems.titanium, 8));
        breakable = false;
        inEditor = true;
        category = Category.effect;
    }

    @Override
    public boolean canBreak(Tile tile){
        return Vars.state.isEditor();
    }

    public class FragmentBuild extends WallBuild{

        @Override
        public void draw() {
            Draw.rect(region, x, y, Mathf.randomSeed(id, -60, 60));
        }

        @Override
        public boolean collide(Bullet other){
            return false;
        }

        @Override
        public void damage(float damage){
            // No damage
        }
    }
}
