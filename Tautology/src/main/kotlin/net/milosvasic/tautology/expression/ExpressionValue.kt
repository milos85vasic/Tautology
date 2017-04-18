package net.milosvasic.tautology.expression

interface ExpressionValue<out T> {

    fun get(): T

}