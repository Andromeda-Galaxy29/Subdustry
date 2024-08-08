package subdustry.content;

import arc.graphics.*;
import mindustry.type.*;

public class SubItems {
    public static Item
            titaniumOre, copperOre, quartz;

    public static void load() {
        titaniumOre = new Item("titanium", Color.valueOf("#919fe7")) {{
            hardness = 2;
            cost = 0.5f;
        }};

        copperOre = new Item("copper", Color.valueOf("#d99d73")) {{
            hardness = 2;
            cost = 0.5f;
        }};

        quartz = new Item("quartz", Color.valueOf("#dfe8ef")) {{
            hardness = 2;
        }};
    }
}
