# Part 3: Visualize songs

Your goal: create a class to visualize songs on the screen.

---

Note that the design of the classes in the `model` package — `Song`, `Note`, and `SongWriter` — does
not make any assumptions about how the code is going to present the notes of a song. In the previous
part of the homework, you transformed that abstract structure into sound; in this step, you will
transform it into graphics.

In both cases, you start with _the same data._ The fact that this is possible is because there is a
**separation of concerns** in the code: some classes are only concerned with _what the data is_, and
others are only concerned with _how the data is presented._ This particular separation of concerns is
called a **model / view** separation, and presenting the same data in multiple ways is one of its typical
purposes. The same approach to model / view separation that lets you show the song song as both
graphics and sound might also, for example, let an email app show the same message data in both a
concise form in the inbox, and a detailed form for viewing individual messages.

Let’s do it.

---

Create a new `SongVisualization` class in the `audiosynth` package, using the following starter code:

```java
package audiosynth;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import audiosynth.audio.Waveform;
import audiosynth.model.Note;
import audiosynth.model.Song;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

/**
 * Visualizes a Song as a collection of multicolored rectangles.
 */
public class SongVisualization extends GraphicsGroup {
    public static final int MAX_PITCH = 120;

    private final double pixelsPerSecond, pixelsPerSemitone;
    private final Map<Waveform,Color> waveformColors = new HashMap<>();

    /**
     * Creates an empty song visualization.
     *
     * @param pixelsPerSecond   Horizontal distance of one second
     * @param pixelsPerSemitone Number of pixels per pitch unit
     */
    public SongVisualization(double pixelsPerSecond, double pixelsPerSemitone) {
        this.pixelsPerSecond = pixelsPerSecond;
        this.pixelsPerSemitone = pixelsPerSemitone;
    }

    /**
     * Shows the notes of the given song, removing any song already present.
     */
    public void showSong(Song song) {
        // TODO: Create visualization of song
    }

    private Color getNoteColor(Note note) {
        Waveform waveform = note.getWaveform();
        Color color = waveformColors.get(waveform);
        if (color == null) {
            color = Color.getHSBColor(waveformColors.size() * 0.382f % 1, 1, 0.6f);
            waveformColors.put(waveform, color);
        }
        return color;
    }
}
```

Now implement the `showSong` method. Here is a sketch of it:

- For each note in the song:

    - Create a rectangle:
        - Calculate the x position from the note’s start time.
        - Calculate the width from the note’s duration.
        - Calculate the y position from the note’s pitch.
        - The height of all notes is the same:

      Once again, unit conversion will be a problem. Here are some hints about it:
        - The horizontal axis in your visualization is time. To convert seconds to pixels,
          use the `pixelsPerSecond` instance variable.
        - The vertical axis in your visualization is pitch. The `Note` class measure pitch
          in “semitones” (which essentially measure how many keys up from the bottom of
          the piano you count to get this note).
        - The vertical axis is **flipped**: larger pitch numbers go _up_, not down. To
          flip the y axis, use `MAX_PITCH`.
          <details>
          <summary>(Huh? How do I do that?)</summary>

          Subtract each note’s pitch from `MAX_PITCH`, then convert to pixels
          <details>
          <summary>(I’m lost. Just show me how.)</summary>

          `(MAX_PITCH - note.getPitch()) * pixelsPerSemitone` is the rectangle’s y coordinate.
          </details>
          </details>

    - Give your rectangle the following appearance:
        - Stroke width of 0.5
        - Fill color determined by `getNoteColor`

    - Add your new rectangle to the graphics group.

        <details>
        <summary>(Wait, what graphics group? How?)</summary>

        `SongVisualization` _is a_ graphics group!
        <details>
        <summary>(Can I have a little more of a hint?)</summary>

        In the `showSong` method, `SongVisualization` needs to to call its own (inherited) `add` method.
        </details>
        </details>

Replace the contents of the `main()` method in `AudioSynth` with the following code:

```java
String fileName = "kondo.csv";  // Also try "bach.csv"
Song song = new SongReader().readSong(fileName);

SongVisualization visualization = new SongVisualization(80, 6);
visualization.showSong(song);

CanvasWindow window = new CanvasWindow(
    fileName,
    (int) visualization.getBounds().getMaxX(),
    (int) visualization.getBounds().getMaxY());
window.add(visualization);
window.setBackground(Color.BLACK);

song.renderAudio().play();
```

Run the main method. You should get images like the ones at the top of the assignment README. Try following along as the music plays! But gosh, the screen isn’t wide enough. If only it scrolled….

Next: [Animation](4_animation.md)
