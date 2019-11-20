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
        song.renderAudio().play();
    }
}
