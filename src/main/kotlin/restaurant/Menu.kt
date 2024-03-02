package restaurant

import userInterface.*

class Menu(
    var name: String = "основное",
    var positions: MutableList<Position> = mutableListOf()
) {
    private fun parseNewPositionName(name: String): Boolean? {
        val nameExists = "Позиция с таким названием уже существует!"
        if (name == "exit") {
            return null
        }

        if (positions.any { it.name == name }) {
            println(nameExists)
            return false
        }

        return true
    }

    fun parseExistingPositionName(): Pair<Boolean?, Position?> {
        val nameDoesntExist = "Позиции с таким названием не существует!"

        val name = readln()
        if (name == "exit") {
            return Pair(null, null)
        }

        val position: Position? = positions.find { it.name == name }

        if (position == null) {
            println(nameDoesntExist)
            return Pair(false, null)
        }

        return Pair(true, position)
    }

    fun addPosition() {
        var name = ""
        var amount = 0
        var duration = 0
        var price: Int

        val ui = InputUI(
            "Добавление позиции",
            mutableListOf(
                InputCommand("название") {
                    name = readln()
                    return@InputCommand parseNewPositionName(name)
                },
                InputCommand("количество") {
                    val pair = parsePositiveInt()

                    if (pair.first == true) {
                        amount = pair.second
                    }

                    return@InputCommand pair.first
                },
                InputCommand("время готовки (в секундах)") {
                    val pair = parsePositiveInt()

                    if (pair.first == true) {
                        duration = pair.second
                    }

                    return@InputCommand pair.first
                },
                InputCommand("цену") {
                    val pair = parsePositiveInt()

                    if (pair.first == true) {
                        price = pair.second
                        positions.add(Position(name, amount, duration, price))
                        println("Позиция успешно добавлена!")
                    }

                    return@InputCommand pair.first
                },
            )
        )

        ui.show()
    }

    fun editPosition() {
        val ui = InputUI(
            "Редактирование позиции",
            mutableListOf(
                InputCommand("название") {
                    val pair = parseExistingPositionName()

                    if (pair.first == true) {
                        editPositionChoice(pair.second!!)
                    }

                    return@InputCommand pair.first
                }
            ),
        )

        ui.show()
    }

    private fun editPositionName(position: Position) {
        val ui = InputUI(
            "Смена названия",
            mutableListOf(
                InputCommand("новое название") {
                    val name = readln()

                    val isCorrect = parseNewPositionName(name)
                    if (isCorrect == true) {
                        position.name = name
                        println("Название успешно изменено!")
                    }

                    return@InputCommand isCorrect
                },
            )
        )

        ui.show()
    }

    private fun editPositionAmount(position: Position) {
        val ui = InputUI(
            "Смена количества",
            mutableListOf(
                InputCommand("новое количество") {
                    val pair = parsePositiveInt()

                    if (pair.first == true) {
                        position.amount = pair.second
                        println("Количество успешно изменено!")
                    }

                    return@InputCommand pair.first
                },
            )
        )

        ui.show()
    }

    private fun editPositionDuration(position: Position) {
        val ui = InputUI(
            "Смена времени готовки",
            mutableListOf(
                InputCommand("новое время готовки (в секундах)") {
                    val pair = parsePositiveInt()

                    if (pair.first == true) {
                        position.duration = pair.second
                        println("Время готовки успешно изменено!")
                    }

                    return@InputCommand pair.first
                },
            )
        )

        ui.show()
    }

    private fun editPositionPrice(position: Position) {
        val ui = InputUI(
            "Смена цены",
            mutableListOf(
                InputCommand("новую цену") {
                    val pair = parsePositiveInt()

                    if (pair.first == true) {
                        position.price = pair.second
                        println("Цена успешно изменена!")
                    }

                    return@InputCommand pair.first
                },
            )
        )

        ui.show()
    }

    private fun editPositionChoice(position: Position) {
        val ui = ChoiceUI(
            "Редактирование позиции",
            mutableListOf(
                ChoiceCommand("Название") {
                    editPositionName(position)
                },
                ChoiceCommand("Количество") {
                    editPositionAmount(position)
                },
                ChoiceCommand("Время готовки") {
                    editPositionDuration(position)
                },
                ChoiceCommand("Цену") {
                    editPositionPrice(position)
                },
            ),
            "Вернуться",
        )

        ui.show()
    }

    fun removePosition() {
        val ui = InputUI(
            "Удаление позиции",
            mutableListOf(
                InputCommand("название") {
                    val pair = parseExistingPositionName()
                    positions.remove(pair.second)

                    if (pair.first == true) {
                        println("Позиция успешно удалена!")
                    }

                    return@InputCommand pair.first
                }
            ),
        )

        ui.show()
    }

    override fun toString(): String {
        var result = endLine + "Меню $name: " + endLine
        for (position in positions) {
            result += position.toString() + endLine
        }

        return result
    }
}