package restaurant

import userInterface.*
import java.util.*

object Restaurant {
    var currentMenu: Menu = Menu()
    private var menus = mutableListOf(currentMenu)
    var orders = mutableListOf<Order>()
    var revenue = 0

    private fun parseNewMenuName(name: String): Boolean? {
        val nameExists = "Меню с таким названием уже существует!"
        if (name == "exit") {
            return null
        }

        if (menus.any { it.name == name }) {
            println(nameExists)
            return false
        }

        return true
    }

    private fun parseExistingMenuName(): Pair<Boolean?, Menu?> {
        val nameDoesntExists = "Меню с таким названием не существует!"

        val name = readln()
        if (name == "exit") {
            return Pair(null, null)
        }

        val menu: Menu? = menus.find { it.name == name }

        if (menu == null) {
            println(nameDoesntExists)
            return Pair(false, null)
        }

        return Pair(true, menu)
    }

    fun getMenu() {
        print(currentMenu)
    }

    fun switchMenu() {
        val ui = InputUI("Смена меню",
            mutableListOf(
                InputCommand("название") {
                    val pair = parseExistingMenuName()

                    if (pair.first != true) {
                        return@InputCommand pair.first
                    }

                    currentMenu = pair.second!!
                    println("Меню успешно сменено!")
                    return@InputCommand pair.first
                }
            )
        )

        ui.show()
    }

    fun addMenu() {
        val ui = InputUI(
            "Смена названия",
            mutableListOf(
                InputCommand("новое название") {
                    val name = readln()

                    val isCorrect = parseNewMenuName(name)
                    if (isCorrect != true) {
                        return@InputCommand isCorrect
                    }

                    val newMenu = Menu(name)
                    menus.add(newMenu)
                    currentMenu = newMenu

                    println("Меню успешно добавлено!")
                    return@InputCommand isCorrect
                },
            )
        )

        ui.show()
    }

    private fun editMenuName() {
        var name: String

        val ui = InputUI(
            "Смена названия",
            mutableListOf(
                InputCommand("новое название") {
                    name = readln()

                    val isCorrect = parseNewMenuName(name)
                    if (isCorrect == true) {
                        currentMenu.name = name
                        println("Название успешно изменено!")
                    }

                    return@InputCommand isCorrect
                },
            )
        )

        ui.show()
    }

    fun editMenu() {
        val ui = ChoiceUI(
            "Редактирование меню",
            mutableListOf(
                ChoiceCommand("Изменить название") {
                    editMenuName()
                },
                ChoiceCommand("Добавить позицию") {
                    currentMenu.addPosition()
                },
                ChoiceCommand("Редактировать позицию") {
                    currentMenu.editPosition()
                },
                ChoiceCommand("Удалить позицию") {
                    currentMenu.removePosition()
                }
            ),
            "Вернуться",
        )

        ui.show()
    }

    fun removeMenu() {
        if (menus.size == 1) {
            println("Нельзя удалить единственное меню")
            return
        }

        val ui = InputUI("Удаление меню",
            mutableListOf(
                InputCommand("название") {
                    val pair = parseExistingMenuName()
                    menus.remove(pair.second)

                    if (pair.first == true) {
                        println("Меню успешно удалено!")
                    }

                    return@InputCommand pair.first
                }
            )
        )

        ui.show()
    }

    fun ordersToString(): String {
        var result = endLine + "Заказы: " + endLine
        for (order in orders) {
            result += order.toString() + endLine
        }

        return result
    }
}