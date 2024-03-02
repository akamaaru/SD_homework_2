package users

import restaurant.Order
import restaurant.Restaurant
import restaurant.State
import userInterface.ChoiceCommand
import userInterface.ChoiceUI
import userInterface.endLine
import kotlin.concurrent.thread

class Customer(
    login: String = "",
    password: String = ""
) : Account(login, password) {
    private var currentOrder: Order = Order()
    private var orders = mutableListOf(currentOrder)

    private fun addOrder() {
        if (currentOrder.state == State.Forming ||
            currentOrder.state == State.Preparing
        ) {
            println("Вы не можете иметь сразу два активных заказа!")
            return
        }

        currentOrder = Order()
        currentOrder.state = State.Forming
        orders.add(currentOrder)
        editOrder()
    }

    private fun editOrder() {
        if (currentOrder.state != State.Forming &&
            currentOrder.state != State.Preparing
        ) {
            println(
                "Ваш заказ ${currentOrder.state.value}!" + endLine +
                        "Для редактирования ваш заказ должен формироваться или готовиться"
            )
            return
        }

        val ui = ChoiceUI(
            "Заказ",
            mutableListOf(
                ChoiceCommand("Добавить позицию") {
                    currentOrder.addPosition()
                },
                ChoiceCommand("Удалить позицию") {
                    currentOrder.removePosition()
                },
                ChoiceCommand("Посмотреть заказ") {
                    println(currentOrder)
                },
                ChoiceCommand("Подтвердить") {
                    if (currentOrder.positions.isEmpty()) {
                        println("Заказ пустой!")
                        return@ChoiceCommand
                    }

                    Restaurant.orders.add(currentOrder)

                    val cookingThread = thread {
                        currentOrder.execute()
                    }

                    println("Ваш заказ готовится!")
                },
                ChoiceCommand("Оплатить заказ") {
                    pay()
                },
                ChoiceCommand("Отменить заказ") {
                    cancel()
                },
            ),
            "Вернуться назад"
        )

        ui.show()
    }

    private fun pay() {
        if (currentOrder.state != State.Done) {
            println(
                "Ваш заказ ${currentOrder.state.value}. " + endLine +
                        "Вы сможете его оплатить, когда он будет готов"
            )
            return
        }

        Restaurant.revenue += currentOrder.price
        currentOrder.state = State.Paid
        println("Ваш заказ успешно оплачен!")
    }

    private fun cancel() {
        if (currentOrder.state != State.Preparing) {
            println(
                "Ваш заказ ${currentOrder.state.value}. " + endLine +
                        "Вы можете его отменить, только когда он готовится"
            )
            return
        }

        currentOrder.state = State.Canceled
        println("Ваш заказ отменён!")
    }

    override fun launch() {
        val ui = ChoiceUI(
            "Главная страница",
            mutableListOf(
                ChoiceCommand("Посмотреть меню") {
                    Restaurant.getMenu()
                },
                ChoiceCommand("Добавить заказ") {
                    addOrder()
                },
                ChoiceCommand("Редактировать заказ") {
                    editOrder()
                },
                ChoiceCommand("Отменить заказ") {
                    cancel()
                },
            ),
            "Выйти из системы",
        )

        ui.show()
    }
}