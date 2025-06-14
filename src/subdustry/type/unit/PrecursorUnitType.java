package subdustry.type.unit;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.*;
import mindustry.gen.Unit;
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

    @Override
    public Color cellColor(Unit unit) {
        float f = Mathf.clamp(unit.healthf());
        return Tmp.c1.set(Color.black).lerp(Color.white, f + Mathf.absin(Time.time, Math.max(f * 5.0F, 1.0F), 1.0F - f));
    }
}