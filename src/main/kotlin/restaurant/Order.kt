package restaurant

import userInterface.InputCommand
import userInterface.InputUI
import userInterface.endLine
import userInterface.parsePositiveInt

enum class State(val value: String) {
    Inactive("не активен"),
    Forming("оформляется"),
    Preparing("готовится"),
    Done("готов"),
    Paid("оплачен"),
    Canceled("отменён"),
}

class Order {
    var positions = mutableMapOf<String, Int>()
    var price: Int = 0
    var state = State.Inactive

    fun addPosition() {
        var position: Position? = null
        var amount: Int

        val ui = InputUI("Добавление позиции",
            mutableListOf(
                InputCommand("название позиции") {
                    val pair = Restaurant.currentMenu.parseExistingPositionName()
                    position = pair.second
                    return@InputCommand pair.first
                },
                InputCommand("количество") {
                    val pair = parsePositiveInt()

                    if (pair.first != true) {
                        return@InputCommand pair.first
                    }

                    amount = pair.second
                    if (position!!.amount < amount) {
                        println("Количество превышает существующее!")
                        return@InputCommand false
                    }

                    price += position!!.price * amount

                    val name = position!!.name
                    positions[name] = (positions[name] ?: 0) + amount
                    Restaurant.currentMenu.positions.find { it.name == name }!!.amount -= amount

                    return@InputCommand pair.first
                }
            )
        )

        ui.show()
    }

    fun removePosition() {
        val ui = InputUI(
            "Удаление позиции",
            mutableListOf(
                InputCommand("название позиции") {
                    val pair = Restaurant.currentMenu.parseExistingPositionName()

                    if (pair.first != true) {
                        return@InputCommand pair.first
                    }

                    val position = pair.second!!
                    if (!positions.containsKey(position.name)) {
                        println("Такой позиции не найдено!")
                        return@InputCommand false
                    }

                    val name = position.name
                    price -= position.price * positions[name]!!
                    Restaurant.currentMenu.positions.find { it.name == name }!!.amount += positions[name] ?: 0
                    positions.remove(name)

                    return@InputCommand pair.first
                },
            )
        )

        ui.show()
    }

    fun execute() {
        state = State.Preparing
        for (entry in positions.entries) {
            if (state == State.Canceled) {
                return
            }

            val duration = Restaurant.currentMenu.positions.find { it.name == entry.key }!!.duration
            Thread.sleep((duration * entry.value * 1_000).toLong())
        }

        state = State.Done
    }

    override fun toString(): String {
        var result = endLine + "Заказ ${state.value}:" + endLine
        for (position in positions) {
            val positionMenu = Restaurant.currentMenu.positions.find { it.name == position.key }
            result += positionMenu?.toString(position.value) + endLine
        }

        result += "Итого: ${price}р."
        return result
    }
}