package Scripts;

import JGamePackage.JGame.Classes.Scripts.Writable.WritableScript;
import JGamePackage.JGame.Classes.World.Image2D;
import JGamePackage.JGame.Types.PointObjects.Vector2;

public class Generator extends WritableScript {
    Vector2 moveRight = new Vector2(42.875 , -22);
    Vector2 moveLeft = new Vector2(-42.875 , -22);
    
    @Override
    public void Start() {
        Image2D template = game.StorageNode.<Image2D>GetChild("TemplateBlock");
        template.SetImage("Assets\\BlockLowOutline.png");
        
        /**Vector2 lastPos = null;
        Image2D firstBlock = null;

        for (int i = -5; i < 5; i++) {
            Image2D newBlock = block.Clone();

            Vector2 pos;
            if (lastPos == null) {
                firstBlock = newBlock;
                pos = Vector2.zero;
            } else {
                pos = lastPos.add(moveRight);
            }

            
            newBlock.Position = pos;
            newBlock.ZIndex = -i;
            newBlock.SetParent(game.WorldNode);

            lastPos = pos;
        }

        lastPos = firstBlock.Position;

        for (int i = 0; i < 9; i++) {
            Image2D newBlock = block.Clone();

            Vector2 pos = lastPos.add(moveLeft);
            newBlock.Position = pos;
            newBlock.ZIndex = -i;
            newBlock.SetParent(game.WorldNode);

            lastPos = pos;
        }**/

        
        Vector2 lastLayerStart = null;

        for (int layer = -15; layer < 45; layer++) {
            int layerZIndex = -layer * 5;

            Vector2 lastBlockPos = null;

            for (int i = 0; i < 60; i++) {
                Vector2 pos;
                
                if (lastBlockPos == null) {

                    if (lastLayerStart == null) {
                        pos = Vector2.zero.subtract(0, layer * 100);
                    } else {
                        pos = lastLayerStart.add(moveLeft);
                    }

                    lastLayerStart = pos;

                } else {
                    pos = lastBlockPos.add(moveRight);
                }

                Image2D block = template.Clone();
                block.ZIndex = layerZIndex - i;
                block.Position = pos;

                lastBlockPos = block.Position;

                block.SetParent(game.WorldNode);
            }

        }
    }
}
