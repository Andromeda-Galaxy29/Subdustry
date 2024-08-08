package subdustry.content;

import arc.graphics.Color;
import mindustry.content.Items;
import mindustry.gen.Sounds;
import mindustry.content.Fx;
import mindustry.type.Category;
import mindustry.world.Block;
import subdustry.blocks.environment.*;
import mindustry.entities.bullet.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.consumers.*;

import static mindustry.type.ItemStack.*;

public class SubBlocks {
    public static Block

    limestoneOutcrop, seabedQuartz, stab;

    public static void load(){
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
                smokeEffect = Fx.smokeCloud;
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
            recoil = -60f;
            size = 2;
            health = 420;
            shootSound = Sounds.shootAlt;
            consumePower(0.5f);
            coolant = consumeCoolant(0.2f);
        }};
    }
}
