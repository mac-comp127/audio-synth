# Song audio rendering specification

Your goal: create code to render your song model to an `AudioBuffer` so you can hear it.

## New methods in `Song`

```java
/**
 * Returns maximum end time of any note in the song. Returns 0 if the song is empty.
 */
public double getDuration()
    // Hint: you can use loops or streams

/**
 * Renders all the notes in the piece to a normalized audio buffer.
 */
public AudioBuffer renderAudio()
    // Sketch of the method:
    //
    // Create a new AudioBuffer. Compute the size of the buffer using:
    //
    //     Utils.covertSecondsToSamples(getDuration())
    //
    // For each note:
    //
    //     Call mix() on your audio buffer to mix the signal for the new note
    //     into the buffer at the correct time.
    //  
    //     Methods that will help you:
    //     - Utils.convertPitchToWavelength()
    //     - Utils.covertSecondsToSamples()
    //     - note.get_______()
    //
    // Call normalize() on your audio buffer. (This prevents the mixed audio
    // from being too loud.)
    //
    // Return the audio buffer.
```

Tests to add to `SongTest`:

```java
private Waveform waveform1 = wavelength -> t -> (t % 2 * 2 - 1) * CONCERT_A_WAVELENGTH * 3;

@Test
void getDuration() {
    assertEquals(0, song.getDuration());
    song.addNote(new Note(waveform0, 5, 4.5, 1.5));
    song.addNote(new Note(waveform1, 7, 10, 2));
    song.addNote(new Note(waveform0, 7, 9, 2.5));
    assertEquals(12, song.getDuration());
}

@Test
void renderAudio() {
    double sampleLen = 1.0 / AudioBuffer.SAMPLE_RATE;
    song.addNote(new Note(waveform0, 81, 2 * sampleLen, 4 * sampleLen));
    song.addNote(new Note(waveform1, 33, 4 * sampleLen, 5 * sampleLen));

    AudioBuffer audio = song.renderAudio();
    assertArrayEquals(
        new float[] { 0, 0, 0.25f, 0.25f, -0.5f, 1, -0.75f, 0.75f, -0.75f },
        audio.getSamples(),
        0.0001f);
}
```

## Try it!

Replace the contents of the `main()` method in `AudioSynth` with the following code:

```java
Song song = new Song();
song.addNote(new Note(new SineWave(), 51, 0, 2.0));
song.addNote(new Note(new TriangularWave(), 58, 0.5, 1.5));
song.addNote(new Note(new SquareWave(), 67, 1.0, 1.0));
song.renderAudio().play();
```

Run it and you should hear a nice Eb flat major triad. (You don’t need to know what that is if you’re not a musician! Just enjoy the nice sound.)
