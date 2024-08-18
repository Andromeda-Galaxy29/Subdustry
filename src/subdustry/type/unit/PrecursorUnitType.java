package subdustry.type.unit;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.world.meta.*;

public class PrecursorUnitType extends UnitType{

    public PrecursorUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("#131817");
        ammoType = new PowerAmmoType();
    }
}