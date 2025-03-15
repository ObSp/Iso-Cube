import java.awt.Color;
import java.awt.event.KeyEvent;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Scripts.Script;
import Scripts.Generator;

public class IsoCube {
    static JGame game = new JGame();
    
    public static void main(String[] args) {
        game.WindowService.BackgroundColor = new Color(102, 204, 255);
        game.WindowService.SetFullscreen(true);
        new Script(Generator.class).SetParent(game.ScriptNode);
        
        game.InputService.OnKeyPress.Connect(kv ->{
            if (kv.getKeyCode() == KeyEvent.VK_DELETE) {
                System.exit(0);
            }
        });
    }
}
