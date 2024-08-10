package subdustry.content;

import arc.graphics.Color;
import mindustry.entities.part.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import subdustry.blocks.environment.*;
import mindustry.world.blocks.storage.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.draw.*;
import subdustry.blocks.power.*;

import static mindustry.type.ItemStack.*;

public class SubBlocks {
    public static Block

    // Environment
    limestoneOutcrop, seabedQuartz, seabedAcidMushroom,
    limestoneFloor, greenSeaGrassFloor, tableCoralFloor, acidMycelium,
    limestoneWall, tableCoralWall,
    greenSeaGrass, writhingWeed, veinedNettle,

    // Turrets
    stab,

    // Power
    solarPanel, copperWireNode,

    // Effect/Storage
    coreShallows;

    public static void load(){
        //Environment
        limestoneOutcrop = new Outcrop("limestone-outcrop"){{
            drops.addAll(SubItems.titaniumOre, SubItems.copperOre);
            minDropAmount = 8;
            maxDropAmount = 10;
            color = Color.valueOf("#c0905c");
        }};

        seabedQuartz = new Outcrop("seabed-quartz"){{
            drops.addAll(SubItems.quartz);
            minDropAmount = 8;
            maxDropAmount = 10;
            color = Color.valueOf("#9aa7c3");
        }};

        seabedAcidMushroom = new Outcrop("seabed-acid-mushroom"){{
            drops.addAll(SubItems.acidMushroom);
            minDropAmount = 1;
            maxDropAmount = 1;
            minGrowTime = 80;
            maxGrowTime = 120;
            color = Color.valueOf("#9aa7c3");
        }};

        titaniumOreWall = new Wall("titaniumore-wall"){{
            requirements(Category.defense, with(SubItems.titaniumOre, 6));
            health = 320;
            armor = 2f;
            size = 1;
        }};

        largeTitaniumOreWall = new Wall("large-titaniumore-wall"){{
            requirements(Category.defense, ItemStack.mult(plastaniumWall.requirements, 4));
            health = 320*4;
            armor = 2f;
            size = 2;
        }};

        limestoneFloor = new Floor("limestone-floor"){{
            variants = 3;
        }};

        greenSeaGrassFloor = new Floor("green-sea-grass-floor"){{
            variants = 3;
        }};

        tableCoralFloor = new Floor("table-coral-floor"){{
            variants = 3;
        }};

        acidMycelium = new Floor("acid-mycelium"){{
            variants = 3;
        }};

        limestoneWall = new StaticWall("limestone-wall"){{
            limestoneFloor.asFloor().wall = this;
            variants = 2;
        }};

        tableCoralWall = new StaticWall("table-coral-wall"){{
            tableCoralFloor.asFloor().wall = this;
            variants = 2;
        }};

        greenSeaGrass = new SeaBush("green-sea-grass"){{
            greenSeaGrassFloor.asFloor().decoration = this;
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

        // Power
        solarPanel = new SolarGenerator("solar-panel"){{
            requirements(Category.power, with(SubItems.copperOre, 20, SubItems.titaniumOre, 40, SubItems.quartz, 40));
            size = 2;
            powerProduction = 0.5f;
        }};

        copperWireNode = new WireNode("copper-wire-node"){{
            requirements(Category.power, with(SubItems.copperOre, 3));

            size = 1;
            range = 5;
        }};

        // Effect/Storage
        coreShallows = new CoreBlock("core-shallows"){{
            requirements(Category.effect, with(SubItems.titaniumOre, 600, SubItems.copperOre, 700, SubItems.quartz, 400));
            alwaysUnlocked = true;
            //poopie

            isFirstTier = true;
            unitType = SubUnitTypes.glide;
            health = 2400;
            itemCapacity = 3500;
            size = 3;

            unitCapModifier = 10;
        }};
    }
}
