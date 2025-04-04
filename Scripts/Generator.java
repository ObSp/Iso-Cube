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

public class Generator extends WritableScript {
    double blockSize = 100;

    Vector2 moveRight = new Vector2(42.875 , -22).multiply(blockSize/100.0);
    Vector2 moveLeft = new Vector2(-moveRight.X , moveRight.Y);
    Vector2 treeShadowOffset = new Vector2(-93, 14);

    private void generate() {
        Image2D template = game.StorageNode.<Image2D>GetChild("TemplateBlock");
        template.SetImage("Assets\\BlockLowOutline.png");

        Image2D templateTree = game.WorldNode.<Image2D>GetChild("TemplateTree");
        Image2D templateTreeShadow = game.WorldNode.<Image2D>GetChild("TemplateTreeShadow");

        templateTree.SetImage("Assets\\Tree.png");
        templateTreeShadow.SetImage("Assets\\TreeShadow.png");

        
        Vector2 lastLayerStart = null;

        Box2D blockLayer = new Box2D();
        blockLayer.Transparency = 1.0;
        blockLayer.Name = "BlockLayer";
        blockLayer.SetParent(game.WorldNode);

        Box2D entityDecorationLayer = new Box2D();
        entityDecorationLayer.Transparency = 1.0;
        entityDecorationLayer.Name = "EntityDecorationLayer";
        entityDecorationLayer.ZIndex = 10;
        entityDecorationLayer.SetParent(game.WorldNode);

        Box2D shadowLayer = new Box2D();
        shadowLayer.Transparency = 1.0;
        shadowLayer.Name = "ShadowLayer";
        shadowLayer.ZIndex = 8;
        shadowLayer.SetParent(game.WorldNode);

        for (int layer = -15; layer < 45; layer++) {

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
                block.ZIndex = -(layer + i);
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
                    tree.Position = block.Position.add(50, 35);
                    tree.ZIndex = (int) tree.Position.Y;
                    tree.SetParent(entityDecorationLayer);

                    Image2D treeShadow = templateTreeShadow.Clone();
                    treeShadow.Visible = true;
                    treeShadow.ZIndex = tree.ZIndex;
                    treeShadow.Position = tree.Position.add(treeShadowOffset);
                    treeShadow.SetParent(shadowLayer);
                } else if (Math.random() > .99) {
                    block.SetImage("Assets\\Decorated\\BlockStones1.png");
                } else if (Math.random() > .98) {
                    block.SetImage("Assets\\Decorated\\BlockPlants1.png");
                } else if (Math.random() > .98) {
                    block.SetImage("Assets\\Decorated\\BlockPlants2.png");
                } else if (Math.random() > .98) {
                    block.SetImage("Assets\\Decorated\\BlockPlants3.png");
                } else if (Math.random() > .98) {
                    block.SetImage("Assets\\Decorated\\BlockMix1.png");
                } else if (Math.random() > .992) {
                    block.SetImage("Assets\\Decorated\\BlockPlants4.png");
                } else if (Math.random() > .995) {
                    block.SetImage("Assets\\Decorated\\BlockPlants5.png");
                }

                block.SetCProp("ImagePath", block.GetImagePath());
                TileManager.PutTileAtPosition(i, layer, block);

                block.SetParent(blockLayer);
            }
        }
    }
    
    @Override
    public void Start() {
        generate();

        game.InputService.OnKeyPress.Connect(kv ->{
            if (kv.getKeyCode() == KeyEvent.VK_R) {
                game.WorldNode.GetChild("BlockLayer").Destroy();
                game.WorldNode.GetChild("EntityDecorationLayer").Destroy();
                game.WorldNode.GetChild("ShadowLayer").Destroy();
                Globals.SEED = (int) (Math.random() * 1000000);
                new Thread(this::generate).start();
            }
        });
    }
}
