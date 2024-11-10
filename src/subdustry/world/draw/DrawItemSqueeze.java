package subdustry.world.draw;

import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.gen.Building;
import mindustry.type.Item;

import static mindustry.Vars.itemSize;

public class DrawItemSqueeze extends DrawItemPile{
    public float sinMag = 4f;
    public float sinScl = 6f;
    public float sinOffset = 50f;
    public float lenOffset = -1f;

    public DrawItemSqueeze() {
    }

    public DrawItemSqueeze(Item item, float radius, int itemAmount) {
        super(item, radius, itemAmount);
    }

    @Override
    public void draw(Building build) {
        float len = Mathf.absin(build.totalProgress() + sinOffset, sinScl, sinMag) + lenOffset;
        Angles.randLenVectors(build.id, drawItemAmount(build), radius + len, (x, y) -> {
            Draw.rect(item.fullIcon, build.x + x, build.y + y, itemSize, itemSize);
        });
    }

}
