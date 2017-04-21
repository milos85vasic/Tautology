package net.milosvasic.tautology

import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import net.milosvasic.tautology.operator.Operator

fun expression(vararg expressions: Any): List<Expression> {
    val builder = ExpressionBuilder()
    expressions.forEach {
        expression ->
        when (expression) {
            is Boolean -> {
                builder.append(expression)
            }
            is ExpressionValue -> {
                builder.append(expression)
            }
            is Operator -> {
                builder.append(expression)
            }
            is ExpressionBuilder -> {
                builder.append(expression)
            }
            is List<*> -> {
                builder.append(expression)
            }
            else -> {
                throw IllegalArgumentException("Unsupported type: ${expression::class.simpleName}")
            }
        }
    }
    return builder.build()
}

