package subdustry.world.blocks.environment;

import arc.files.Fi;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;

import java.util.Scanner;

/** A prop that has a custom shape */
public class ShapedProp extends Prop {
    /** The shape is loaded from files with extension .shape in assets/prop-shapes
     *  0 - center, 1 - solid, . - empty */
    public Seq<Point2> shape = new Seq<>();
    public float shadowAlpha = 0.2f;
    public float shadowLayer = Layer.groundUnit + 1;

    public ShapedProp(String name) {
        super(name);
        solid = true;
        breakable = alwaysReplace = unitMoveBreakable = false;
        hasShadow = false;
        customShadow = true;
        variants = 0;
        layer = Layer.groundUnit + 2;
        fillsTile = false;
    }

    @Override
    public void init() {
        super.init();
        Fi file = Vars.tree.get("prop-shapes/"+name+".shape");
        if(file.exists()){
            Log.info("Found shape file " + file.nameWithoutExtension());
            shape = parseShapeFile(file);
        }else{
            Log.warn("Shape file for " + name + " is missing");
        }
        clipSize = getClipSize();
    }

    public Seq<Point2> parseShapeFile(Fi file){
        Seq<Point2> parsedShape = new Seq<>();
        Seq<String> lines = new Seq<>();

        try(Scanner scanner = new Scanner(file.read(512))) {
            while (scanner.hasNextLine()) {
                String lineString = scanner.nextLine();
                if (lineString.isEmpty()) {
                    continue;
                }
                lines.add(lineString);
            }
        }

        int centerX = -1;
        int centerY = -1;
        for (int y = 0; y < lines.size; y++){
            for (int x = 0; x < lines.get(y).length(); x++){
                char ch = lines.get(y).charAt(x);
                if(ch == '1'){
                    parsedShape.add(new Point2(x, y));
                }else if(ch == '0'){
                    parsedShape.add(new Point2(x, y));
                    if (centerX == -1 && centerY == -1){
                        centerX = x;
                        centerY = y;
                    }else{
                        Log.warn("Extra center point in shape " + file.nameWithoutExtension());
                    }
                }else if(ch != '.'){
                    Log.warn("Unexpected symbol " + lines.get(y).charAt(x) + " in shape " + file.nameWithoutExtension());
                }
            }
        }
        if(centerX != -1 && centerY != -1) {
            for (Point2 p : parsedShape) {
                p.x -= centerX;
                p.y -= centerY;
                p.y = - p.y; //Flips the y because in-game y goes from the bottom, in the file it goes from the top
            }
        }else{
            Log.warn("No center point in shape " + file.nameWithoutExtension());
        }
        return parsedShape;
    }

    public float getClipSize(){
        float maxOffset = 0;
        for (Point2 p : shape){
            maxOffset = Math.max(maxOffset, Math.max(Math.abs(p.x), Math.abs(p.y)));
        }
        return maxOffset * 2 * Vars.tilesize;
    }

    public boolean isCenter(Tile tile){
        for (Point2 p : shape){
            if (Vars.world.tile(tile.x + p.x, tile.y + p.y).block() != this){
                return false;
            }
        }
        return true;
    }

    @Override
    public void drawShadow(Tile tile) {

    }

    @Override
    public void drawBase(Tile tile) {
        if (isCenter(tile)) {
            Draw.z(shadowLayer);
            Draw.alpha(shadowAlpha);
            Draw.rect(variants == 0 ? customShadowRegion : variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))], tile.drawx(), tile.drawy());
            Draw.reset();
            Draw.z(layer);
            super.drawBase(tile);
        }
    }
}
