package subdustry.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.annotations.Annotations.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.world.meta.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class SubUnitTypes {
  public static UnitType

  seamoth;

  public static void load() {
    seamoth = new UnitType("seamoth"){{
      speed = 2.5f;
      accel = 0.05f;
      drag = 0.04f;
      flying = true;
      health = 120
      engineSize = 0f;
      targetFlags = new BlockFlag[]{BlockFlag.factory, null};
      hitSize = 11;
      itemCapacity = 15;
      weapons.add(new Weapon(){{
        y = 0f;
        x = 0f;
        reload = 50f;
        shootSound = Sounds.shootSnap;
        bullet = new BasicBulletType(2, 16){{
          width = 6f;
          height = 6f;
          shrinkY = 0f;
          shrinkX = 0f;
          lifetime = 60f;
          frontColor = Color.white;
          sprite = "large-orb";
          trailColor = backColor = Color.lightGray;
          trailEffect = Fx.missileTrail;
          trailInterval = 3
          hitEffect = despawnEffect = Fx.hitSquaresColor;
          hitColor = Color.white
          splashDamage = 10;
          splashDamageRadius = 6f;
          shootEffect = new MultiEffect(Fx.shootBigColor, Fx.shockwave);
          smokeEffect = new Effect(17f, e -> {
            color(Pal.lighterOrange, Color.lightGray, Color.gray, e.fin());
            stroke(e.fout() + 0.2f);
            randLenVectors(e.id, 8, e.finpow() * 19f, e.rotation, 10f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.2f);
            });
        }),
            }};
      }});
    }};
  }
}
