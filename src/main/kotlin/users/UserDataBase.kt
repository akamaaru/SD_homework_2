package users

import userInterface.endLine

object UserDataBase {
    var database = mutableListOf<Account>()

    override fun toString(): String {
        var result = "Пользователи системы:$endLine"
        for (user in database) {
            result += if (user.login.endsWith("admin")) {
                "Администратор\t"
            } else {
                "Посетитель\t"
            }

            result += "login = ${user.login}; password = ${user.password}" + endLine
        }

        return result
    }
}