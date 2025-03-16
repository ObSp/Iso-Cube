import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Scripts.Script;
import JGamePackage.JGame.Classes.UI.UIFrame;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Types.PointObjects.UDim2;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import JGamePackage.JGame.Types.StartParams.StartParams;
import NoiseMaps.TreeNoiseMap;
import NoiseMaps.WaterNoiseMap;
import Scripts.Generator;

public class IsoCube {
    static JGame game = new JGame(new StartParams(true, true, false));

    private static void visualizeNoiseMap() {
        game.Camera.Position = new Vector2(500);
        for (int x = 0; x < 100; x++) {

            for (int y = 0; y < 100; y++) {
                Box2D b = new Box2D();
                b.Size = new Vector2(10);
                b.FillColor = TreeNoiseMap.ShouldGenerateTree(x, y) ? Color.black : Color.white;
                b.Position = new Vector2(x, y).multiply(10);
                b.SetParent(game.WorldNode);
            }

        }
    }
    
    public static void main(String[] args) {
        game.WindowService.BackgroundColor = new Color(102, 204, 255);

        UIFrame tint = new UIFrame();
        tint.BackgroundColor = new Color(255, 204, 102);
        tint.BackgroundTransparency = 1;
        tint.Size = UDim2.fromScale(1, 1);
        tint.SetParent(game.UINode);
        //visualizeNoiseMap();

        new Script(Generator.class).SetParent(game.ScriptNode);
        
        game.InputService.OnKeyPress.Connect(kv ->{
            if (kv.getKeyCode() == KeyEvent.VK_DELETE) {
                System.exit(0);
            }
        });
    }
}
