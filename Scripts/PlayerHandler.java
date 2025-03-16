package Scripts;

import JGamePackage.JGame.Classes.Scripts.Writable.WritableScript;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Classes.World.WorldBase;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import Other.TileManager;

public class PlayerHandler extends WritableScript {
    Image2D hoverEffect;
    Vector2 gridSize = new Vector2(42.875, 22);

    private double snapToGrid(double x, double blockSize) {
	    return Math.floor((x+(blockSize/2))/blockSize)*blockSize;
    }

    private Vector2 snapToGrid(Vector2 x) {
        return new Vector2(snapToGrid(x.X, gridSize.X), snapToGrid(x.Y, gridSize.Y));
    }

    @Override
    public void Start() {
        hoverEffect = game.WorldNode.<Image2D>GetChild("HoverEffect");
    }
    
    @Override
    public void Tick(double dt) {
        /*WorldBase target = game.InputService.GetMouseWorldTarget();
        if (target == null || !(target instanceof Image2D) || !target.Name.equals("TemplateBlock")) {
            hoverEffect.Visible = false;
            return;
        }
        hoverEffect.Visible = true;
        hoverEffect.Position = target.Position;

        hoverEffect.ZIndex = target.ZIndex + 2; */

        Vector2 mousePos = game.InputService.GetMouseWorldPosition().subtract(hoverEffect.Size.X/2, 25);

        int tileX = (int) ((mousePos.X / gridSize.X + mousePos.Y / gridSize.Y) /2);
        int tileY = (int) ((mousePos.Y / gridSize.Y -(mousePos.X / gridSize.X)) /2);

        Image2D tile = TileManager.GetTileAtPosition(tileX, tileY);
        if (tile == null) {
            hoverEffect.Visible = false;
            return;
        }
        hoverEffect.Visible = true;

        hoverEffect.Position = TileManager.GetTileAtPosition(tileX, tileY).Position;
        hoverEffect.ZIndex = 1000;
    }
}
