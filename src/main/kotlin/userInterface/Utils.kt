package userInterface

fun parsePositiveInt(): Pair<Boolean?, Int> {
    val incorrectInput = "Некорректный формат данных, повторите попытку!"
    val negativeInput = "Данные не могут быть отрицательными!"

    val input = readln()

    if (input == "exit") {
        return Pair(null, 0)
    }

    val intOrNull = input.toIntOrNull()
    if (intOrNull == null) {
        println(incorrectInput)
        return Pair(false, 0)
    }

    if (intOrNull < 0) {
        println(negativeInput)
        return Pair(false, 0)
    }

    return Pair(true, intOrNull)
}