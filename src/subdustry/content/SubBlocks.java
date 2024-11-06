package subdustry.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.Vec2;
import mindustry.entities.*;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;
import subdustry.world.blocks.distribution.*;
import subdustry.world.blocks.environment.*;
import subdustry.world.blocks.power.*;
import subdustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.defense.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;
import subdustry.world.draw.*;

import static mindustry.type.ItemStack.*;

public class SubBlocks {
    public static Block

    // Environment
    limestoneOutcrop, seabedQuartz, seabedAcidMushroom,

    limestoneFloor, limesand, limeSeaGrassFloor, tableCoralFloor, tubeCoralFloor, acidMycelium,
    steelFloor, steelPanels, wreckFloor, wreckTiles, wreckVent,
    greenSeaGrassFloor, creepvineRoots, mossyStone,

    limestoneGeyser, stoneGeyser, mossyStoneGeyser,

    limestoneWall, limesandWall, tableCoralWall, tubeCoralWall, steelWall, wreckWall,
    greenSeaGrassWall, mossyStoneWall,

    limeSeaGrass, tableCoral, tubeCoral, writhingWeed, veinedNettle, brainCoral, greenSeaGrass, creepvine,

    // Turrets
    dissolve,

    // Production/Drills
    harvester, metalGrinder,

    //Distribution
    submarineDuct, submarineDuctRouter, submarineFlowDuct, submarineDuctBridge,

    // Power
    solarPanel, tidalGenerator, copperWireNode, battery,

    // Defense
    titaniumOreWall, largeTitaniumOreWall,

    // Crafting
    titaniumCrucible, rubberSynthesizer, glassSmelter, trashCan,

    // Effect/Storage
    coreShallows,

    //Precursor-only
    ionCubeSynthesizer;

