package userInterface

fun clearConsole() {
    print("\u001b[H\u001b[2J")
}

val endLine: String = System.lineSeparator()

abstract class Command(
    open val description: String,
)

class ChoiceCommand(
    description: String,
    val function: () -> Unit
) : Command(description)

class InputCommand(
    description: String,
    val function: () -> Boolean?
) : Command(description)

interface UI {
    fun show()
}

class ChoiceUI(
    private val title: String,
    private val commands: MutableList<ChoiceCommand>,
    private val returnDescription: String,
) : UI {
    override fun show() {
        clearConsole()
        var message = endLine + "===${title}===" + endLine
        for (i in 0..<commands.size) {
            message += "${i + 1}. ${commands[i].description}" + endLine
        }

        message += "${commands.size + 1}. $returnDescription" + endLine
        message += "Ваша команда: "
        val incorrectInput = "Некорректные данные, повторите ещё раз!"

        while (true) {
            print(message)
            val input = readln()

            val index = input.toIntOrNull()
            if (index == null ||
                index < 1 || index > commands.size + 1
            ) {
                print(incorrectInput)
                continue
            }

            if (index == commands.size + 1) {
                return
            }

            commands[index - 1].function()
        }
    }
}

class InputUI(
    private val title: String,
    private val commands: MutableList<InputCommand>
) : UI {
    override fun show() {
        clearConsole()
        val title = endLine + "===${title}===" + endLine +
                "Чтобы вернуться, введите \"exit\""

        println(title)

        for (command in commands) {
            do {
                print("Введите ${command.description}: ")
                val result = command.function() ?: return
            } while (!result)
        }

        return
    }
}
