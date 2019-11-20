# Specification for `AudioBuffer.mix()`

Your goal: modify `AudioBuffer` so that you can mix in slices of different signals. You will use this to combine many notes into one song.

## Outline of new `AudioBuffer` method

Copy and paste this (including the Javadoc!) into `AudioBuffer` to get started:

```java
/**
 * Renders a slice of the given signal into this audio buffer, adding the new signal to the
 * sample values already present in the buffer.
 *
 * @param signal    A sound source.
 * @param offset    The number of samples into the audio buffer to start adding the new signal.
 *                  This offset is time 0 for the signal.
 * @param duration  The number of samples of the signal to add to the buffer. The offset +
 *                  duration must not exceed the length of the buffer.
 */
public void mix(Signal signal, int offset, int duration)
```

## Tests to add

Add the following two tests to `AudioBufferTest`:

```java
@Test
void mixStartsSignalAtTime0() {
    audio.mix(n -> n + 10, 4, 3);
    assertArrayEquals(
        new float[] { 0, 0, 0, 0, 10, 11, 12, 0 },
        audio.getSamples());
}

@Test
void mixAddsToExistingSignal() {
    audio.getSamples()[4] = 100;
    audio.mix(n -> 1, 2, 4);
    assertArrayEquals(
        new float[] { 0, 0, 1, 1, 101, 1, 0, 0 },
        audio.getSamples());
}
```

These tests should pass if you implemented `mix()` correctly. Take a moment to study each one. Do you understand (1) what the code does, and (2) what particular condition it is testing?

Are there any other tests you’d like to add?
