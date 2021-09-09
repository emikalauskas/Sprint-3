package ru.sber.oop

import kotlin.random.Random

interface Fightable {
    val powerType: String
    var healthPoints: Int
    val damageRoll: Int
        get() = Random.nextInt(0, 10000)

    fun attack(opponent: Fightable): Int
}

//TODO: create class Player, Monster, Goblin here...
class Player (
    val name: String,
    val isBlessed: Boolean,
    override val powerType: String,
    override var healthPoints: Int
) : Fightable {
    override fun attack(opponent: Fightable): Int {
        val damageDealt = if (isBlessed) damageRoll * 2 else damageRoll
        opponent.healthPoints -= damageDealt
        return damageDealt
    }
}

abstract class Monster : Fightable {
    abstract val name: String
    abstract val description: String

    override fun attack(opponent: Fightable): Int {
        val damageDeath = damageRoll
        opponent.healthPoints -= damageDeath
        return damageDeath
    }
}

fun Monster.getSalutation() {
    println("Time is money")
}

class Goblin (
    override val name: String,
    override val description: String,
    override val powerType: String,
    override var healthPoints: Int
) : Monster() {
    override val damageRoll: Int
        get() = super.damageRoll / 2
}


