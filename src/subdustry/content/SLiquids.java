package subdustry.content;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class SLiquids {
    public static Liquid
            lubricant;

    public static void load() {
        lubricant = new Liquid("lubricant", Color.valueOf("#f7f006")){{
            heatCapacity = 0.7f;
            flammability = 0.09f;
            viscosity = 0.4f;
            coolant = true;
            boilPoint = 0.5f;
            gasColor = Color.grays(0.5f);
        }};
    }
}
