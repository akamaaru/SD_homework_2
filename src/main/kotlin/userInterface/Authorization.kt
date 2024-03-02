package userInterface

import users.Account
import users.UserDataBase

fun parseAuthorizationLogin(login: String): Boolean? {
    val incorrectLogin = "Пользователь с таким логином не найден!"

    if (login == "exit") {
        return null
    }

    if (!UserDataBase.database.any { it.login == login }) {
        println(incorrectLogin)
        return false
    }

    return true
}

fun parseAuthorizationPassword(login: String, password: String): Boolean? {
    val incorrectPassword = "Пароль неверный!"

    if (password == "exit") {
        return null
    }

    val account = UserDataBase.database.find { it.login == login }!!

    if (account.password != password) {
        println(incorrectPassword)
        return false
    }

    return true
}

fun authorize() {
    var login = ""
    var password: String
    var account: Account

    val ui = InputUI(
        "Авторизация",
        mutableListOf(
            InputCommand("логин") {
                login = readln()
                return@InputCommand parseAuthorizationLogin(login)
            },
            InputCommand("пароль") {
                password = readln()

                val isCorrect = parseAuthorizationPassword(login, password)
                if (isCorrect == true) {
                    account = UserDataBase.database.find { it.login == login }!!
                    account.launch()
                }

                return@InputCommand isCorrect
            }
        )
    )

    ui.show()
}
