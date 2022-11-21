# Part 1: Implement `AudioBuffer.mix()`

Your goal: modify `AudioBuffer` so that you can mix in slices of different signals. You will use this to combine many notes into one song.


## New `AudioBuffer` method

Copy and paste this (including the Javadoc!) into `AudioBuffer` to get started, then implement the method according to what the documentation describes:

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
 * @throws IllegalArgumentException If the offset or duration extend outside the bounds of this
 *                  buffer.
 */
public void mix(Signal signal, int offset, int duration)
```

Here is the code to use to generate an error if the offset or duration is out of bounds:

```java
throw new IllegalArgumentException("input signal is out of bounds");
```

Ensure that your bounds check has **failure atomicity**: if there is illegal argument error,
then the buffer should not be modified at all, not even the samples that would be in bounds.


## Tests to add

Add the following tests to `AudioBufferTest`:

```java
@Test
void mixCatchesOutOfBoundsErrors() {
    assertThrows(IllegalArgumentException.class, () -> {
        audio.mix(n -> 100, -1, 1);
    });
    assertThrows(IllegalArgumentException.class, () -> {
        audio.mix(n -> 100, 7, 2);
    });

    // Buffer should be unmodified if params were illegal
    assertArrayEquals(
        new float[] { 0, 0, 0, 0, 0, 0, 0, 0 },
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

@Test
void mixStartsSignalAtTime0() {
    audio.mix(n -> n + 10, 4, 3);
    assertArrayEquals(
        new float[] { 0, 0, 0, 0, 10, 11, 12, 0 },
        audio.getSamples());
}

@Test
void mixReachesBothEnds() {
    audio.mix(n -> 1, 0, 8);
    audio.mix(n -> 10, 0, 3);
    audio.mix(n -> 100, 5, 3);
    assertArrayEquals(
        new float[] { 11, 11, 11, 1, 1, 101, 101, 101 },
        audio.getSamples());
}

@Test
void mixMultiple() {
    audio.mix(n -> n * 0.5 + 1, 4, 3);
    assertArrayEquals(
        new float[] { 0, 0, 0, 0, 1.0f, 1.5f, 2.0f, 0 },
        audio.getSamples());

    audio.mix(n -> n - 6, 1, 5);
    assertArrayEquals(
        new float[] { 0, -6, -5, -4, -2, -0.5f, 2.0f, 0 },
        audio.getSamples());
}
```

These tests should pass if you implemented `mix()` correctly. Take a moment to study each one. Do you understand (1) what the code does, and (2) what particular condition it is testing?

Next: [Render audio](2_render_audio.md)
