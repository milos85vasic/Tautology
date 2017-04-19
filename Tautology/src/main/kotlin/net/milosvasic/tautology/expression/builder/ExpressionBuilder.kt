package net.milosvasic.tautology.expression.builder

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator

class ExpressionBuilder {

    private val expressions = mutableListOf<Expression>()

    fun append(value: Boolean): ExpressionBuilder {
        return append(object : ExpressionValue<Boolean> {
            override fun getValue(): Boolean {
                return value
            }
        })
    }

    fun append(value: ExpressionValue<Boolean>): ExpressionBuilder {
        if (!expressions.isEmpty() && expressions.last().right == null) {
            if (expressions.last() is BooleanExpression) {
                val lastItem = expressions.last() as BooleanExpression
                val updated = BooleanExpression(lastItem.value, Operator.AND())
                expressions[expressions.lastIndex] = updated
            }
        }
        expressions.add(BooleanExpression(value))
        return this
    }

    fun build(): List<Expression> {
        return expressions
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        expressions.forEachIndexed {
            index, expression ->
            if (expression.left != null) {
                stringBuilder.append(expression.left.value)
            }
            if (expression is ExpressionValue<*>) {
                if (index > 0) {
                    stringBuilder.append(" ")
                }
                stringBuilder.append("${expression.getValue()}")
            }
            if (expression.right != null) {
                stringBuilder
                        .append(" ")
                        .append(expression.right.value)
            }
        }
        return stringBuilder.toString()
    }

}