    public static void load(){
        //Environment
        limestoneOutcrop = new HarvestingNode("limestone-outcrop"){{ //TODO: Better RNG for outcrops
            drops.addAll(SubItems.titanium, SubItems.copperOre);
            minDropAmount = 8;
            maxDropAmount = 10;
            color = Color.valueOf("#c0905c");
        }};

        seabedQuartz = new HarvestingNode("seabed-quartz"){{
            drops.addAll(SubItems.quartz);
            minDropAmount = 8;
            maxDropAmount = 10;
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

        limeSeaGrassFloor = new Floor("lime-sea-grass-floor"){{
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
            variants = 1;
        }};

        wreckTiles = new Floor("wreck-tiles"){{
            variants = 1;
        }};

        wreckVent = new Floor("wreck-vent"){{
            variants = 1;
        }};

        greenSeaGrassFloor = new Floor("green-sea-grass-floor"){{
            variants = 3;
        }};

        creepvineRoots = new Floor("creepvine-roots"){{
            variants = 2;
        }};

        mossyStone = new Floor("mossy-stone"){{
            variants = 3;
        }};


        limestoneGeyser = new SteamVent("limestone-geyser"){{
            parent = blendGroup = limestoneFloor;
            attributes.set(Attribute.steam, 1f);
            variants = 1;
            effectColor = Pal.ammo;
        }};

        stoneGeyser = new SteamVent("stone-geyser"){{
            parent = blendGroup = Blocks.stone;
            attributes.set(Attribute.steam, 1f);
            variants = 1;
            effectColor = Pal.ammo;
        }};

        mossyStoneGeyser = new SteamVent("mossy-stone-geyser"){{
            parent = blendGroup = mossyStone;
            attributes.set(Attribute.steam, 1f);
            variants = 1;
            effectColor = Pal.ammo;
        }};


        limestoneWall = new StaticWall("limestone-wall"){{
            limestoneFloor.asFloor().wall = this;
            variants = 2;
        }};

        limesandWall = new StaticWall("limesand-wall"){{
            limesand.asFloor().wall = this;
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

        greenSeaGrassWall = new StaticWall("green-sea-grass-wall"){{
            greenSeaGrassFloor.asFloor().wall = this;
            creepvineRoots.asFloor().wall = this;
            variants = 2;
        }};

        mossyStoneWall = new StaticWall("mossy-stone-wall"){{
            mossyStone.asFloor().wall = this;
            variants = 2;
        }};

        limeSeaGrass = new SeaBush("lime-sea-grass"){{
            limeSeaGrassFloor.asFloor().decoration = this;
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

        brainCoral = new BrainCoral("brain-coral"){{
            variants = 1;
        }};

        greenSeaGrass = new Seaweed("green-sea-grass"){{
            greenSeaGrassFloor.asFloor().decoration = this;
            mossyStone.asFloor().decoration = this;
            variants = 3;
        }};

        creepvine = new Kelp("creepvine"){{
            itemDrop = SubItems.creepvineSeedCluster;
        }};

        //Turrets
        dissolve = new ItemTurret("dissolve"){{
            researchCostMultiplier = 0.05f;
            reload = 60f;
            shoot.shots = 5;
            shoot.shotDelay = 3f;
            requirements(Category.turret, with(SubItems.titanium, 40, SubItems.copperOre, 20, SubItems.quartz, 30));
            range = 120f;
            size = 2;
            recoil = 1f;
            loopSound = Sounds.spray;
            shootSound = Sounds.flame2;
            consumeAmmoOnce = false;
            inaccuracy = 3f;
            squareSprite = false;
            outlineColor = Color.valueOf("#171724");
            ammo(
                SubItems.acidMushroom, new BasicBulletType(3.5f, 14){{
                    lifetime = 30f;
                    width = 8f;
                    height = 10f;
                    shrinkY = 0f;
                    shrinkX = 0f;
                    sprite = "circle-bullet";
                    frontColor = backColor = hitColor = SubItems.acidMushroom.color;

                    ammoMultiplier = 1f;
                    lifetime = 34f;
                    statusDuration = 60f * 2f;
                    despawnEffect = Fx.hitLiquid;
                    hitEffect = Fx.hitLiquid;
                    smokeEffect = Fx.none;
                    shootEffect = Fx.none;
                    drag = 0.001f;
                    knockback = 0.55f;
                    displayAmmoMultiplier = false;
                }}
            );

            drawer = new DrawTurret("alterra-"){{
                parts.addAll(
                        new RegionPart("-liquid-overlay"){{
                            progress = PartProgress.smoothReload;
                            color = SubItems.acidMushroom.color;
                            colorTo = new Color(1f, 1f, 1f, 0f);
                        }},
                        new RegionPart("-top")
                );
            }};
        }};

        // Production/Drills
        harvester = new Harvester("harvester"){{
            requirements(Category.production, with(SubItems.copperOre, 30, SubItems.titanium, 30, SubItems.acidMushroom, 15));
            size = 2;
            squareSprite = false;
            range = 6;
            reload = 360;
            consumePower(1f);
            researchCost = with(SubItems.copperOre, 30, SubItems.titanium, 30, SubItems.acidMushroom, 15);
        }};

        metalGrinder = new WallCrafter("metal-grinder"){{
            requirements(Category.production, with(SubItems.titanium, 45, SubItems.quartz, 15));
            consumePower(1.5f);

            drillTime = 120f;
            size = 2;
            attribute = SubAttributes.metal;
            output = SubItems.metalSalvage;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
            researchCostMultiplier = 0.5f;
        }};

        //Distribution
        submarineDuct = new Duct("submarine-duct"){{
            requirements(Category.distribution, with(SubItems.titanium, 1));
            health = 80;
            speed = 5f;
            researchCost = with(SubItems.titanium, 5);
        }};

        submarineDuctRouter = new DuctRouter("submarine-duct-router"){{
            requirements(Category.distribution, with(SubItems.titanium, 5));
            health = 80;
            speed = 5f;
            solid = false;
            researchCost = with(SubItems.titanium, 15);
        }};

        submarineFlowDuct = new FlowDuct("submarine-flow-duct"){{
            requirements(Category.distribution, with(SubItems.titanium, 8, SubItems.copperOre, 8));
            health = 80;
            speed = 5f;
            solid = false;
            squareSprite = false;
            researchCostMultiplier = 1.5f;
            researchCost = with(SubItems.titanium, 40, SubItems.copperOre, 40);
        }};

        submarineDuctBridge = new MultiDirectionDuctBridge("submarine-duct-bridge"){{
            requirements(Category.distribution, with(SubItems.titanium, 6, SubItems.copperOre, 6));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;
            ((Duct)submarineDuct).bridgeReplacement = this;
            researchCost = with(SubItems.titanium, 30, SubItems.copperOre, 30);
        }};

        // Power
        solarPanel = new SolarGenerator("solar-panel"){{
            requirements(Category.power, with(SubItems.copperOre, 15, SubItems.titanium, 30, SubItems.quartz, 30));
            size = 2;
            powerProduction = 0.5f;
            researchCost = with(SubItems.copperOre, 5, SubItems.titanium, 10, SubItems.quartz, 10);
        }};

        tidalGenerator = new TidalGenerator("tidal-generator"){{
            requirements(Category.power, with(SubItems.titanium, 20, SubItems.quartz, 35, SubItems.acidMushroom, 35));
            size = 3;
            powerProduction = 15/60f;
            researchCost = with(SubItems.titanium, 8, SubItems.quartz, 12, SubItems.acidMushroom, 12);
        }};

        copperWireNode = new WireNode("copper-wire-node"){{
            requirements(Category.power, with(SubItems.copperOre, 3));
            consumesPower = outputsPower = true;

            size = 1;
            range = 5;
            researchCost = with(SubItems.copperOre, 3);
            consumePowerBuffered(500f);
        }};

        battery = new Battery("battery"){{
            requirements(Category.power, with(SubItems.copperOre, 10, SubItems.acidMushroom, 20));
            consumePowerBuffered(4000f);
            emptyLightColor = Color.valueOf("8ca8e8");
            fullLightColor = Color.valueOf("d0efff");
            baseExplosiveness = 1f;
            size = 2;
            researchCost = with(SubItems.copperOre, 50, SubItems.acidMushroom, 100);
        }};

        // Defense/Walls
        titaniumOreWall = new Wall("titanium-ore-wall"){{
            requirements(Category.defense, with(SubItems.titanium, 6));
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

        // Crafting
        titaniumCrucible = new GenericCrafter("titanium-crucible"){{
            requirements(Category.crafting, with(SubItems.titanium, 40, SubItems.quartz, 30));

            size = 2;

            drawer = new DrawMulti(
                    new DrawDefault(),
                    new DrawRegion("-crusher", 4, true),
                    new DrawRegion("-crusher-top"),
                    new DrawFlame(Color.sky){
                        @Override
                        public TextureRegion[] icons(Block block){
                            return new TextureRegion[]{};
                        }
                    }
            );

            outputItem = new ItemStack(SubItems.titanium, 4);
            craftTime = 120;

            craftEffect = new Effect(40, e -> {
                Draw.color(Color.white);
                Lines.stroke(e.fout() * 1.5f);

                Angles.randLenVectors(e.id, 8, e.finpow() * 18f, (x, y) -> {
                    float ang = Mathf.angle(x, y);
                    Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1f);
                });
            });

            consumeItem(SubItems.metalSalvage, 1);
            consumePower(1f);

            researchCostMultiplier = 0.5f;
        }};

        rubberSynthesizer = new GenericCrafter("rubber-synthesizer"){{
            requirements(Category.crafting, with(SubItems.titanium, 40, SubItems.copperOre, 20, SubItems.quartz, 30));

            size = 3;

            ambientSound = Sounds.techloop;
            ambientSoundVolume = 0.02f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCircles(){{
                        color = Color.valueOf("51b5c3");
                        strokeMax = 2.5f;
                        radius = 10f;
                        amount = 3;
                    }},
                    new DrawRegion("-mid"),
                    new DrawBubbles(){{
                        color = Color.valueOf("51b5c3");
                    }},
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        color = Color.valueOf("51b5c3");
                        alpha = 0.7f;
                    }}
            );
            craftEffect = new RadialEffect(new Effect(160f, e -> {
                Vec2 v = new Vec2();

                Draw.color(Color.valueOf("51b5c3"));
                Draw.alpha(0.6f);

                Mathf.rand.setSeed(e.id);
                for(int i = 0; i < 3; i++){
                    float len = Mathf.rand.random(6f), rot = Mathf.rand.range(40f) + e.rotation;

                    e.scaled(e.lifetime * Mathf.rand.random(0.3f, 1f), b -> {
                        v.trns(rot, len * b.finpow());
                        Fill.circle(e.x + v.x, e.y + v.y, 2f * b.fslope() + 0.2f);
                    });
                }
            }), 4, 90f, 7f);

            outputItem = new ItemStack(SubItems.siliconeRubber, 2);
            craftTime = 120;

            consumeItem(SubItems.creepvineSeedCluster, 1);
            consumePower(1.5f);
        }};

        glassSmelter = new GenericCrafter("glass-smelter"){{
            requirements(Category.crafting, with(SubItems.titanium, 30, SubItems.copperOre, 20, SubItems.siliconeRubber, 20));

            size = 2;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.02f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawCrucibleFlame(),
                    new DrawRegion("-rotator", 3, true),
                    new DrawDefault(),
                    new DrawGlowRegion()
            );

            outputItem = new ItemStack(SubItems.glass, 1);
            craftTime = 60;

            consumeItem(SubItems.quartz, 2);
            consumePower(1.5f);
        }};

        trashCan = new Incinerator("trash-can"){{
            requirements(Category.crafting, with(SubItems.titanium, 10));

            size = 1;
            hasPower = false;
            squareSprite = false;
        }};

        // Effect/Storage
        coreShallows = new CoreBlock("core-shallows"){{
            requirements(Category.effect, with(SubItems.titanium, 600, SubItems.copperOre, 700, SubItems.quartz, 400));
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

            requirements(Category.production, with(SubItems.titanium, 10, SubItems.ionCube, 5));
        }};
    }
}
