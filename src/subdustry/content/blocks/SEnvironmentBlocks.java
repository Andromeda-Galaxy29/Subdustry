package subdustry.content.blocks;

import arc.graphics.Color;
import mindustry.content.Blocks;
import mindustry.graphics.Pal;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.*;
import subdustry.content.*;
import subdustry.world.blocks.environment.*;

public class SEnvironmentBlocks {
    public static Block

    cliff, cliffHelper,
    // Harvesting Nodes
    limestoneOutcrop, seabedQuartz, seabedAcidMushroom,
    // Spawners
    peeperSpawner, boomerangSpawner,
    // Floors
    limestone, limesand, limeSeagrassFloor, tableCoralFloor, tubeCoralFloor, acidMycelium,
    steelFloor, steelPanels, wreckFloor, wreckTiles, wreckVent,
    greenSeagrassFloor, creepvineRoots, mossyStone,
    // Geysers
    limestoneGeyser,
    stoneGeyser, mossyStoneGeyser,
    // Walls
    limestoneWall, limesandWall, tableCoralWall, tubeCoralWall,
    steelWall, wreckWall,
    greenSeagrassWall, mossyStoneWall,
    // Props
    limeSeagrass, slantedShellPlates, tableCoral, tubeCoral, giantCoralTube, floatingRock, writhingWeed, veinedNettle, brainCoral,
    greenSeagrass, creepvine;

    public static void load() {

        cliff = new SCliff("cliff");
        cliffHelper = new SCliffHelper("cliff-helper");

        //Harvesting Nodes
        limestoneOutcrop = new HarvestingNode("limestone-outcrop") {{
            drops.addAll(SItems.titanium, SItems.copperOre);
            variants = 3;
            minDropAmount = 4;
            maxDropAmount = 5;
            color = Color.valueOf("#c0905c");
        }};

        seabedQuartz = new HarvestingNode("seabed-quartz") {{
            drops.addAll(SItems.quartz);
            variants = 2;
            minDropAmount = 4;
            maxDropAmount = 5;
            color = Color.valueOf("#9aa7c3");
        }};

        seabedAcidMushroom = new HarvestingNode("seabed-acid-mushroom") {{
            drops.addAll(SItems.acidMushroom);
            variants = 3;
            minDropAmount = 1;
            maxDropAmount = 1;
            minGrowTime = 80;
            maxGrowTime = 120;
            color = Color.valueOf("#a89fd6");
        }};

        // Spawners
        peeperSpawner = new BoidSpawner("peeper-spawner", BoidTypes.peeper);

        boomerangSpawner = new BoidSpawner("boomerang-spawner", BoidTypes.boomerang);

        // Floors
        limestone = new SFloor("limestone") {{
            variants = 3;
            cliffLightColor = Color.valueOf("c5a879");
        }};

        limesand = new SFloor("limesand") {{
            variants = 3;
            cliffLightColor = Color.valueOf("fbd9a9");
        }};

        limeSeagrassFloor = new SFloor("lime-seagrass-floor") {{
            variants = 3;
            cliffLightColor = Color.valueOf("e2e597");
        }};

        tableCoralFloor = new SFloor("table-coral-floor") {{
            variants = 3;
            cliffLightColor = Color.valueOf("fa992f");
        }};

        tubeCoralFloor = new SFloor("tube-coral-floor") {{
            variants = 3;
            cliffLightColor = Color.valueOf("f7d485");
            cliffDarkColor = Color.valueOf("936534");
        }};

        acidMycelium = new SFloor("acid-mycelium") {{
            variants = 3;
            cliffLightColor = Color.valueOf("a89fd6");
            cliffDarkColor = Color.valueOf("080823");
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

        greenSeagrassFloor = new SFloor("green-seagrass-floor") {{
            variants = 3;
            cliffLightColor = Color.valueOf("95c85b");
        }};

        creepvineRoots = new SFloor("creepvine-roots") {{
            variants = 2;
            cliffLightColor = Color.valueOf("79b647");
        }};

        mossyStone = new SFloor("mossy-stone") {{
            variants = 3;
            cliffLightColor = Color.valueOf("696971");
            cliffDarkColor = Color.valueOf("2e2e31");
        }};

        // Geysers
        limestoneGeyser = new SteamVent("limestone-geyser") {{
            parent = blendGroup = limestone;
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

        // Walls
        limestoneWall = new StaticWall("limestone-wall") {{
            limestone.asFloor().wall = this;
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
            attributes.set(SAttributes.metal, 1f);
        }};

        wreckWall = new StaticWall("wreck-wall") {{
            wreckFloor.asFloor().wall = this;
            wreckTiles.asFloor().wall = this;
            wreckVent.asFloor().wall = this;
            variants = 3;
            attributes.set(SAttributes.metal, 1f);
        }};

        greenSeagrassWall = new StaticWall("green-seagrass-wall") {{
            greenSeagrassFloor.asFloor().wall = this;
            creepvineRoots.asFloor().wall = this;
            variants = 2;
        }};

        mossyStoneWall = new StaticWall("mossy-stone-wall") {{
            mossyStone.asFloor().wall = this;
            variants = 2;
        }};

        // Props
        limeSeagrass = new SeaBush("lime-seagrass") {{
            limeSeagrassFloor.asFloor().decoration = this;
        }};

        slantedShellPlates = new Prop("slanted-shell-plates") {{
            variants = 3;
        }};

        tableCoral = new WallProp("table-coral") {{
            variants = 2;
        }};

        tubeCoral = new WallProp("tube-coral") {{
            variants = 2;
        }};

        giantCoralTube = new ShapedProp("giant-coral-tube") {{
            variants = 1;
        }};

        floatingRock = new FloatingProp("floating-rock") {{
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

        greenSeagrass = new Seaweed("green-seagrass") {{
            greenSeagrassFloor.asFloor().decoration = this;
            mossyStone.asFloor().decoration = this;
            variants = 3;
        }};

        creepvine = new Kelp("creepvine") {{
            itemDrop = SItems.creepvineSeedCluster;
        }};
    }
}
