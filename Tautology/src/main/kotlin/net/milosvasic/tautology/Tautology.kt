package net.milosvasic.tautology

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.operator.Operator

class Tautology {

    var expressions = mutableListOf<Expression>()

    fun evaluate(): Boolean {
        expressions.forEach {
            expression ->
            when (expression) {
                is BooleanExpression -> {
                    if (expression.operator == null) {
                        return expression.value.get()
                    } else {
                        when (expression.operator) {
                            is Operator.NOT -> {
                                return !expression.value.get()
                            }
                            else -> throw IllegalArgumentException("Unsupported operator: ${expression.operator::class.simpleName}")
                        }
                    }
                }
                else -> throw IllegalArgumentException("Unsupported expression type: ${expression::class.simpleName}")
            }
        }
        return true
    }

}