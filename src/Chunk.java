import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.Random;

public class Chunk extends Drawable {
    public static final int CHUNK_TILE_AMT = 13;
    public static final int CHUNK_AMT = 16;

    private int type;
    private Tile[] tiles;
    private Random random;
    private long offset;
    private ArrayList<MovableObject> movableObjects;

    public Chunk(long offset, int seed) {
        this.offset = offset;

        random = new Random(seed);
        type = random.nextInt(4);

        movableObjects = new ArrayList<>();

        generateTiles();
        generateMovables();
    }

    public void draw(GraphicsContext gc) {
        for(int i = 0; i < tiles.length; i++) {
            tiles[i].draw(gc);
        }

        for(MovableObject movableObject : movableObjects) {
            movableObject.draw(gc);
        }
    }

    public void move(long o) {
        offset += o;

        for(Tile tile : tiles) {
            tile.move(o);
        }

        for(MovableObject movable : movableObjects) {
            movable.move(o);
        }
    }

    private void generateMovables() {
        //random.nextInt(20) - 10 - makes start position variable
        if(type == ChunkType.ROAD) {
            movableObjects.add(new MovableObject(offset, random.nextInt(3) + 2, random.nextInt(2), random.nextInt(20) - 10));
        }
        if(type == ChunkType.WATER_LOG) {
            movableObjects.add(new MovableObject(offset, random.nextInt(1) + 1, MovableObjectType.LOG, random.nextInt(20) - 10));
        }
    }

    private void generateTiles() {
        tiles = new Tile[CHUNK_TILE_AMT];

        int[] selectableTiles;

        if(type == ChunkType.GRASS) {
            selectableTiles = new int[]{TileType.GRASS_DARK,
                    TileType.GRASS_LIGHT};
        }
        else if(type == ChunkType.ROAD) {
            selectableTiles = new int[]{TileType.ROAD};
        }
        else if(type == ChunkType.WATER) {
            selectableTiles = new int[]{TileType.WATER, TileType.WATER_LEAF};
        }
        else if(type == ChunkType.WATER_LOG) {
            selectableTiles = new int[]{TileType.WATER};
        }
        else {
            selectableTiles = new int[]{1000};
        }

        for(int i = 0; i < tiles.length; i++) {
            int tileType = selectableTiles[random.nextInt(selectableTiles.length)];
            tiles[i] = new Tile(tileType, i * Tile.TILE_SIZE_X, offset);
        }
    }

    public boolean isOutOfScreen() {
        return offset >= (long)CHUNK_AMT * (long)Tile.TILE_SIZE_Y * 1_000_000_000L;
    }

    public long getOffset() {
        return offset;
    }

    public Tile getTile(int a) {
        return tiles[a];
    }

    public int getType() {
        return type;
    }

    public ArrayList<MovableObject> getMovableObjects() {
        return movableObjects;
    }
}
