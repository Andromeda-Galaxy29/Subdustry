package subdustry.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.graphics.Pal;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import subdustry.content.SubAttributes;
import subdustry.content.SubItems;
import subdustry.world.blocks.environment.*;

public class SubEnvironmentBlocks {
    public static Block
    // Harvesting Nodes
    limestoneOutcrop, seabedQuartz, seabedAcidMushroom,
    // Floors
    limestoneFloor, limesand, limeSeaGrassFloor, tableCoralFloor, tubeCoralFloor, acidMycelium,
    steelFloor, steelPanels, wreckFloor, wreckTiles, wreckVent,
    greenSeaGrassFloor, creepvineRoots, mossyStone,
    // Geysers
    limestoneGeyser, stoneGeyser, mossyStoneGeyser,
    // Walls
    limestoneWall, limesandWall, tableCoralWall, tubeCoralWall, steelWall, wreckWall,
    greenSeaGrassWall, mossyStoneWall,
    // Props
    limeSeaGrass, tableCoral, tubeCoral, writhingWeed, veinedNettle, brainCoral, greenSeaGrass, creepvine;

    public static void load() {
        //Environment
        limestoneOutcrop = new HarvestingNode("limestone-outcrop") {{
            drops.addAll(SubItems.titanium, SubItems.copperOre);
            minDropAmount = 8;
            maxDropAmount = 10;
            color = Color.valueOf("#c0905c");
        }};

        seabedQuartz = new HarvestingNode("seabed-quartz") {{
            drops.addAll(SubItems.quartz);
            minDropAmount = 8;
            maxDropAmount = 10;
            color = Color.valueOf("#9aa7c3");
        }};

        seabedAcidMushroom = new HarvestingNode("seabed-acid-mushroom") {{
            drops.addAll(SubItems.acidMushroom);
            minDropAmount = 1;
            maxDropAmount = 1;
            minGrowTime = 80;
            maxGrowTime = 120;
            color = Color.valueOf("#9aa7c3");
        }};

        limestoneFloor = new Floor("limestone-floor") {{
            variants = 3;
        }};

        limesand = new Floor("limesand") {{
            variants = 3;
        }};

        limeSeaGrassFloor = new Floor("lime-sea-grass-floor") {{
            variants = 3;
        }};

        tableCoralFloor = new Floor("table-coral-floor") {{
            variants = 3;
        }};

        tubeCoralFloor = new Floor("tube-coral-floor") {{
            variants = 3;
        }};

        acidMycelium = new Floor("acid-mycelium") {{
            variants = 3;
        }};

        steelFloor = new Floor("steel-floor") {{
            variants = 3;
        }};

        steelPanels = new Floor("steel-panels") {{
            variants = 3;
        }};

        wreckFloor = new Floor("wreck-floor") {{
            variants = 1;
        }};

        wreckTiles = new Floor("wreck-tiles") {{
            variants = 1;
        }};

        wreckVent = new Floor("wreck-vent") {{
            variants = 1;
        }};

        greenSeaGrassFloor = new Floor("green-sea-grass-floor") {{
            variants = 3;
        }};

        creepvineRoots = new Floor("creepvine-roots") {{
            variants = 2;
        }};

        mossyStone = new Floor("mossy-stone") {{
            variants = 3;
        }};


        limestoneGeyser = new SteamVent("limestone-geyser") {{
            parent = blendGroup = limestoneFloor;
            attributes.set(Attribute.steam, 1f);
            variants = 1;
            effectColor = Pal.ammo;
        }};

        stoneGeyser = new SteamVent("stone-geyser") {{
            parent = blendGroup = Blocks.stone;
            attributes.set(Attribute.steam, 1f);
            variants = 1;
            effectColor = Pal.ammo;
        }};

        mossyStoneGeyser = new SteamVent("mossy-stone-geyser") {{
            parent = blendGroup = mossyStone;
            attributes.set(Attribute.steam, 1f);
            variants = 1;
            effectColor = Pal.ammo;
        }};


        limestoneWall = new StaticWall("limestone-wall") {{
            limestoneFloor.asFloor().wall = this;
            variants = 2;
        }};

        limesandWall = new StaticWall("limesand-wall") {{
            limesand.asFloor().wall = this;
            variants = 2;
        }};

        tableCoralWall = new StaticWall("table-coral-wall") {{
            tableCoralFloor.asFloor().wall = this;
            variants = 2;
        }};

        tubeCoralWall = new StaticWall("tube-coral-wall") {{
            tubeCoralFloor.asFloor().wall = this;
            variants = 2;
        }};

        steelWall = new StaticWall("steel-wall") {{
            steelFloor.asFloor().wall = this;
            steelPanels.asFloor().wall = this;
            variants = 3;
            attributes.set(SubAttributes.metal, 1f);
        }};

        wreckWall = new StaticWall("wreck-wall") {{
            wreckFloor.asFloor().wall = this;
            wreckTiles.asFloor().wall = this;
            wreckVent.asFloor().wall = this;
            variants = 3;
            attributes.set(SubAttributes.metal, 1f);
        }};

        greenSeaGrassWall = new StaticWall("green-sea-grass-wall") {{
            greenSeaGrassFloor.asFloor().wall = this;
            creepvineRoots.asFloor().wall = this;
            variants = 2;
        }};

        mossyStoneWall = new StaticWall("mossy-stone-wall") {{
            mossyStone.asFloor().wall = this;
            variants = 2;
        }};

        limeSeaGrass = new SeaBush("lime-sea-grass") {{
            limeSeaGrassFloor.asFloor().decoration = this;
        }};

        tableCoral = new WallProp("table-coral") {{
            variants = 2;
        }};

        tubeCoral = new WallProp("tube-coral") {{
            variants = 2;
        }};

        writhingWeed = new Seaweed("writhing-weed") {{
            variants = 3;
        }};

        veinedNettle = new Seaweed("veined-nettle") {{
            variants = 1;
        }};

        brainCoral = new BrainCoral("brain-coral") {{
            variants = 1;
        }};

        greenSeaGrass = new Seaweed("green-sea-grass") {{
            greenSeaGrassFloor.asFloor().decoration = this;
            mossyStone.asFloor().decoration = this;
            variants = 3;
        }};

        creepvine = new Kelp("creepvine") {{
            itemDrop = SubItems.creepvineSeedCluster;
        }};
    }
}
