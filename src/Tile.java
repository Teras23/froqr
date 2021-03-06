import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tile extends Drawable {
    public static final int TILE_SIZE_X = 32;
    public static final int TILE_SIZE_Y = 32;

    private int type;
    private int posX;
    private long offset;

    public Tile(int type, int x, long o) {
        this.type = type;
        posX = x;
        offset = o;
    }

    public void draw(GraphicsContext gc) {
        if(type == TileType.GRASS_LIGHT) {
            gc.setFill(Color.GREEN.brighter());
        }
        else if(type == TileType.GRASS_DARK) {
            gc.setFill(Color.GREEN.darker());
        }
        else if(type == TileType.WATER) {
            gc.setFill(Color.BLUE.brighter());
        }
        else if(type == TileType.WATER_LEAF) {
            gc.setFill(Color.GREEN);
        }
        else {
            gc.setFill(Color.GRAY);
        }

        gc.fillRect(posX, offset / 1_000_000_000, 32, 32);
    }

    public void move(long a) {
        offset += a;
    }

    public int getType() {
        return type;
    }
}
