package subdustry.world.blocks.environment;

import arc.math.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;

public class BrainCoral extends Prop {

    public Effect effect = Fx.airBubble;
    public float effectSpacing = 60;

    public BrainCoral(String name) {
        super(name);
        variants = 1;
        breakable = alwaysReplace = false;
        solid = true;
        hasShadow = true;
        customShadow = true;
        clipSize = 100;
    }

    @Override
    public void drawBase(Tile tile) {
        super.drawBase(tile);
        if (Time.time % effectSpacing <= 1 && !Vars.state.isPaused()) {
            effect.at(tile.worldx() + Mathf.random(-Vars.tilesize/2, Vars.tilesize/2), tile.worldy() + Mathf.random(-Vars.tilesize/2, Vars.tilesize/2));
        }
    }
}
