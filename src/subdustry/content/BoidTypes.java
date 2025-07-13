package subdustry.content;

import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.part.RegionPart;
import subdustry.type.BoidType;

public class BoidTypes {
    public static BoidType peeper, boomerang;

    public static void load(){
        peeper = new BoidType("peeper"){{
            parts.add(
                    new RegionPart(){{
                        suffix = "-tail";
                        layerOffset = 0.0005f;
                        rotation = -25f + 90f;
                        moveRot = 50f;
                        progress = p -> Mathf.sin(Time.time, 10f, 0.5f) + 0.5f;
                    }}
            );
        }};

        boomerang = new BoidType("boomerang"){{

        }};
    }
}
