package subdustry.content;

import arc.graphics.*;
import arc.struct.Seq;
import mindustry.content.Planets;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.world.meta.*;
import subdustry.game.SubTeams;
import subdustry.planet.*;

public class SubPlanets {
    public static Planet star4546, planet4546B, moon1, moon2;

    public static void load(){
        star4546 = new Planet("4546", null, 4f){{
            bloom = true;
            accessible = false;
            visible = true;
            solarSystem = this;

            meshLoader = () -> new SunMesh(
                    this, 4,
                    5, 0.3, 1.7, 1.2, 1,
                    1.1f,
                    Color.valueOf("ff9638"),
                    Color.valueOf("ffc64c"),
                    Color.valueOf("ffe371"),
                    Color.valueOf("ffe371"),
                    Color.valueOf("f4ee8e"),
                    Color.valueOf("fffbc1")
            );
        }};

        planet4546B = new Planet("4546b", star4546, 1.2f, 3){{
            generator = new Planet4546bGenerator();

            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, Color.valueOf("7ab2e3").a(0.75f), 2, 0.45f, 0.9f, 0.38f),
                    new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.valueOf("b0f2ff").a(0.75f), 2, 0.45f, 1f, 0.41f)
            );

            alwaysUnlocked = true;
            accessible = true;
            allowLaunchToNumbered = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = false;
            startSector = 0;
            allowWaveSimulation = true;
            clearSectorOnLose = true;
            allowWaves = true;
            prebuildBase = false;
            defaultCore = SubBlocks.coreShallows;

            defaultEnv = Env.terrestrial | Env.underwater | Env.groundOil | Env.groundWater;

            drawOrbit = true;
            orbitRadius = 50;
            rotateTime = 20 * 60;

            atmosphereColor = Color.valueOf("7cc9ffaa");
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.2f;

            visible = true;
            bloom = false;
            iconColor = Color.valueOf("5682c7");
            hasAtmosphere = true;

            sectorSeed = 1204;
            ruleSetter = r -> {
                r.waveTeam = SubTeams.precursor;
                r.showSpawns = true;
                r.coreIncinerates = true;
                r.ambientLight = Color.black.a(0.5f);
                r.weather = new Seq<Weather.WeatherEntry>(){
                    //No weather
                };
            };
        }};

        moon1 = new Planet("moon1", planet4546B, 0.7f){{
            generator = new Moon1Generator();
            meshLoader = () -> new HexMesh(this, 4);
            accessible = false;
            visible = true;
            hasAtmosphere = true;
            atmosphereColor = Color.valueOf("eb9745");
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.1f;
            iconColor = Color.valueOf("724b36");
            startSector = 10;
            orbitRadius = 5;
            orbitTime = 5 * 60;
        }};

        moon2 = new Planet("moon2", planet4546B, 0.3f){{
            generator = new Moon2Generator();
            meshLoader = () -> new HexMesh(this, 3);
            accessible = false;
            visible = true;
            hasAtmosphere = true;
            atmosphereColor = Color.valueOf("787986");
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.1f;
            iconColor = Color.valueOf("787986");
            startSector = 10;
            orbitRadius = 10;
            orbitTime = 10 * 60;
        }};
    }
}
