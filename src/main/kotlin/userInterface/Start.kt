package userInterface

fun start() {
    val ui = ChoiceUI(
        "Добро пожаловать в пиццерию \"Нюрнберг\"!" + endLine +
                "Вход в систему",
        mutableListOf(
            ChoiceCommand("Вход") {
                authorize()
            },
            ChoiceCommand("Регистрация") {
                register()
            },
        ),
        "Завершить приложение",
    )

    try {
        ui.show()
    } catch (e: Exception) {
        println("Произошла ошибка во время выполнения программы!")
        println(e.message)
        start()
    }
}
