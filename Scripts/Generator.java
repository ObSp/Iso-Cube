package Scripts;

import JGamePackage.JGame.Classes.Scripts.Writable.WritableScript;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;
import NoiseMaps.SandNoiseMap;
import NoiseMaps.TreeNoiseMap;
import NoiseMaps.WaterNoiseMap;

public class Generator extends WritableScript {
    double blockSize = 100;

    Vector2 moveRight = new Vector2(42.875 , -22).multiply(blockSize/100.0);
    Vector2 moveLeft = new Vector2(-moveRight.X , moveRight.Y);
    
    @Override
    public void Start() {
        Image2D template = game.StorageNode.<Image2D>GetChild("TemplateBlock");
        template.SetImage("Assets\\BlockLowOutline.png");

        Image2D templateTree = game.WorldNode.<Image2D>GetChild("TemplateTree");

        
        Vector2 lastLayerStart = null;

        for (int layer = -15; layer < 45; layer++) {
            int layerZIndex = -layer * 5;

            Vector2 lastBlockPos = null;

            for (int i = 0; i < 60; i++) {
                Vector2 pos;
                
                if (lastBlockPos == null) {

                    if (lastLayerStart == null) {
                        pos = Vector2.zero.subtract(0, layer * blockSize);
                    } else {
                        pos = lastLayerStart.add(moveLeft);
                    }

                    lastLayerStart = pos;

                } else {
                    pos = lastBlockPos.add(moveRight);
                }

                Image2D block = template.Clone();
                block.ZIndex = (int) pos.Y; //layerZIndex - i;
                block.Position = pos;
                block.Size = new Vector2(blockSize);

                lastBlockPos = block.Position;

                if (WaterNoiseMap.IsPointWater(i, layer)) {
                    block.SetImage("Assets\\WaterOutline.png");
                    block.Position = block.Position.add(0, 25);
                } else if (SandNoiseMap.IsPointSand(i, layer)) {
                    block.SetImage("Assets\\Sand.png");
                } else if (TreeNoiseMap.ShouldGenerateTree(i, layer)) {
                    Image2D tree = templateTree.Clone();
                    tree.Visible = true;
                    tree.ZIndex = block.ZIndex + 500;
                    tree.Position = block.Position;//.add(7, -30);
                    tree.SetParent(game.WorldNode);
                    block.SetImage("Assets\\Tree.png");
                } else if (Math.random() > .99) {
                    block.SetImage("Assets\\Decorated\\BlockStones1.png");
                }

                block.SetParent(game.WorldNode);
            }

        }
    }
}
