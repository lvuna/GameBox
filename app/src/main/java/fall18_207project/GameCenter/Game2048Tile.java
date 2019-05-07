package fall18_207project.GameCenter;

public class Game2048Tile extends Tile {

    /**
     * The background id to find the tile image.
     */
    private int background;
    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId The background
     */
    Game2048Tile(int backgroundId) {
        super(backgroundId);
        id = backgroundId + 1;

        switch (backgroundId + 1) {
            case 2:
                background = R.drawable.tile_2_2048;
                break;
            case 4:
                background = R.drawable.tile_4_2048;
                break;
            case 8:
                background = R.drawable.tile_8_2048;
                break;
            case 16:
                background = R.drawable.tile_16_2048;
                break;
            case 32:
                background = R.drawable.tile_32_2048;
                break;
            case 64:
                background = R.drawable.tile_64_2048;
                break;
            case 128:
                background = R.drawable.tile_128_2048;
                break;
            case 256:
                background = R.drawable.tile_256_2048;
                break;
            case 512:
                background = R.drawable.tile_512_2048;
                break;
            case 1024:
                background = R.drawable.tile_1024_2048;
                break;
            case 2048:
                background = R.drawable.tile_2048_2048;
                break;
            default:
                background = R.drawable.tile_blank_2048;
        }
    }
}
