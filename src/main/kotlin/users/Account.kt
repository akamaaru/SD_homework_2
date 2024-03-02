package users

abstract class Account(
    var login: String = "",
    var password: String = ""
) {
    abstract fun launch()
}
