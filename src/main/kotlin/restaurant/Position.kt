package restaurant

class Position(
    var name: String = "",
    var amount: Int = 0,
    var duration: Int = 0,
    var price: Int = 0
) {
    fun toString(orderAmount: Int): String {
        return "$name: ${orderAmount}шт., готовится ${duration * orderAmount} сек., ${price * orderAmount}р."
    }

    override fun toString(): String {
        return "$name: ${amount}шт., готовится $duration сек., ${price}р."
    }
}