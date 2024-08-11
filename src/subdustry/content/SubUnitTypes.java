package subdustry.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.ai.*;
import mindustry.ai.types.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.PowerAmmoType;
import mindustry.type.unit.*;
import mindustry.type.weapons.*;
import mindustry.content.*;
import mindustry.world.meta.*;
import mindustry.world.meta.BlockFlag;
import subdustry.content.SubSounds;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;
import static mindustry.Vars.tilePayload;
import static mindustry.Vars.tilesize;

public class SubUnitTypes{
    public static UnitType

    seamoth,

    krill, prawn,

    glide;

    public static void load(){
        krill = new UnitType("krill"){{
            constructor = MechUnit::create;
            speed = 0.6f;
            hitSize = 10;
            health = 250;
            weapons.add(new Weapon("subdustry-krill-gun"){{
                reload = 25f;
                x = 4.5f;
                shootX = -0.25f;
                shootY = 5f;
                y = 1f;
                top = false;
                alternate = false;
                shootSound = Sounds.shootAlt;
                ejectEffect =new Effect(30f, e -> {
                    color(Color.sky, Color.lightGray, Pal.lightishGray, e.fin());
                    alpha(e.fout(0.3f));
                    float rot = Math.abs(e.rotation) + 90f;
                    int i = -Mathf.sign(e.rotation);

                    float len = (2f + e.finpow() * 6f) * i;
                    float lr = rot + e.fin() * 30f * i;
                    Fill.rect(
                            e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                            e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                            1f, 2f, rot + e.fin() * 50f * i
                    );
                });
                bullet = new BasicBulletType(4f, 12){{
                    lifetime = 24f;
                    sprite = "missile-large";
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = Color.lightGray;
                    width = 6f;
                    height = 8f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    trailLength = 10;
                    trailWidth = 2f;
                    trailParam = 1;
                }};
            }});
        }};

        prawn = new UnitType("prawn-2"){{
            constructor = MechUnit::create;
            speed = 0.6f;
            canBoost = true;
            boostMultiplier = 0.9f;
            engineColor = Pal.techBlue;
            hitSize = 18f;
            health = 1150;
            armor = 5f;

            weapons.add(new Weapon("subdustry-prawn-2-gun"){{
                reload = 35f;
                x = 7.75f;
                y = 3f;
                shootY = 7f;
                shootX = -0.25f;
                top = false;
                ejectEffect = new Effect(35f, e -> {
                    color(Color.sky, Color.lightGray, Pal.lightishGray, e.fin());
                    alpha(e.fout(0.5f));
                    float rot = Math.abs(e.rotation) + 90f;
                    int i = -Mathf.sign(e.rotation);
                    float len = (2f + e.finpow() * 10f) * i;
                    float lr = rot + e.fin() * 20f * i;
                    rect(Core.atlas.find("casing"),
                        e.x + trnsx(lr, len) + Mathf.randomSeedRange(e.id + i + 7, 3f * e.fin()),
                        e.y + trnsy(lr, len) + Mathf.randomSeedRange(e.id + i + 8, 3f * e.fin()),
                        2f, 3f, rot + e.fin() * 50f * i
                    );
                });
                shootSound = Sounds.missile;
                velocityRnd = 0.05f;
                shoot = new ShootAlternate(){{
                    shots = 2;
                    spread = 1;
                    barrels = 2;
                }};
                bullet = new MissileBulletType(0.5f, 25){{
                    splashDamage = 15;
                    drag = -0.5f;
                    lifetime = 10f;
                    height = 9f;
                    width = 8f;
                    sprite = "subdustry-torpedo";
                    frontColor = Color.sky;
                    backColor = Color.lightGray;
                    hitEffect = new MultiEffect(Fx.shockwave, new ParticleEffect(){{
                        colorFrom = Color.sky;
                        colorTo = Color.white;
                        length = 16f;
                        baseLength = 16f;
                        interp = Interp.pow3Out;
                        sizeInterp = Interp.slope;
                        sizeFrom = 0f;
                        sizeTo = 3f;
                        lifetime = 60f;
                    }});
                    splashDamageRadius = 15f;
                    despawnEffect = Fx.none;
                    hitSound = SubSounds.muffledExplosion;
                    despawnHit = true;
                    trailColor = Colors.sky;
                }};
            }});
        }};






        seamoth = new UnitType("seamoth"){{
            constructor = UnitEntity::create;
            speed = 2.5f;
            accel = 0.05f;
            drag = 0.04f;
            flying = true;
            health = 120;
            engineSize = 0f;
            targetFlags = new BlockFlag[]{BlockFlag.factory, null};
            hitSize = 11;
            itemCapacity = 15;

            parts.add(new RegionPart("-engine"){{
                growY = 0.2f;
                growX = 0.1f;
                growProgress = PartProgress.constant(1).blend(p -> Mathf.absin(11f, 1f) * p.warmup, 0.2f);
                progress = PartProgress.constant(1);
                y = -2f;
                outline = false;
                layerOffset = -0.01f;
            }});

            abilities.add(
                    new MoveEffectAbility(0f, -7f, Color.lightGray, new Effect(30, e -> {
                        color(e.color);
                        stroke(e.fout() + 0.2f);
                        Fill.circle(e.x, e.y, e.rotation * e.fout());
                    }), 5f)
            );

            weapons.add(new Weapon(){{
                y = 0f;
                x = 0f;
                reload = 50f;
                shootSound = Sounds.lasershoot;
                bullet = new BasicBulletType(2, 16){{
                    width = 10f;
                    height = 10f;
                    shrinkY = 0f;
                    shrinkX = 0f;
                    lifetime = 60f;
                    frontColor = Color.white;
                    sprite = "large-orb";
                    trailColor = backColor = Color.lightGray;
                    trailEffect = Fx.missileTrail;
                    trailInterval = 3f;
                    hitEffect = despawnEffect = Fx.hitSquaresColor;
                    hitColor = Color.white;
                    splashDamage = 10;
                    splashDamageRadius = 6f;
                    shootEffect = new MultiEffect(Fx.shootBigColor, Fx.shockwave);
                    smokeEffect = new Effect(17f, e -> {
                        color(Pal.lighterOrange, Color.lightGray, Color.gray, e.fin());
                        stroke(e.fout() + 0.2f);
                        randLenVectors(e.id, 8, e.finpow() * 19f, e.rotation, 10f, (x, y) -> {
                            Fill.circle(e.x + x, e.y + y, e.fout() * 2f + 0.2f);
                        });
                    });
                }};
            }});
        }};

        glide = new UnitType("glide"){{
            constructor = PayloadUnit::create;
            speed = 5f;
            coreUnitDock = true;
            isEnemy = false;
            targetPriority = -2;
            lowAltitude = false;
            flying = true;
            buildSpeed = 1f;
            drag = 0.08f;
            accel = 0.09f;
            rotateSpeed = 7f;
            itemCapacity = 70;
            health = 240f;
            armor = 1f;
            hitSize = 10f;
            engineSize = 0;
            payloadCapacity = 2f * 2f * tilesize * tilesize;
            pickupUnits = false;
            vulnerableWithPayloads = true;

            fogRadius = 0f;
            targetable = false;
            hittable = false;
            parts.add(new RegionPart("-engine"){{
                growY = 0.2f;
                growX = 0.1f;
                growProgress = PartProgress.constant(1).blend(p -> Mathf.absin(11f, 1f) * p.warmup, 0.2f);
                progress = PartProgress.constant(1);
                y = -1f;
                outline = false;
                layerOffset = -0.01f;
            }});

            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 0f;
                y = 5f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                repairSpeed = 3f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;
                mirror = false;
                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;
                bullet = new BulletType(){{
                    maxRange = 115f;
                }};
            }});
        }};


    }
}
