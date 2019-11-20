# Specification for reading song files

Your goal: create a class to read song data from a file.

## `SongReader` class

Here we give you just a little started code:

```java
/**
 * A utility for reading Songs from text files. The text file consists of the following four values
 * in this order:
 *
 * - One of the following waveform names: sine tri square saw noise
 * - pitch in MIDI units
 * - start time in seconds
 * - duration in seconds
 *
 * ...repeated for every note in the song. Values may be separated by commas, spaces, or newlines.
 * The order of notes does not matter.
 */
public class SongReader

    /**
     * Creates a new song reader with the following name-to-waveform mappings:
     *
     * - "sine"   → SineWave
     * - "tri"    → TriangularWave
     * - "square" → SquareWave
     * - "saw"    → SawtoothWave
     * - "noise"  → Noise
     */
    public SongReader()
        // Hint: use a Map instance variable to track the name → waveform mappings

    /**
     * Reads song data from the given project resource.
     */
    public Song readSong(String resourceName) {
        return readSong(getClass().getResourceAsStream("/" + resourceName));
    }

    /**
     * Reads song data from an arbitrary input stream.
     */
    public Song readSong(InputStream in) {
        Scanner scanner = new Scanner(in).useDelimiter("\\s*,\\s*|\\s+");
        Song song = new Song();
        while(scanner.hasNext()) {
            // Use scanner.next() and scanner.nextDouble() to read the following
            // things, in this order:
            //
            // - waveform name
            // - pitch
            // - start time
            // - duration
            //
            // ...then use that data to create a new Note and add it to the song.
        }
        return song;
    }
```

Take a look at `res/simpletest.csv` if you want to see what the input data looks like.

Think you have it working? Look in `IntegrationTest`, and you will see a section marked:

```java
// TODO: uncomment this when you think you have everything working
```

An “integration test” is a test that checks that many individual pieces all work together properly.

Uncomment that section and see if the test passes.

## Grab some headphones and get your dancing shoes on

Did the integration test pass? **The moment of truth is upon you!** Replace the contents of the `main()` method in `AudioSynth` with the following code:

```java
String fileName = "simpletest.csv";
new SongReader()
    .readSong(fileName)
    .renderAudio()
    .play();
```

Did you hear something that sounds like music? 🕺💃🏾 If so, try replacing `simpletest.csv` with:

- `bach.csv`
- `kondo.csv`
