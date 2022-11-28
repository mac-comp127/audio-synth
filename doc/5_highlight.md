# Part 5 (bonus): Highlight notes

Your goal: highlight the currently playing notes.

Any rectangles that represent currently playing notes are filled with **white**. When each note is done
playing, its rectangle goes **back to its original color**.

There are several ways to implement this.

<details>
<summary>(Click for hints about possible approaches)</summary>

You will need to figure out a way to ask, “Is this note currently playing?,” then ask that question
for every note every time there is a time update.

Now, how to update the colors of the rectangles on the screen?

You could delete and recreate all of the rectangles every time the time is updated. It won’t
flicker: remember that Kilt Graphics waits for each callback closure to finish its work before
redrawing the canvas (unless you call `canvas.draw()` explicitly). However, it's possible that this
might be slow. Would it be? You could try it!

If you want to make the animation as efficient (and thus as smooth) as possible, you’ll need a way to
keep track of all of the rectangles you created. There are two different approaches that could work:

- You can keep track of all of the rectangles in some data structure, then loop over the
    **rectangles** and check their x coordinates to determine whether each note is a currently
    playing note.
- You can keep track of the association from notes to rectangles in some data structure, then loop
    each **note**, using its start time and duration to figure out whether it is currently playing,
    then update the corresponding rectangle.

Whichever approach you use, you’ll need to store rectangles in some sort of data structure when you
first create them, then use that data structure in the `setTime` method.

Contact your instructor if you want more hints.
</details>
