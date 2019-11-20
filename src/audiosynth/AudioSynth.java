package audiosynth;

import comp127graphics.CanvasWindow;

import java.awt.Color;

/**
 * Generates and visualizes music.
 */
public class AudioSynth {
    public static void main(String[] args) {
        String fileName = "kondo.csv";
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
    }
}
