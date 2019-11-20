package audiosynth;

import audiosynth.waveform.Waveform;
import comp127graphics.GraphicsGroup;
import comp127graphics.Line;
import comp127graphics.Rectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Visualizes a Song as a collection of multicolored rectangles.
 */
public class SongVisualization extends GraphicsGroup {
    public static final int MAX_PITCH = 120;

    private final double pixelsPerSecond, pixelsPerSemitone;
    private final GraphicsGroup noteGroup;
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

        noteGroup = new GraphicsGroup();
        add(noteGroup);
    }

    /**
     * Shows the notes of the given song, removing any song already present.
     */
    public void showSong(Song song) {
        noteGroup.removeAll();

        for (Note note : song.getNotes()) {
            Rectangle rect = new Rectangle(
                note.getStartTime() * pixelsPerSecond,
                (MAX_PITCH - note.getPitch()) * pixelsPerSemitone,
                note.getDuration() * pixelsPerSecond,
                pixelsPerSemitone);
            rect.setStrokeWidth(0.5);
            rect.setFillColor(getNoteColor(note));
            noteGroup.add(rect);
        }
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
