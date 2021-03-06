package net.milosvasic.tautology

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.operator.Operator

class Tautology {

    fun evaluate(expressions: List<Expression>): Boolean {
        return evaluate(Expressions(expressions))
    }

    fun evaluate(expressions: Expressions): Boolean {
        if (expressions.items.last().right != null) {
            throw IllegalArgumentException("Expression not closed properly after: '${expressions.items.last().right?.value}'")
        }
        expressions.items.forEach {
            expression ->
            when (expression) {
                is BooleanExpression -> {
                    if (expression.right != null) {
                        when (expression.right) {
                            is Operator.AND -> {
                                if (!expression.getValue()) {
                                    return false
                                }
                            }
                            is Operator.OR -> {
                                if (expression.getValue()) {
                                    return true
                                }
                            }
                            else -> throw IllegalArgumentException("Unsupported operator: '${expression.right.value}'")
                        }
                    } else {
                        return expression.getValue()
                    }
                }
                else -> throw IllegalArgumentException("Unsupported expression type: '${expression::class.simpleName}'")
            }
        }
        return true
    }

}