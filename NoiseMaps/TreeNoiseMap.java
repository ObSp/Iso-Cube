package NoiseMaps;

import Other.Globals;
import lib.Noise;

public class TreeNoiseMap {
    static double resolution = 15;
    static double amplitude = 10;

    static double spreadResolution = 1.5;
    
    public static boolean ShouldGenerateTree(double x, double y) {
        double value = (Noise.noise(x/resolution, Globals.SEED/resolution, y/resolution) * amplitude)/2*amplitude;

        if (!(value > .68)) return false;

        double spreadValue = (Noise.noise(x/spreadResolution, Globals.SEED/spreadResolution, y/spreadResolution) + 1)/2;

        return spreadValue > .6;
    }
}
