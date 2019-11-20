

```java
/**
 * Returns the end time of the last note in the whole piece.
 */
public double getDuration()

/**
 * Renders all the notes in the piece to an audio buffer.
 */
public AudioBuffer renderAudio()
```


```java
Song song = new Song();
song.addNote(new Note(new SineWave(), 51, 0, 2.0));
song.addNote(new Note(new TriangularWave(), 58, 0.5, 1.5));
song.addNote(new Note(new SquareWave(), 67, 1.0, 1.0));
song.renderAudio().play();
```
