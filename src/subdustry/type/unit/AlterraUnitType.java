package subdustry.type.unit;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.ammo.*;
import mindustry.world.meta.*;

public class AlterraUnitType extends UnitType{

    public AlterraUnitType(String name){
        super(name);
        outlineColor = Color.valueOf("#424558");
        ammoType = new PowerAmmoType();
    }
}