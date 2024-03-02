package users

import restaurant.Restaurant
import userInterface.ChoiceCommand
import userInterface.ChoiceUI
import java.io.File
import java.nio.file.Paths

class Administrator(
    login: String = "",
    password: String = ""
) : Account(login, password) {
    private fun menuChoice() {
        val ui = ChoiceUI(
            "Меню",
            mutableListOf(
                ChoiceCommand("Посмотреть") {
                    Restaurant.getMenu()
                },
                ChoiceCommand("Выбрать другое") {
                    Restaurant.switchMenu()
                },
                ChoiceCommand("Добавить новое") {
                    Restaurant.addMenu()
                },
                ChoiceCommand("Редактировать текущее") {
                    Restaurant.editMenu()
                },
                ChoiceCommand("Удалить текущее") {
                    Restaurant.removeMenu()
                },
            ),
            "Вернуться на главный экран",
        )

        ui.show()
    }

    private fun writeToFile(fileName: String, message: String) {
        val path = Paths.get("").toAbsolutePath().toString() +
                "\\src\\main\\kotlin\\log\\$fileName"
        val file = File(path)
        file.writeText(message)
    }

    private fun saveChoice() {
        val ui = ChoiceUI(
            "Сохранение данных",
            mutableListOf(
                ChoiceCommand("Сохранить текущее меню") {
                    writeToFile("menu.txt", Restaurant.currentMenu.toString())
                },
                ChoiceCommand("Сохранить сумму выручки") {
                    writeToFile("revenue.txt", "Сумма выручки ресторана: ${Restaurant.revenue}р.")
                },
                ChoiceCommand("Сохранить пользователей в системе") {
                    writeToFile("users.txt", UserDataBase.toString())
                },
                ChoiceCommand("Сохранить заказы") {
                    writeToFile("orders.txt", Restaurant.ordersToString())
                },
            ),
            "Вернуться на главный экран",
        )

        ui.show()
    }

    override fun launch() {
        val ui = ChoiceUI(
            "Главная страница",
            mutableListOf(
                ChoiceCommand("Редактор меню") {
                    menuChoice()
                },
                ChoiceCommand("Сохранить данные") {
                    saveChoice()
                }
            ),
            "Выйти из системы",
        )

        ui.show()
    }
}