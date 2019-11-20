# Specification for `AudioBuffer.mix()`

```java
/**
 * Renders part of the given signal into this audio buffer, adding to sample values already
 * present in the buffer.
 *
 * @param signal    A sound source.
 * @param offset    The number of samples into the audio buffer to start mixing in the signal.
 *                  Signal time 0 starts at this offset.
 * @param duration  The number of samples of the signal to add to the buffer. The offset +
 *                  duration must not exceed the length of the buffer.
 */
public void mix(Signal signal, int offset, int duration)
```

```java
@Test
void mix() {
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
