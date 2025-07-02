package subdustry.world.blocks.environment;

import mindustry.world.blocks.environment.Prop;

/** Does nothing on its own. Used to place cliffs */
public class SCliffHelper extends Prop {

    public SCliffHelper(String name) {
        super(name);
        breakable = alwaysReplace = false;
        solid = true;
        fillsTile = false;
        hasShadow = false;
        variants = 0;
    }
}
