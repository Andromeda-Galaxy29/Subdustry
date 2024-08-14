package subdustry.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import mindustry.entities.Effect;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.Attribute;
import subdustry.blocks.environment.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.defense.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;
import subdustry.blocks.power.*;
import subdustry.blocks.production.*;
import subdustry.graphics.*;

import static mindustry.type.ItemStack.*;

public class SubBlocks {
    public static Block

    // Environment
    limestoneOutcrop, seabedQuartz, seabedAcidMushroom,

    limestoneFloor, limesand, shallowSeaGrassFloor, tableCoralFloor, tubeCoralFloor, acidMycelium,
    steelFloor, steelPanels, wreckFloor, wreckTiles, wreckVent,

    limestoneWall, tableCoralWall, tubeCoralWall, steelWall, wreckWall,

    shallowSeaGrass, tableCoral, tubeCoral, writhingWeed, veinedNettle,

    // Turrets
    stab,

    // Production/Drills
    harvester, metalGrinder,

    //Distribution
    submarineDuct,

    // Power
    solarPanel, copperWireNode,

    // Defense
    titaniumOreWall, largeTitaniumOreWall,

    // Effect/Storage
    coreShallows,

    //Precursor-only
    ionCubeSynthesizer;

    public static void load(){
        //Environment
        limestoneOutcrop = new HarvestingNode("limestone-outcrop"){{
            drops.addAll(SubItems.titaniumOre, SubItems.copperOre);
            minDropAmount = 6;
            maxDropAmount = 8;
            color = Color.valueOf("#c0905c");
        }};

        seabedQuartz = new HarvestingNode("seabed-quartz"){{
            drops.addAll(SubItems.quartz);
            minDropAmount = 6;
            maxDropAmount = 8;
            color = Color.valueOf("#9aa7c3");
        }};

        seabedAcidMushroom = new HarvestingNode("seabed-acid-mushroom"){{
            drops.addAll(SubItems.acidMushroom);
            minDropAmount = 1;
            maxDropAmount = 1;
            minGrowTime = 80;
            maxGrowTime = 120;
            color = Color.valueOf("#9aa7c3");
        }};


        limestoneFloor = new Floor("limestone-floor"){{
            variants = 3;
        }};

        limesand = new Floor("limesand"){{
            variants = 3;
        }};

        shallowSeaGrassFloor = new Floor("shallow-sea-grass-floor"){{
            variants = 3;
        }};

        tableCoralFloor = new Floor("table-coral-floor"){{
            variants = 3;
        }};

        tubeCoralFloor = new Floor("tube-coral-floor"){{
            variants = 3;
        }};

        acidMycelium = new Floor("acid-mycelium"){{
            variants = 3;
        }};

        steelFloor = new Floor("steel-floor"){{
            variants = 3;
        }};

        steelPanels = new Floor("steel-panels"){{
            variants = 3;
        }};

        wreckFloor = new Floor("wreck-floor"){{
            variants = 3;
        }};

        wreckTiles = new Floor("wreck-tiles"){{
            variants = 1;
        }};

        wreckVent = new Floor("wreck-vent"){{
            variants = 1;
        }};


        limestoneWall = new StaticWall("limestone-wall"){{
            limestoneFloor.asFloor().wall = this;
            variants = 2;
        }};

        tableCoralWall = new StaticWall("table-coral-wall"){{
            tableCoralFloor.asFloor().wall = this;
            variants = 2;
        }};

        tubeCoralWall = new StaticWall("tube-coral-wall"){{
            tubeCoralFloor.asFloor().wall = this;
            variants = 2;
        }};

        steelWall = new StaticWall("steel-wall"){{
            steelFloor.asFloor().wall = this;
            steelPanels.asFloor().wall = this;
            variants = 3;
            attributes.set(SubAttributes.metal, 1f);
        }};

        wreckWall = new StaticWall("wreck-wall"){{
            wreckFloor.asFloor().wall = this;
            wreckTiles.asFloor().wall = this;
            wreckVent.asFloor().wall = this;
            variants = 3;
            attributes.set(SubAttributes.metal, 1f);
        }};


        shallowSeaGrass = new SeaBush("shallow-sea-grass"){{
            shallowSeaGrassFloor.asFloor().decoration = this;
        }};

        tableCoral = new WallProp("table-coral"){{
            variants = 2;
        }};

        tubeCoral = new WallProp("tube-coral"){{
            variants = 2;
        }};

        writhingWeed = new Seaweed("writhing-weed"){{
            variants = 3;
        }};

        veinedNettle = new Seaweed("veined-nettle"){{
            variants = 1;
        }};

        //Turrets
        stab = new PowerTurret("stab"){{
            requirements(Category.turret, with(SubItems.titaniumOre, 25, SubItems.copperOre, 30));
            shootType = new RailBulletType(){{
                length = 24;
                pointEffectSpace = 60f;
                pierceEffect = Fx.hitBulletColor;
                pointEffect = Fx.none;
                hitEffect = Fx.hitBulletColor;
                smokeEffect = Fx.none;
                damage = 80;
                collidesAir = true;
                buildingDamageMultiplier = 0.2f;
                ammoMultiplier = 1f;
            }};
            reload = 65f;
            shootCone = 2f;
            rotateSpeed = 6f;
            targetAir = true;
            range = 24f;
            shootY = 0f;
            shootEffect = Fx.none;
            recoil = 0f;
            size = 2;
            health = 420;
            shootSound = Sounds.shootAlt;
            outlineColor = Color.valueOf("#171724");
            consumePower(0.5f);
            coolant = consumeCoolant(0.2f);
            drawer = new DrawTurret("alterra-"){{
                parts.addAll(
                        new RegionPart("-knife"){{
                            progress = PartProgress.recoil;
                            moveY = 14f;
                            mirror = false;
                        }}
                );
            }};
        }};

        // Production/Drills
        harvester = new Harvester("harvester"){{
            requirements(Category.production, with(SubItems.copperOre, 30, SubItems.titaniumOre, 30, SubItems.acidMushroom, 15));
            size = 2;
            squareSprite = false;
            range = 6;
            reload = 360;
            consumePower(1.5f);
        }};

        metalGrinder = new WallCrafter("metal-grinder"){{
            requirements(Category.production, with(SubItems.titaniumOre, 45, SubItems.quartz, 15));
            consumePower(1.5f);

            drillTime = 110f;
            size = 2;
            attribute = SubAttributes.metal;
            output = SubItems.metalSalvage;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
        }};

        //Distribution
        submarineDuct = new Duct("submarine-duct"){{
            requirements(Category.distribution, with(SubItems.titaniumOre, 1));
            health = 80;
            speed = 5f;
            researchCost = with(SubItems.titaniumOre, 10);
        }};

        // Power
        solarPanel = new SolarGenerator("solar-panel"){{
            requirements(Category.power, with(SubItems.copperOre, 15, SubItems.titaniumOre, 30, SubItems.quartz, 30));
            size = 2;
            powerProduction = 0.5f;
        }};

        copperWireNode = new WireNode("copper-wire-node"){{
            requirements(Category.power, with(SubItems.copperOre, 3));

            size = 1;
            range = 5;
        }};

        // Defense/Walls
        titaniumOreWall = new Wall("titanium-ore-wall"){{
            requirements(Category.defense, with(SubItems.titaniumOre, 6));
            health = 320;
            armor = 2f;
            size = 1;
        }};

        largeTitaniumOreWall = new Wall("titanium-ore-wall-large"){{
            requirements(Category.defense, ItemStack.mult(titaniumOreWall.requirements, 4));
            health = 320*4;
            armor = 2f;
            size = 2;
        }};

        // Effect/Storage
        coreShallows = new CoreBlock("core-shallows"){{
            requirements(Category.effect, with(SubItems.titaniumOre, 600, SubItems.copperOre, 700, SubItems.quartz, 400));
            alwaysUnlocked = true;
            //poopie

            isFirstTier = true;
            squareSprite = false;
            unitType = SubUnitTypes.glide;
            health = 2400;
            itemCapacity = 3500;
            size = 3;

            unitCapModifier = 10;
        }};

        //Precursor
        ionCubeSynthesizer = new GenericCrafter("ion-cube-synthesizer"){{
            size = 3;
            consumePower(100/60f);
            outputItem = new ItemStack(SubItems.ionCube, 4);
            craftTime = 240f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawGlowRegion("-glow-bottom"){{color = Color.valueOf("#117412");}},
                    new DrawSynthesis("-deposit"),
                    new DrawDefault(),
                    new DrawGlowRegion("-glow"){{color = Color.valueOf("#81e550");}}
            );

            ambientSound = Sounds.spellLoop;
            craftEffect = new Effect(60f, e -> {
                Draw.color(Color.valueOf("#81e550"));
                Angles.randLenVectors(e.id, e.fin(), 9, 16f, (x, y, fin, fout) -> {
                    Fill.poly(e.x + x, e.y + y, 4, 5f * fout);
                });
            });

            requirements(Category.production, with(SubItems.titaniumOre, 10, SubItems.ionCube, 5));
        }};
    }
}
