package audiosynth;

import audiosynth.instrument.Instrument;
import comp127graphics.GraphicsGroup;
import comp127graphics.Line;
import comp127graphics.Rectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SongVisualization extends GraphicsGroup {
    public static final int MAX_PITCH = 88;

    private final double pixelsPerSecond, pixelsPerSemitone;
    private final double hairlinePosition = 200;
    private final Random rand = new Random();
    private final GraphicsGroup noteGroup;
    private final Map<Instrument,Color> instrumentColors = new HashMap<>();
    private final Map<Note,Rectangle> noteVisualizations = new HashMap<>();

    public SongVisualization(double pixelsPerSecond, double pixelsPerSemitone) {
        this.pixelsPerSecond = pixelsPerSecond;
        this.pixelsPerSemitone = pixelsPerSemitone;

        Line hairline = new Line(hairlinePosition, 0, hairlinePosition, (MAX_PITCH + 1) * pixelsPerSemitone);
        hairline.setStrokeColor(Color.DARK_GRAY);
        hairline.setStrokeWidth(0.5);
        add(hairline);

        noteGroup = new GraphicsGroup();
        add(noteGroup);
        setTime(0);
    }

    public void showSong(Song song) {
        noteGroup.removeAll();
        noteVisualizations.clear();

        for (Note note : song.getNotes()) {
            Rectangle rect = new Rectangle(
                note.getStartTime() * pixelsPerSecond,
                (88 - note.getPitch()) * pixelsPerSemitone,
                note.getDuration() * pixelsPerSecond,
                pixelsPerSemitone);
            rect.setStrokeWidth(0.5);
            rect.setFillColor(getNoteColor(note));
            noteGroup.add(rect);
            noteVisualizations.put(note, rect);
        }
    }

    private Color getNoteColor(Note note) {
        Instrument instrument = note.getInstrument();
        Color color = instrumentColors.get(instrument);
        if (color == null) {
            color = Color.getHSBColor(rand.nextFloat(), 1, 0.6f);
            instrumentColors.put(instrument, color);
        }
        return color;
    }

    public void setTime(double seconds) {
        noteGroup.setPosition(hairlinePosition - seconds * pixelsPerSecond, 0);

        for (var entry : noteVisualizations.entrySet()) {
            Note note = entry.getKey();
            Rectangle rect = entry.getValue();
            if (seconds >= note.getStartTime() && seconds <= note.getEndTime()) {
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
