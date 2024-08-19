package subdustry.content;

import mindustry.type.SectorPreset;

public class Planet4546bSectors {
    public static SectorPreset safeShallows;

    public static void load(){
        safeShallows = new SectorPreset("safe-shallows", SubPlanets.planet4546B, 0){{
            difficulty = 1;
            captureWave = 15;
            alwaysUnlocked = true;
        }};

    }
}
