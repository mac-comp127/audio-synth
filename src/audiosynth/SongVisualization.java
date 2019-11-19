package audiosynth;

import audiosynth.waveform.Waveform;
import comp127graphics.GraphicsGroup;
import comp127graphics.Line;
import comp127graphics.Rectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class SongVisualization extends GraphicsGroup {
    public static final int MAX_PITCH = 120;

    private final double pixelsPerSecond, pixelsPerSemitone;
    private final double hairlinePosition = 200;
    private final Line hairline;
    private final GraphicsGroup noteGroup;
    private final Map<Waveform,Color> waveformColors = new HashMap<>();
    private final Map<Note,Rectangle> noteVisualizations = new HashMap<>();

    public SongVisualization(double pixelsPerSecond, double pixelsPerSemitone) {
        this.pixelsPerSecond = pixelsPerSecond;
        this.pixelsPerSemitone = pixelsPerSemitone;

        hairline = new Line(hairlinePosition, 0, hairlinePosition, (MAX_PITCH + 1) * pixelsPerSemitone);
        hairline.setStrokeColor(Color.DARK_GRAY);
        hairline.setStrokeWidth(0.5);
        add(hairline);

        noteGroup = new GraphicsGroup();
        add(noteGroup);
        setTime(0, true);
    }

    public void showSong(Song song) {
        noteGroup.removeAll();
        noteVisualizations.clear();

        for (Note note : song.getNotes()) {
            Rectangle rect = new Rectangle(
                note.getStartTime() * pixelsPerSecond,
                (MAX_PITCH - note.getPitch()) * pixelsPerSemitone,
                note.getDuration() * pixelsPerSecond,
                pixelsPerSemitone);
            rect.setStrokeWidth(0.5);
            rect.setFillColor(getNoteColor(note));
            noteGroup.add(rect);
            noteVisualizations.put(note, rect);
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

    public void setTime(double seconds, boolean done) {
        noteGroup.setPosition(hairlinePosition - seconds * pixelsPerSecond, 0);
        hairline.setStroked(!done);

        for (var entry : noteVisualizations.entrySet()) {
            Note note = entry.getKey();
            Rectangle rect = entry.getValue();
            if (!done && seconds >= note.getStartTime() && seconds <= note.getEndTime()) {
                rect.setFillColor(Color.WHITE);
                rect.setStrokeColor(Color.WHITE);
            } else {
                rect.setFillColor(getNoteColor(note));
                rect.setStrokeColor(Color.BLACK);
            }
        }

        if (noteGroup.getCanvas() != null) {
            noteGroup.getCanvas().draw();
        }
    }
}
