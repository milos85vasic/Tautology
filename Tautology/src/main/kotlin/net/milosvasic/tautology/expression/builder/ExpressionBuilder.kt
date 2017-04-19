package net.milosvasic.tautology.expression.builder

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator

class ExpressionBuilder {

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
        }
        lastAppendedValue = value
        return this
    }

    fun build(): List<Expression> {
        val expressions = mutableListOf<Expression>()

        return expressions
    }

    override fun toString(): String {
        return "- - -" // TODO: Define this
    }

}

