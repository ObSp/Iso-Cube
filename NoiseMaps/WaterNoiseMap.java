package NoiseMaps;

import Other.Globals;
import lib.Noise;

public class WaterNoiseMap {
    static double resolution = 25;
    
    public static boolean IsPointWater(double x, double y) {
        double value = (Noise.noise(x/resolution, y/resolution, Globals.SEED/resolution) + 1)/2;

        return value > .7;
    }
}
