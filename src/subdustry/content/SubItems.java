package subdustry.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.type.*;

public class SubItems {
    public static Item
            metalSalvage, titanium, copperOre, quartz, acidMushroom,
            caveSulfur, creepvineSeedCluster, siliconeRubber, glass,
            ionCube;

    public static final Seq<Item> subnauticaItems = new Seq<>();

    public static void load() {
        metalSalvage = new Item("metal-salvage", Color.valueOf("#cbcadb")) {{
            hardness = 1;
        }};

        titanium = new Item("titanium", Color.valueOf("#919fe7")) {{
            hardness = 2;
            cost = 0.5f;
        }};

        copperOre = new Item("copper-ore", Color.valueOf("#d99d73")) {{
            hardness = 2;
            cost = 0.5f;
        }};

        quartz = new Item("quartz", Color.valueOf("#dfe8ef")) {{
            hardness = 2;
        }};

        acidMushroom = new Item("acid-mushroom", Color.valueOf("#a89fd6")){{
            flammability = 0.3f;
            charge = 0.01f;
        }};

        caveSulfur = new Item("cave-sulfur", Color.valueOf("#d0ae1f")){{
            flammability = 1;
            explosiveness = 0.6f;
        }};

        creepvineSeedCluster = new Item("creepvine-seed-cluster", Color.valueOf("#f7f006")){{
            flammability = 0.3f;
        }};

        siliconeRubber = new Item("silicone-rubber", Color.valueOf("#173448"));

        glass = new Item("glass", Color.valueOf("#65b7ff"));

        //precursor-exclusive
        ionCube = new Item("ion-cube"){{
           charge = 0.75f;
           color = Color.valueOf("41a326");
        }};

        subnauticaItems.addAll(
                metalSalvage, titanium, copperOre, quartz, acidMushroom,
                caveSulfur, creepvineSeedCluster, siliconeRubber, glass,
                ionCube
        );
    }
}
