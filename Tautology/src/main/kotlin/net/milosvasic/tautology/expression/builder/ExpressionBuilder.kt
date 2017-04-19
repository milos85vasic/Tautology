package net.milosvasic.tautology.expression.builder

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator

class ExpressionBuilder {

    private val expressions = mutableListOf<Expression>()
    private var lastLeftOperator: Operator.LeftOperator? = null
    private var lastRightOperator: Operator.RightOperator? = null
    private var lastAppendedValue: ExpressionValue<Boolean>? = null

    fun append(value: ExpressionValue<Boolean>): ExpressionBuilder {
        if (lastAppendedValue != null) {
            var rightOperator = lastRightOperator
            if (rightOperator == null) {
                rightOperator = Operator.AND()
            }
            val expression = BooleanExpression(value, rightOperator, lastLeftOperator)
            expressions.add(expression)
        }
        lastAppendedValue = value
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

