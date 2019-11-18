package audiosynth;

public class Utils {
    public static double convertPitchToWavelength(double pitch) {
        return AudioBuffer.SAMPLE_RATE / (8.1757989 * Math.pow(2, pitch / 12));
    }

    public static int covertSecondsToSamples(double seconds) {
        return (int) Math.round(seconds * AudioBuffer.SAMPLE_RATE);
    }
}
