
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
     * •••••••••••••••••
     */
    public SongReader()

    /**
     * Reads song data from the given project resource.
     */
    public Song readSong(String resourceName)

    /**
     * Reads song data from an arbitrary input stream.
     */
    public Song readSong(InputStream in)
```



IntegrationTest// TODO: uncomment this when you think you have everything working


```java
String fileName = "simpletest.csv";
Song song = new SongReader().readSong(fileName);
song.renderAudio().play();
```