package net.milosvasic.tautology.expression

interface ExpressionValue<out T> {

    fun getValue(): T

}