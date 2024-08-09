package subdustry.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.content.Fx;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.power.SolarGenerator;
import subdustry.blocks.environment.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import subdustry.blocks.power.WireNode;

import static mindustry.type.ItemStack.*;

public class SubBlocks {
    public static Block

    limestoneOutcrop, seabedQuartz, stab, solarPanel, copperWireNode, coreShallows;

    public static void load(){
        coreShallows = new CoreBlock("core-shallows"){{
            requirements(category.effect, with(SubItems.titaniumOre, 600, SubItems.copperOre, 700, SubItems.quartz, 400));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = UnitTypes.glide;
            health = 2400;
            itemCapacity = 3500;
            size = 3;

            unitCapModifier = 10;
        }};

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
    }
}
