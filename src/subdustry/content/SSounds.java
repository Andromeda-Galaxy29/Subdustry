package subdustry.content;

import arc.audio.Sound;
import mindustry.Vars;

public class SSounds {
    public static Sound muffledExplosion;
    public static void load(){
        muffledExplosion = Vars.tree.loadSound("muffledExplosion");
            }
}
