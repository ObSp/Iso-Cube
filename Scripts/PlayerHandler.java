package Scripts;

import JGamePackage.JGame.Classes.Scripts.Writable.WritableScript;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Classes.World.WorldBase;

public class PlayerHandler extends WritableScript {
    Image2D hoverEffect;


    @Override
    public void Start() {
        hoverEffect = game.WorldNode.<Image2D>GetChild("HoverEffect");
        hoverEffect.SetImage("Assets\\Hover.png");
        hoverEffect.ZIndex = 9;

        game.Camera.Position = game.Camera.Position.add(0, 250);
        game.Camera.DepthFactor = 1.2;
    }
    
    @Override
    public void Tick(double dt) {
        //Vector2 mousePos = game.InputService.GetMouseWorldPosition();//.subtract(hoverEffect.Size.X/2, 25);
        WorldBase target = game.InputService.GetMouseWorldTarget();
        if (target == null) {
            return;
        }
        hoverEffect.Position = target.Position;
    }
}
