# kt-vertex

## How to use

make sure you have [kotlin](https://kotlinlang.org/docs/command-line.html) and java installed

run `kotlinc kt-vertex.kt -include-runtime -d kt-vertex.jar && java -jar kt-vertex.jar`, it should start the program as well as generate a jar in the same folder, just ignore the jar

## Format

Vertex2d: [Double_x, Double_y]

Vertex3d: [Double_x, Double_y, Double_z]

The doubles can be inputted as ints in the beginning.

The program will ask you to create a vertex at the start.

Format is mentioned but here is an example:

`vertex3d 0.5 9 1`

or

`vertex2d 9 2.6`

## Commands

(each command will say if it's compatible with vertex2d, vertex3d, or both)

### Scaling

`scale`(both): scales x and y (z if vertex3d) by a factor amount (double)

if original is [10, 10, 10]
```
> scale 0.5
Scaling with factor: 0.5 to [5, 5, 5]
```

`scalex`(both): scales only x by factor amount

`scaley`(both): scales only y by factor amount

`scalez`(vertex3d only): scales z by factor amount

### Translating

`translate`(both): translates vertex by specific amount

if original is [10, 10, 10]
```
> translate 1 2 3
Translated 3d vertex to [11, 12, 13]
```

`translateall`(both): translate x and y (and z if vertex3d) by amount

if original is [10, 10, 10]
```
> translate 2
Translated 3d vertex to [12, 12, 12]
```

`translatex`(both): translates only the x amount

`translatey`(both): translates only the y amount

`translatez`(vertex3d only): translates only the z amount

### Other

`get`: gets the current formatted vertex
aliases: `vertex`, `formattedvertex`, `status`, `vertex2d`, `vertex3d`

`reset`: resets the vertex back to [0, 0] or [0, 0, 0]
aliases: `clear` (don't mistake this for clearing the terminal)

`exit`: exit (obviously)