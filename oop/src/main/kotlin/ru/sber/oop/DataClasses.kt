package ru.sber.oop

data class User(val name: String, val age: Long) {
    lateinit var city: String

    override fun equals(other: Any?): Boolean =
        !(other !is User || this::city.isInitialized && !other::city.isInitialized ||
        !this::city.isInitialized && other::city.isInitialized ||
        this::city.isInitialized && other::city.isInitialized && city != other.city ||
        name != other.name || age != other.age)
}

fun main() {
    val user1 = User("Alex", 13)
    //TODO: user2 = ...
    val user2 = user1.copy(name = "Mike")
    //TODO: user3 = ...
    user1.city = "Omsk"
    val user3 = user1.copy()
    user3.city = "Tomsk"
    println(user1 == user3)
}