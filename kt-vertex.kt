const val CODE_VERSION = "1.0.0"

class Vertex2d(xPos: Double, yPos: Double) {
    var x: Double
        get() = field
    var y: Double
        get() = field

    fun getFormattedVertex(): String {
        return "[" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + "]"
    }

    fun translate(xs: Double, ys: Double) {
        x += xs
        y += ys
    }

    fun translateX(xs: Double) {
        x += xs
    }

    fun translateY(ys: Double) {
        y += ys
    }

    fun scale(factor: Double) {
        x *= factor
        y *= factor
    }

    fun parse(vertex: String): Vertex2d? { // format: "[x, y]" space is optional
        var (cleanX, cleanY) = extractValues(vertex) ?: return null
        return Vertex2d(cleanX, cleanY)
    }

    private fun extractValues(input: String): Pair<Double, Double>? {
        val cleanedInput = input.replace("[\\[\\]\\s]".toRegex(), "")
        val values = cleanedInput.split(",")

        if (values.size == 2) {
            try {
                val x = values[0].toDouble()
                val y = values[1].toDouble()
                return x to y
            } catch (e: NumberFormatException) {
                println("Invalid format: $input")
            }
        } else {
            println("Invalid number of values: $input")
        }

        return null
    }

    fun reset() {
        x = 0.0
        y = 0.0
    }

    init {
        x = xPos
        y = yPos
    }
}

class Vertex3d(xPos: Double, yPos: Double, zPos: Double) {
    var x: Double
        get() = field
    var y: Double
        get() = field
    var z: Double
        get() = field

    fun getFormattedVertex(): String {
        return "[" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + ", " + String.format("%.2f", z) + "]"
    }

    fun translate(xs: Double, ys: Double, zs: Double) {
        x += xs
        y += ys
        z += zs
    }

    fun translateX(xs: Double) {
        x += xs
    }

    fun translateY(ys: Double) {
        y += ys
    }

    fun translateZ(zs: Double) {
        z += zs
    }

    fun scale(factor: Double) {
        x *= factor
        y *= factor
        z *= factor
    }

    fun parse(vertex: String): Vertex3d? { // format: "[x, y, z]" space is optional
        var (cleanX, cleanY, cleanZ) = extractValues(vertex) ?: return null
        return Vertex3d(cleanX, cleanY, cleanZ)
    }

    private fun extractValues(input: String): Triple<Double, Double, Double>? {
        val cleanedInput = input.replace("[\\[\\]\\s]".toRegex(), "")
        val values = cleanedInput.split(",")

        if (values.size == 3) {
            try {
                val x = values[0].toDouble()
                val y = values[1].toDouble()
                val z = values[2].toDouble()
                return Triple(x, y, z)
            } catch (e: NumberFormatException) {
                println("Invalid format: $input")
            }
        } else {
            println("Invalid number of values: $input")
        }

        return null
    }

    fun reset() {
        x = 0.0
        y = 0.0
        z = 0.0
    }

    init {
        x = xPos
        y = yPos
        z = zPos
    }
}

var USER_VERTEX_2D: Vertex2d = Vertex2d(6.9, 6.9)
var USER_VERTEX_3D: Vertex3d = Vertex3d(6.9, 6.9, 6.9)
var RUNTIME_VERTEX_TYPE: Int = 0

