# Part 2: Render songs as audio

Your goal: create code to render your song model to an `AudioBuffer` so you can hear it.

This is where the abstract description of music in the `Song` class becomes actual audio.


## New method in `Song`

```java
/**
 * Renders all the notes in the piece to a normalized audio buffer.
 */
public AudioBuffer renderAudio() {
    ...
}
```

Sketch of the method:

1. Create a new AudioBuffer.

    Compute the size of the buffer using the song's duration. Pay attention to units!

    <details>
      <summary>(Click for hint about units)</summary>
        
      There is a method in `Song` that gives you the duration in _seconds_. But
      `AudioBuffer` needs you to specify a duration in _samples_.

      There is already a method that converts seconds to samples.

      <details>
      <summary>(Click for more of a hint)</summary>

      The method that converts seconds to samples is in the `Utils` class.
      <details>
      <summary>(Just show me how to use it!)</summary>

      `Utils.convertSecondsToSamples(getDuration())`
      </details>
      </details>

    </details>
    
2. For each note in the song:

    - Create a signal for the note by converting its pitch to a wavelength.
      (Use the class diagram you drew to help you.)
      <details>
      <summary>(Hint about unit conversion)</summary>

      There is another method in `Utils` that will help you.
      </details>
      <details>
      <summary>(Hint about creating the signal)</summary>

      Look at your class diagram. How can you get from `Note` to `Signal`?
      <details>
      <summary>(More of a hint about creating the signal)</summary>

      A `Note` has a `Waveform`. A `Waveform` can create a `Signal` if you
      specify the wavelength.
      </details>
      </details>

    - Tell your audio buffer to mix the signal into the buffer
      at the correct time and for the correct duration.

3. Call `normalize()` on your audio buffer. This prevents clipping in the mixed audio.

    <details>
    <summary>Aside: What is “clipping?”</summary>

    Digital audio playback always has a limited range of allowed amplitudes.
    In `AudioBuffer`, this range is -1…1. Audio outside this range gets “clipped:” the
    top of the wave gets flattened out so that the wave stays within the allowed bounds.
    The resulting audio is loud and distorted.

    It is OK if _intermediate_ calculations go outside the allowed range, but to avoid
    clipping, software must ensure that the final result stays within range. That is
    what calling `normalize()` will do for you.
    </details>

4. Return the audio buffer.



Add this test to `SongTest`:

```java
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

Does that work? Try uncommenting the commented-out code in `IntegrationTest`, which
checks all of the pieces working together.


## Let’s hear some music!

Did those tests pass? Run the main method in `AudioSynth`! You should hear a nice E♭ flat major triad.

If that works, then comment out the simple 3-note song, and uncomment the code below that loads a song from a data file. You can use both `kondo.csv` and `bach.csv` for testing.

Enjoy the tunes! Then, when you’re ready, proceed to the exciting next step.

Next: [Visualization](3_visualization.md)
