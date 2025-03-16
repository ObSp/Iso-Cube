package Scripts;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.Classes.Scripts.Writable.WritableScript;
import JGamePackage.JGame.Classes.World.Box2D;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import NoiseMaps.SandNoiseMap;
import NoiseMaps.TreeNoiseMap;
import NoiseMaps.WaterNoiseMap;
import Other.Globals;
import Other.TileManager;

public class GeneratorRemake extends WritableScript {
    double gridSize = 100.0;

    Vector2 treeShadowOffset = new Vector2(-93, 14);
    Vector2 iHat = new Vector2(1, .5);
    Vector2 jHat = new Vector2(-1, .5);

    private Vector2 GetIsoPositionFromGridPosition(int x, int y) {
        return iHat.multiply(x).add(jHat.multiply(y)).multiply(gridSize).divide(2);
    }

    private void generate() {
        Image2D template = game.StorageNode.<Image2D>GetChild("TemplateBlock");
        template.SetImage("Assets\\NewBlock.png");
        template.Size = new Vector2(100);

        Image2D templateTree = game.WorldNode.<Image2D>GetChild("TemplateTree");
        Image2D templateTreeShadow = game.WorldNode.<Image2D>GetChild("TemplateTreeShadow");

        Box2D container = new Box2D();
        container.Transparency = 1.0;
        container.Name = "Container";
        container.SetParent(game.WorldNode);

        int mapSize = 10;
        
        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                Image2D block = template.Clone();
                block.Position = GetIsoPositionFromGridPosition(x, y);
                block.SetParent(container);

                block.ZIndex = (x + y);
            }
        }
    }
    
    @Override
    public void Start() {
        generate();

        game.InputService.OnKeyPress.Connect(kv ->{
            if (kv.getKeyCode() == KeyEvent.VK_R) {
                game.WorldNode.GetChild("Container").Destroy();
                Globals.SEED = (int) (Math.random() * 1000000);
                new Thread(this::generate).start();
            }
        });
    }
}
