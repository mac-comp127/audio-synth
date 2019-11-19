package audiosynth;

import audiosynth.waveform.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class SongReader {
    private final Map<String, Waveform> waveforms = new HashMap<>();

    public SongReader() {
        waveforms.put("sine", new SineWave());
        waveforms.put("tri", new TriangularWave());
        waveforms.put("square", new SquareWave());
        waveforms.put("saw", new SawtoothWave());
        waveforms.put("noise", new Noise());
    }

    public Song readSong(String resourceName) {
        return readSong(getClass().getResourceAsStream("/" + resourceName));
    }

    public Song readSong(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter("\\s*,\\s*|\\s+");
        Song song = new Song();
        while(scanner.hasNext()) {
            String waveformName = scanner.next();
            Waveform waveform = Objects.requireNonNull(
                waveforms.get(waveformName), "No waveform named " + waveformName);
            double pitch = scanner.nextDouble();
            double startTime = scanner.nextDouble();
            double duration = scanner.nextDouble();
            song.addNote(new Note(waveform, pitch, startTime, duration));
        }
        return song;
    }
}
