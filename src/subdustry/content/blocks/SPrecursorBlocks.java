package subdustry.content.blocks;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawGlowRegion;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;
import subdustry.content.SItems;
import subdustry.world.draw.DrawSynthesis;

import static mindustry.type.ItemStack.with;

public class SPrecursorBlocks {
    public static Block ionCubeSynthesizer;

    public static void load() {
        //Precursor
        ionCubeSynthesizer = new GenericCrafter("ion-cube-synthesizer") {{
            size = 3;
            consumePower(100 / 60f);
            outputItem = new ItemStack(SItems.ionCube, 4);
            craftTime = 240f;
            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawGlowRegion("-glow-bottom") {{
                        color = Color.valueOf("#117412");
                    }},
                    new DrawSynthesis("-deposit"),
                    new DrawDefault(),
                    new DrawGlowRegion("-glow") {{
                        color = Color.valueOf("#81e550");
                    }}
            );

            ambientSound = Sounds.spellLoop;
            craftEffect = new Effect(60f, e -> {
                Draw.color(Color.valueOf("#81e550"));
                Angles.randLenVectors(e.id, e.fin(), 9, 16f, (x, y, fin, fout) -> {
                    Fill.poly(e.x + x, e.y + y, 4, 5f * fout);
                });
            });

            requirements(Category.production, with(SItems.titanium, 10, SItems.ionCube, 5));
        }};
    }
}
