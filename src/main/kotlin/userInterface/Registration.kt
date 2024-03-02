package userInterface

import users.Account
import users.Administrator
import users.Customer
import users.UserDataBase

fun parseRegistrationLogin(login: String): Boolean? {
    val loginExists = "Пользователь с таким логином уже существует!"

    if (login == "exit") {
        return null
    }

    if (UserDataBase.database.any { it.login == login }) {
        println(loginExists)
        return false
    }

    return true
}

fun parseRegistrationPassword(password: String): Boolean? {
    if (password == "exit") {
        return null
    }

    return true
}

fun register() {
    var login = ""
    var password = ""

    val ui = InputUI(
        "Регистрация",
        mutableListOf(
            InputCommand("логин") {
                login = readln()
                return@InputCommand parseRegistrationLogin(login)
            },
            InputCommand("пароль") {
                password = readln()

                val isCorrect = parseRegistrationPassword(password)
                if (isCorrect != true) {
                    return@InputCommand isCorrect
                }

                val account: Account = if (login.endsWith("admin")) {
                    Administrator(login, password)
                } else {
                    Customer(login, password)
                }

                UserDataBase.database.add(account)

                account.launch()

                return@InputCommand isCorrect
            }
        ),
    )

    ui.show()
}
