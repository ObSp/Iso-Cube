package Scripts;

import JGamePackage.JGame.Classes.Scripts.Writable.WritableScript;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import Other.TileManager;

public class PlayerHandler extends WritableScript {
    Image2D hoverEffect;


    @Override
    public void Start() {
        hoverEffect = game.WorldNode.<Image2D>GetChild("HoverEffect");
        game.Camera.Position = game.Camera.Position.add(0, 250);
    }
    
    @Override
    public void Tick(double dt) {
        Vector2 mousePos = game.InputService.GetMouseWorldPosition();//.subtract(hoverEffect.Size.X/2, 25);

        int tileX = (int) ((mousePos.X / 25 + mousePos.Y / 25) /2);
        int tileY = (int) ((mousePos.Y / 25 -(mousePos.X / 25)) /2);

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
