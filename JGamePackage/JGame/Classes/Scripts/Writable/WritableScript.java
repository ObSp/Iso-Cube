package JGamePackage.JGame.Classes.Scripts.Writable;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Classes.Scripts.Script;

public abstract class WritableScript {
    /**A reference to the {@code Script} object
     */
    public Script script;

    public JGame game;

    public void Start() {}

    public void Tick(double deltaTime) {}
}
