# Visualization specification

Your goal: create a class to visualize your song model on the screen.

# `SongVisualization` class

```java
/**
 * Visualizes a Song as a collection of multicolored rectangles.
 */
public class SongVisualization extends GraphicsGroup
    public static final int MAX_PITCH = 120;

    private final Map<Waveform,Color> waveformColors = new HashMap<>();
    // other instance variables?

    /**
     * Creates an empty song visualization.
     *
     * @param pixelsPerSecond   Horizontal distance of one second
     * @param pixelsPerSemitone Number of pixels per pitch unit
     */
    public SongVisualization(double pixelsPerSecond, double pixelsPerSemitone)

    /**
     * Shows the notes of the given song, removing any song already present.
     */
    public void showSong(Song song)
    // Remove all existing graphics
    //
    // For each note:
    //
    //    Create a rectangle using the pixelsPerSecond, pixelsPerSemitone,
    //    and the note's data to compute the coordinates.
    //
    //    Tip: Use (MAX_PITCH - note.getPitch()) to make higher notes
    //    be higher on the screen instead of lower.
    //
    //    Set the rectangle’s fill color, then add it to your graphics group.


    /**
     * A helper method you can use to generate different colors for different
     * notes based on their waveform. (Study this. How does it work?)
     */
    private Color getNoteColor(Note note) {
        Waveform waveform = note.getWaveform();
        Color color = waveformColors.get(waveform);
        if (color == null) {
            color = Color.getHSBColor(waveformColors.size() * 0.382f % 1, 1, 0.6f);
            waveformColors.put(waveform, color);
        }
        return color;
    }
```

Replace the contents of the `main()` method in `AudioSynth` with the following code:

```java
String fileName = "kondo.csv";  // Also try "bach.csv"
Song song = new SongReader().readSong(fileName);

SongVisualization visualization = new SongVisualization(80, 6);
visualization.showSong(song);

CanvasWindow window = new CanvasWindow(
    fileName,
    (int) visualization.getWidth(),
    (int) visualization.getHeight());
window.add(visualization);
window.setBackground(Color.BLACK);

song.renderAudio().play();
```

You should get images like the ones at the top of the assignment README. Try following along as the music plays!
