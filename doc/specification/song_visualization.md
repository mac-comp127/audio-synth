```java
/**
 * Visualizes a Song as a collection of multicolored rectangles.
 */
public class SongVisualization extends GraphicsGroup
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

    /**
     * Moves the visualization to show that the given time is the current time.
     *
     * @param seconds Time from the beginning of the song
     * @param done    True if the song is done playing
     */
    public void setTime(double seconds, boolean done)




SongVisualization visualization = new SongVisualization(80, 6);
visualization.showSong(song);

CanvasWindow window = new CanvasWindow(
    fileName,
    (int) visualization.getWidth(),
    (int) visualization.getHeight());
window.add(visualization);
window.setBackground(Color.BLACK);

song.renderAudio().play(
    visualization::setTime);