fun main() {
    println("kt_vertex $CODE_VERSION")
    println("Enter a vertex")
    println("Format: vertex2d x:Double y:Double OR vertex3d x:Double y:Double z:Double")
    val userInput = readLine()?.trim() ?: ""
    val pattern2D = Regex("""vertex2d\s+(\d+(?:\.\d+)?)\s+(\d+(?:\.\d+)?)""")
    val pattern3D = Regex("""vertex3d\s+(\d+(?:\.\d+)?)\s+(\d+(?:\.\d+)?)\s+(\d+(?:\.\d+)?)""")
    when {
        pattern2D.matches(userInput) -> {
            val matchResult = pattern2D.find(userInput)
            val (x, y) = matchResult?.destructured ?: return
            USER_VERTEX_2D = Vertex2d(x.toDouble(), y.toDouble())
            RUNTIME_VERTEX_TYPE = 0
        }
        pattern3D.matches(userInput) -> {
            val matchResult = pattern3D.find(userInput)
            val (x, y, z) = matchResult?.destructured ?: return
            USER_VERTEX_3D = Vertex3d(x.toDouble(), y.toDouble(), z.toDouble())
            RUNTIME_VERTEX_TYPE = 1
        }
        else -> {
            println("Invalid input format.")
            System.exit(1)
        }
    }
    while (true) {
        print("> ")
        val input = readLine()?.trim() ?: continue
        val parts = input.split(" ")
        val command = parts[0]
        when (command) {
            "scale" -> {
                if (parts.size < 2) {
                    println("Missing factor amount.")
                    continue
                }
                val factor = parts[1].toDoubleOrNull()
                if (factor == null) {
                    println("Invalid factor amount.")
                    continue
                }
                
                if (RUNTIME_VERTEX_TYPE == 0) {
                    USER_VERTEX_2D.scale(factor)
                    var scaled = USER_VERTEX_2D.getFormattedVertex()
                    println("Scaling with factor: $factor to $scaled")
                } else {
                    USER_VERTEX_3D.scale(factor)
                    var scaled = USER_VERTEX_3D.getFormattedVertex()
                    println("Scaling with factor: $factor to $scaled")
                }
            }
            "reset" -> {
                if (RUNTIME_VERTEX_TYPE == 0) {
                    USER_VERTEX_2D.reset()
                    println("Reset vertex to [0.0, 0.0]")
                } else {
                    USER_VERTEX_3D.reset()
                    println("Reset vertex to [0.0, 0.0, 0.0]")
                }
            }
            "translate2d" -> {
                if (RUNTIME_VERTEX_TYPE != 0) {
                    println("Runtime vertex is not 2d")
                    continue
                }
                if (parts.size < 3) {
                    println("Missing translation amounts.")
                    continue
                }
                val xs = parts[1].toDoubleOrNull()
                val ys = parts[2].toDoubleOrNull()
                if (xs == null || ys == null) {
                    println("Invalid translation amounts.")
                    continue
                }
                USER_VERTEX_2D.translate(xs, ys)
                var translated = USER_VERTEX_2D.getFormattedVertex()
                println("Translated 2d vertex to $translated")
            }
            "translate3d" -> {
                if (RUNTIME_VERTEX_TYPE != 1) {
                    println("Runtime vertex is not 3d")
                    continue
                }
                if (parts.size < 4) {
                    println("Missing translation amounts.")
                    continue
                }
                val xs = parts[1].toDoubleOrNull()
                val ys = parts[2].toDoubleOrNull()
                val zs = parts[3].toDoubleOrNull()
                if (xs == null || ys == null || zs == null) {
                    println("Invalid translation amounts.")
                    continue
                }
                USER_VERTEX_3D.translate(xs, ys, zs)
                var translated = USER_VERTEX_3D.getFormattedVertex()
                println("Translated 3d vertex to $translated")
            }
            "translateX" -> {
                if (parts.size < 2) {
                    println("Missing translation amount.")
                    continue
                }
                val xs = parts[1].toDoubleOrNull()
                if (xs == null) {
                    println("Invalid translation amount.")
                    continue
                }
                if (RUNTIME_VERTEX_TYPE == 0) {
                    USER_VERTEX_2D.translateX(xs)
                    var translated = USER_VERTEX_2D.getFormattedVertex()
                    println("Translated vertex's x by $xs to $translated")
                } else {
                    USER_VERTEX_3D.translateX(xs)
                    var translated = USER_VERTEX_3D.getFormattedVertex()
                    println("Translated vertex's x by $xs to $translated")
                }
            }
            "translateY" -> {
                if (parts.size < 2) {
                    println("Missing translation amount.")
                    continue
                }
                val ys = parts[1].toDoubleOrNull()
                if (ys == null) {
                    println("Invalid translation amount.")
                    continue
                }
                if (RUNTIME_VERTEX_TYPE == 0) {
                    USER_VERTEX_2D.translateY(ys)
                    var translated = USER_VERTEX_2D.getFormattedVertex()
                    println("Translated vertex's y by $ys to $translated")
                } else {
                    USER_VERTEX_3D.translateY(ys)
                    var translated = USER_VERTEX_3D.getFormattedVertex()
                    println("Translated vertex's y by $ys to $translated")
                }
            }
            "translateZ" -> {
                if (RUNTIME_VERTEX_TYPE == 0) {
                    println("Runtime vertex is 2d, to translate z you need a 3d vertex")
                }
                if (parts.size < 2) {
                    println("Missing translation amount.")
                    continue
                }
                val zs = parts[1].toDoubleOrNull()
                if (zs == null) {
                    println("Invalid translation amount.")
                    continue
                }
                USER_VERTEX_3D.translateZ(zs)
                var translated = USER_VERTEX_3D.getFormattedVertex()
                println("Translated vertex's z by $zs to $translated")
            }
            "get" -> {
                if (RUNTIME_VERTEX_TYPE == 0) {
                    var formatted = USER_VERTEX_2D.getFormattedVertex()
                    println("$formatted")
                } else {
                    var formatted = USER_VERTEX_3D.getFormattedVertex()
                    println("$formatted")
                }
            }
            "exit" -> {
                System.exit(0)
            }
            else -> {
                println("Invalid command: $command")
            }
        }
    }
}