package subdustry.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.ShootHelix;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.weapons.*;
import mindustry.content.*;
import mindustry.world.meta.*;
import subdustry.type.unit.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static mindustry.Vars.*;

public class SUnitTypes {
    public static UnitType

    seamoth, nautilus, batyscaphe, cyclops, atlas,
    krill, prawn, crab, crayfish, lobster,
    glide,

    drone, droid, mechanoid, automaton, construct,
    keep, hold, secure, contain, preserve,
    probe, vessel, shuttle, satellite, station;

    public static void load(){
        krill = new AlterraUnitType("krill"){{
            constructor = MechUnit::create;
            speed = 0.6f;
            hitSize = 10;
            health = 250;

            envDisabled = Env.space;

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
                    backColor = Color.sky;
                    frontColor = Color.white;
                    trailColor = hitColor = Color.sky;
                    width = 6f;
                    height = 8f;
                    hitEffect = despawnEffect = Fx.hitBulletColor;
                    trailLength = 10;
                    trailWidth = 2f;
                    trailParam = 1;
                }};
            }});
        }};

        prawn = new AlterraUnitType("prawn"){{
            constructor = MechUnit::create;
            speed = 0.6f;
            canBoost = true;
            boostMultiplier = 0.9f;
            engineColor = Color.sky;
            engineOffset = 10;
            engineSize = 5;
            hitSize = 18f;
            health = 1150;
            armor = 5f;

            envDisabled = Env.space;

            weapons.add(new Weapon("subdustry-prawn-gun"){{
                reload = 35f;
                x = 28 / 4f;
                y = 12 / 4f;
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
                shoot.shots = 2;
                bullet = new MissileBulletType(0.5f, 25){{
                    splashDamage = 15;
                    drag = -0.08f;
                    lifetime = 35f;
                    shootEffect = Fx.shootBigColor;
                    height = 12f;
                    width = 10f;
                    sprite = "subdustry-torpedo";
                    keepVelocity = false;
                    backColor = Color.sky;
                    frontColor = Color.white;
                    trailColor = hitColor = Color.sky;
                    hitEffect = new MultiEffect(Fx.shockwave, new ParticleEffect(){{
                        colorFrom = Color.sky;
                        colorTo = Color.white;
                        length = 16f;
                        baseLength = 2f;
                        interp = Interp.pow3Out;
                        sizeInterp = Interp.slope;
                        sizeFrom = 0f;
                        sizeTo = 3f;
                        lifetime = 60f;
                    }});
                    splashDamageRadius = 15f;
                    despawnEffect = Fx.none;
                    hitSound = SSounds.muffledExplosion;
                    despawnHit = true;
                    homingDelay = 10;
                    weaveScale = 12f;
                    weaveMag = 2f;
                }};
            }});
        }};

        seamoth = new AlterraUnitType("seamoth"){{
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

            envEnabled = Env.underwater | Env.space;

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
                    backColor = Color.sky;
                    frontColor = Color.white;
                    trailColor = hitColor = Color.sky;
                    sprite = "large-orb";
                    trailEffect = Fx.missileTrail;
                    trailInterval = 3f;
                    hitEffect = despawnEffect = Fx.hitSquaresColor;
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

        glide = new AlterraUnitType("glide"){{
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

            envRequired = Env.underwater;

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

        //Precursor
        drone = new PrecursorUnitType("drone"){{
            constructor = LegsUnit::create;

            speed = 0.75f;
            drag = 0.2f;
            hitSize = 9;
            rotateSpeed = 3;
            health = 200;
            armor = 0;
            faceTarget = true;
            targetAir = true;

            legStraightness = 0.1f;
            stepShake = 0;
            legCount = 4;
            legLength = 8;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -1;
            legBaseOffset = 3;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.1f;
            legGroupSize = 2;
            legMoveSpace = 1.2f;
            groundLayer = Layer.legUnit;

            shadowElevation = 0.1f;

            weapons.addAll(
                    new Weapon("subdustry-drone-weapon"){{
                        x = 4;
                        y = 0;
                        reload = 24;
                        shootSound = Sounds.spark;
                        mirror = true;
                        rotate = true;
                        alternate = true;
                        rotateSpeed = 2;
                        rotationLimit = 45;
                        bullet = new LaserBulletType(){{
                            damage = 14;
                            colors = new Color[]{
                                    Color.valueOf("#81e550"),
                                    Color.valueOf("#ffffff")
                            };
                            hitEffect = Fx.hitLancer;
                            sideLength = 2;
                            hitSize = 3;
                            lifetime = 8;
                            drawSize = 300;
                            collidesAir = true;
                            healPercent = 5;
                            collidesTeam = true;
                            length = 50;
                            width = 4;
                            pierce = false;
                            pierceCap = 1;
                        }};
                    }}
            );
        }};

        droid = new PrecursorUnitType("droid"){{
            constructor = LegsUnit::create;

            speed = 0.6f;
            drag = 0.2f;
            hitSize = 13;
            rotateSpeed = 3;
            health = 600;
            armor = 0;
            faceTarget = true;
            targetAir = true;

            legCount = 6;
            legLength = 14;
            lockLegBase = false;
            legContinuousMove = false;
            legExtension = -3f;
            legBaseOffset = 5f;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.95f;
            legForwardScl = 0.7f;
            groundLayer = Layer.legUnit;

            shadowElevation = 0.2f;

            weapons.addAll(
                    new Weapon("subdustry-droid-weapon"){{
                        x = -6;
                        y = -3;
                        reload = 24;
                        shootSound = Sounds.artillery;
                        mirror = true;
                        rotate = true;
                        alternate = true;
                        rotateSpeed = 2;
                        rotationLimit = 45;
                        bullet = new BasicBulletType() {{
                            speed = 3;
                            damage = 22;
                            lifetime = 30;
                            width = 14;
                            height = 14;
                            trailWidth = 2.5f;
                            trailLength = 8;
                            backSprite = "large-bomb-back";
                            sprite = "large-bomb";
                            backColor = trailColor = hitColor = Color.valueOf("81e550");
                            frontColor = Color.valueOf("#ffffff");
                            shootEffect = Fx.shootSmallColor;
                            despawnEffect = hitEffect = new Effect(9, e -> {
                                color(Color.white, e.color, e.fin());
                                stroke(1f + e.fout());
                                Lines.square(e.x, e.y, e.fin() * 10f, e.rotation + 45f);

                                Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                            });

                            healPercent = 12;
                            collidesTeam = true;

                            fragBullets = 4;
                            fragSpread = 90;
                            fragRandomSpread = 0;
                            fragVelocityMax = 1;
                            fragVelocityMin = 1;
                            fragLifeMax = 1;
                            fragLifeMin = 1;
                            fragBullet = new BasicBulletType() {{
                                speed = 3;
                                damage = 8;
                                lifetime = 10;
                                width = 6;
                                height = 6;
                                trailWidth = 1.5f;
                                trailLength = 8;
                                backSprite = "large-bomb-back";
                                sprite = "large-bomb";
                                backColor = trailColor = hitColor = Color.valueOf("81e550");
                                frontColor = Color.valueOf("#ffffff");
                                hitEffect = despawnEffect = Fx.hitBulletColor;

                                healPercent = 3;
                                collidesTeam = true;
                            }};
                        }};
                    }}
            );
        }};

        keep = new PrecursorUnitType("keep"){{
            constructor = ElevationMoveUnit::create;

            drag = 0.07f;
            speed = 1.5f;
            rotateSpeed = 5f;
            accel = 0.09f;

            hitSize = 9;
            rotateSpeed = 8;
            health = 220;
            armor = 0;
            faceTarget = true;
            targetAir = true;

            hovering = true;

            shadowElevation = 0.1f;

            parts.addAll(
                    new HoverPart(){{
                        x = 4f;
                        y = -4;
                        mirror = true;
                        radius = 5;
                        phase = 90;
                        stroke = 2;
                        layerOffset = -0.01f;
                        color = Color.valueOf("e174f7");
                    }},
                    new HoverPart(){{
                        x = 4f;
                        y = 3f;
                        mirror = true;
                        radius = 5;
                        phase = 90;
                        stroke = 2;
                        layerOffset = -0.01f;
                        color = Color.valueOf("e174f7");
                    }}
            );

            abilities.add(new MoveEffectAbility(0f, -5f, Color.valueOf("e174f7"), Fx.missileTrailShort, 4f));

            weapons.addAll(
                    new Weapon("subdustry-keep-weapon"){{
                        x = 0;
                        y = 0;
                        reload = 48;
                        shootSound = Sounds.missile;
                        mirror = false;
                        rotate = false;
                        top = false;
                        layerOffset = -0.005f;
                        shoot = new ShootHelix(){{
                            mag = 3;
                            scl = 3;
                        }};
                        bullet = new BasicBulletType(2f, 13){{
                            lifetime = 30f;
                            backColor = Color.valueOf("e174f7");
                            frontColor = Color.white;
                            trailColor = hitColor = Color.valueOf("e174f7");
                            width = 10f;
                            height = 10f;
                            hitEffect = despawnEffect = Fx.hitBulletColor;
                            trailEffect = Fx.colorTrail;
                            trailInterval = 2;
                            trailLength = 10;
                            trailWidth = 2f;
                            trailParam = 2;
                        }};
                    }}
            );
        }};

        probe = new PrecursorUnitType("probe"){{
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 2.2f;
            rotateSpeed = 12;
            accel = 0.1f;
            drag = 0.04f;
            flying = true;
            health = 240;
            hitSize = 8;
            engineSize = 0;
            faceTarget = true;
            crashDamageMultiplier = 0f;

            parts.addAll(
                    new ShapePart(){{
                        y = -6.3f;
                        layerOffset = -0.01f;
                        progress = PartProgress.constant(1);
                        rotateSpeed = 2;
                        color = Color.valueOf("71dfff");
                        sides = 4;
                        hollow = true;
                        stroke = 0f;
                        strokeTo = 1.2f;
                        radius = 2.8f;
                    }},
                    new ShapePart(){{
                        y = -6.3f;
                        layerOffset = -0.011f;
                        progress = PartProgress.constant(1);
                        rotateSpeed = 2;
                        color = Color.white;
                        sides = 4;
                        hollow = false;
                        strokeTo = 1f;
                        radius = 2.8f;
                    }}
            );

            abilities.add(new MoveEffectAbility(0f, -6.3f, Color.valueOf("71dfff"),
                new Effect(22, e -> {
                    color(e.color);
                    Fill.poly(e.x, e.y, 4, e.rotation * e.fout(), Time.time * 2);
                }).layer(Layer.bullet - 0.001f),
            4f));


            weapons.addAll(
                    new Weapon("subdustry-probe-weapon"){{
                        x = 17/4f;
                        y = -11/4f;
                        reload = 18;
                        shootSound = Sounds.lasershoot;
                        ejectEffect = Fx.none;
                        bullet = new BasicBulletType(){{
                            speed = 3;
                            damage = 8;
                            lifetime = 10;
                            width = 8;
                            height = 8;
                            trailWidth = 2f;
                            trailLength = 8;
                            backColor = trailColor = hitColor = Color.valueOf("71dfff");
                            frontColor = Color.valueOf("#ffffff");
                            shootEffect = Fx.shootSmallColor;
                            despawnEffect = hitEffect = new Effect(9, e -> {
                                color(Color.white, e.color, e.fin());
                                stroke(0.7f + e.fout());
                                Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                                Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                            });

                            fragBullets = 2;
                            fragSpread = 90;
                            fragRandomSpread = 0;
                            fragVelocityMax = 1;
                            fragVelocityMin = 1;
                            fragLifeMax = 1;
                            fragLifeMin = 1;
                            fragBullet = new BasicBulletType(){{
                                speed = 3;
                                damage = 5;
                                lifetime = 25;
                                width = 8;
                                height = 8;
                                trailWidth = 2f;
                                trailLength = 8;
                                backColor = trailColor = hitColor = Color.valueOf("71dfff");
                                frontColor = Color.valueOf("#ffffff");
                                despawnEffect = hitEffect = new Effect(9, e -> {
                                    color(Color.white, e.color, e.fin());
                                    stroke(0.7f + e.fout());
                                    Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                                    Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                                });
                                homingPower = 0.19f;
                                homingDelay = 4f;
                            }};
                        }};
                    }}
            );
        }};
    }
}
