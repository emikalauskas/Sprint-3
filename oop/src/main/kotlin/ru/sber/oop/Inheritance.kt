package ru.sber.oop

open class Room(val name: String, val size: Int) {
    protected open val dangerLevel = 5
    val monster: Monster = Goblin (
        name = "Gazlowe",
        description = "The Boss of Ratchet",
        powerType = "Melee",
        healthPoints = 2375
    )
    val player = Player (
        name = "Trall",
        isBlessed = true,
        powerType = "Melee",
        healthPoints = 10000
    )

    constructor(name: String) : this(name, 100)

    fun description() = "Room: $name"

    open fun load() {
        monster.getSalutation()
        println("Nothing much to see here...")
    }
}

//TODO: create class TownSquare here...
class TownSquare : Room(name = "Town Square", size = 1000) {

    override val dangerLevel = super.dangerLevel - 3

    final override fun load() {
        println("There is something to see here ...")
    }
}

fun main() {
    val room = Room("Dungeon", 20000)
    room.load()
    val townSquare = TownSquare()
    townSquare.load()
    val damageDealt = room.player.attack(room.monster)
    println("Damage dealt:")
    println("-$damageDealt")
    if (room.monster.healthPoints <= 0)
        println("${room.monster.name} died by ${room.player.name}")
}