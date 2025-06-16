package subdustry.world.blocks.environment;

import arc.math.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.world.*;

public class BrainCoral extends ShapedProp {

    public Effect effect = Fx.airBubble;
    public float effectSpacing = 60;

    public BrainCoral(String name) {
        super(name);
    }

    @Override
    public void drawBase(Tile tile) {
        super.drawBase(tile);
        if (isCenter(tile) && Time.time % effectSpacing <= 1 && !Vars.state.isPaused()) {
            effect.at(tile.worldx() + Mathf.random(-Vars.tilesize/2, Vars.tilesize/2), tile.worldy() + Mathf.random(-Vars.tilesize/2, Vars.tilesize/2));
        }
    }
}
