package Other;

import java.util.HashMap;

import JGamePackage.JGame.Classes.World.Image2D;

public class TileManager {
    public static HashMap<Integer, HashMap<Integer, Image2D>> tiles = new HashMap<>();

    public static Image2D GetTileAtPosition(int x, int y) {
        if (tiles.get(x) == null) return null;
        return tiles.get(x).get(y);
    }

    public static void PutTileAtPosition(int x, int y, Image2D tile) {
        if (tiles.get(x) == null) tiles.put(x, new HashMap<>());
        tiles.get(x).put(y, tile);
    }
}
