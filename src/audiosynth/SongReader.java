package audiosynth;

import audiosynth.instrument.*;

import java.awt.Color;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class SongReader {
    private final Map<String, Instrument> instruments = new HashMap<>();

    public SongReader() {
        instruments.put("sine", new SineWave());
        instruments.put("tri", new TriangularWave());
        instruments.put("square", new SquareWave());
        instruments.put("saw", new SawtoothWave());
        instruments.put("noise", new Noise());

//        instruments.put("sine", wavelength -> n -> Math.sin(n * 2 * Math.PI / wavelength);
//        instruments.put("tri", wavelength -> n -> Math.abs(n / wavelength % 1 - 0.5) * 4 - 1);
//        etc.
    }

    public Song readSong(String resourceName) {
        return readSong(getClass().getResourceAsStream("/" + resourceName));
    }

    public Song readSong(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter("\\s*,\\s*|\\s+");
        Song song = new Song();
        while(scanner.hasNext()) {
            String instrumentName = scanner.next();
            Instrument instrument = Objects.requireNonNull(
                instruments.get(instrumentName), "No instrument named " + instrumentName);
            double pitch = scanner.nextDouble();
            double startTime = scanner.nextDouble();
            double duration = scanner.nextDouble();
            song.addNote(new Note(instrument, pitch, startTime, duration));
        }
        return song;
    }
}
