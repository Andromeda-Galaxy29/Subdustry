package subdustry.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import subdustry.blocks.environment.*;

import static mindustry.type.ItemStack.*;

public class SubBlocks {
    public static Block

    limestoneOutcrop, seabedQuartz;

    public static void load(){
        limestoneOutcrop = new Outcrop("limestone-outcrop"){{
            drops.addAll(SubItems.titaniumOre, SubItems.copperOre);
            color = Color.valueOf("#c0905c");
        }};

        seabedQuartz = new Outcrop("seabed-quartz"){{
            drops.addAll(SubItems.quartz);
            minDropAmount = 1;
            maxDropAmount = 3;
            color = Color.valueOf("#9aa7c3");
        }};
    }
}
