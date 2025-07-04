package subdustry.content;

import mindustry.world.meta.*;

public class SAttributes {
    public static Attribute metal;

    public static void load(){
         metal = Attribute.add("subdustry-metal-salvage");
    }
}
