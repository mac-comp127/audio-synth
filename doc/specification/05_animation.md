# Animation specification

Your goal: make the music scroll as it plays.

## New method for `SongVisualization`

```java
/**
 * Moves the visualization to show that the given time is the current time.
 *
 * @param seconds Time from the beginning of the song
 * @param done    True if the song is done playing
 */
public void setTime(double seconds, boolean done)
    // Move all the notes left based on seconds.
    //
    // Hint: Change the existing methods so that you add all the rectangles
    // to a _second_ GraphicsGroup inside this one. Then all this method
    // needs to do is change the position of that inner GraphicsGroup.
```

In the `main()` method in `AudioSynth`, change the last line that plays the music so that it calls your new `setTime()` method as the music plays:

```java
song.renderAudio().play(visualization::setTime);
```
