package subdustry.content.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.distribution.Duct;
import mindustry.world.blocks.distribution.DuctRouter;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.SolarGenerator;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.Incinerator;
import mindustry.world.blocks.production.WallCrafter;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.*;
import subdustry.content.SubAttributes;
import subdustry.content.SubItems;
import subdustry.content.SubLiquids;
import subdustry.content.SubUnitTypes;
import subdustry.world.blocks.distribution.FlowDuct;
import subdustry.world.blocks.distribution.MultiDirectionDuctBridge;
import subdustry.world.blocks.power.TidalGenerator;
import subdustry.world.blocks.power.WireNode;
import subdustry.world.blocks.production.Harvester;
import subdustry.world.blocks.production.KelpCollector;
import subdustry.world.blocks.research.Fragment;
import subdustry.world.blocks.research.Scanner;
import subdustry.world.draw.DrawItemSqueeze;

import static mindustry.type.ItemStack.with;

public class SubAlterraBlocks {
    public static Block
    // Turrets
    dissolve,
    // Production/Drills
    harvester, metalGrinder, kelpCollector,
    // Distribution
    submergedDuct, submergedDuctRouter, submergedFlowDuct, submergedDuctBridge,
    // Liquid
    submergedConduit,
    // Power
    solarPanel, tidalGenerator, copperWireNode, battery,
    // Defense
    titaniumOreWall, largeTitaniumOreWall,
    // Crafting
    titaniumCrucible, rubberSynthesizer, lubricantSqueezer, glassSmelter, trashCan,
    // Effect/Storage
    coreShallows,
    // Research
    scanner,
    seamothFragment;

    public static void load(){
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

            coolant = consume(new ConsumeLiquid(SubLiquids.lubricant, 6f / 60f));
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

        kelpCollector = new KelpCollector("kelp-collector"){{
            requirements(Category.production, with(SubItems.titanium, 45, SubItems.copperOre, 30, SubItems.quartz, 15));
            consumePower(2f);

            reload = 180f;
            armRotation = 12;
            size = 2;
            squareSprite = false;
            researchCostMultiplier = 0.5f;
        }};

        //Distribution
        submergedDuct = new Duct("submarine-duct"){{
            requirements(Category.distribution, with(SubItems.titanium, 1));
            health = 80;
            speed = 5f;
            researchCost = with(SubItems.titanium, 5);
        }};

        submergedDuctRouter = new DuctRouter("submarine-duct-router"){{
            requirements(Category.distribution, with(SubItems.titanium, 5));
            health = 80;
            speed = 5f;
            solid = false;
            researchCost = with(SubItems.titanium, 15);
        }};

        submergedFlowDuct = new FlowDuct("submarine-flow-duct"){{
            requirements(Category.distribution, with(SubItems.titanium, 8, SubItems.copperOre, 8));
            health = 80;
            speed = 5f;
            solid = false;
            squareSprite = false;
            researchCostMultiplier = 1.5f;
            researchCost = with(SubItems.titanium, 40, SubItems.copperOre, 40);
        }};

        submergedDuctBridge = new MultiDirectionDuctBridge("submarine-duct-bridge"){{
            requirements(Category.distribution, with(SubItems.titanium, 6, SubItems.copperOre, 6));
            fadeIn = moveArrows = false;
            range = 4;
            speed = 74f;
            arrowSpacing = 6f;
            bufferCapacity = 14;
            ((Duct) submergedDuct).bridgeReplacement = this;
            researchCost = with(SubItems.titanium, 30, SubItems.copperOre, 30);
        }};

        // Liquid
        submergedConduit = new ArmoredConduit("submerged-conduit"){{
            requirements(Category.liquid, with(SubItems.titanium, 1, SubItems.quartz, 1));
            liquidCapacity = 16f;
            liquidPressure = 1f;
            health = 80;
            botColor = Color.valueOf("#2c2c3d");
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

        lubricantSqueezer = new GenericCrafter("lubricant-squeezer"){{
            requirements(Category.crafting, with(SubItems.titanium, 20, SubItems.quartz, 30, SubItems.siliconeRubber, 20));

            size = 2;

            ambientSound = Sounds.bioLoop;
            ambientSoundVolume = 0.03f;

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(SubLiquids.lubricant),
                    new DrawPistons(){{
                        sinMag = 2f;
                        sinScl = 5f;
                        sides = 4;
                        angleOffset = 45;
                    }},
                    new DrawItemSqueeze(SubItems.creepvineSeedCluster, 3.5f, 10){{
                        sinMag = 2f;
                        sinScl = 5f;
                    }},
                    new DrawDefault()
            );

            outputLiquid = new LiquidStack(SubLiquids.lubricant, 6 /60f);
            craftTime = 120;

            consumeItem(SubItems.creepvineSeedCluster, 2);
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

            isFirstTier = true;
            squareSprite = false;
            unitType = SubUnitTypes.glide;
            health = 2400;
            itemCapacity = 3500;
            size = 3;

            unitCapModifier = 10;
        }};

        // Research
        scanner = new Scanner("scanner"){{
            requirements(Category.effect, with(SubItems.titanium, 60, SubItems.copperOre, 30, SubItems.acidMushroom, 35));
            consumePower(30/60f);
            researchCostMultiplier = 0.1f;
        }};

        seamothFragment = new Fragment("seamoth-fragment"){{
            content = SubUnitTypes.seamoth;
            size = 2;
        }};
    }
}
