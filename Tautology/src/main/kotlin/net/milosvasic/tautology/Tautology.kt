package net.milosvasic.tautology

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression

class Tautology {

    var expressions = mutableListOf<Expression>()

    fun evaluate(): Boolean {
        expressions.forEach {
            expression ->
            when (expression) {
                is BooleanExpression -> {

                }
                else -> throw IllegalArgumentException("Unsupported expression type: ${expression::class.simpleName}")
            }
        }
        return true
    }

}