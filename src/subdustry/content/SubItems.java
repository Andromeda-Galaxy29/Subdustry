package subdustry.content;

import arc.graphics.*;
import mindustry.type.*;

public class SubItems {
    public static Item
            metalSalvage, titaniumOre, copperOre, quartz, caveSulfur, acidMushroom, glass;

    public static void load() {
        metalSalvage = new Item("metal-salvage", Color.valueOf("#cbcadb")) {{
            hardness = 1;
        }};

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

        caveSulfur = new Item("cave-sulfur", Color.valueOf("#d0ae1f")){{
            flammability = 1;
            explosiveness = 0.6f;
        }};

        acidMushroom = new Item("acid-mushroom", Color.valueOf("#f3c4e7")){{
            flammability = 0.3f;
            charge = 0.01f;
        }};

        glass = new Item("glass", Color.valueOf("#65b7ff"));
    }
}
