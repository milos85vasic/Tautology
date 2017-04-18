package net.milosvasic.tautology.expression

import net.milosvasic.tautology.operator.Operator

class BooleanExpression(val value: ExpressionValue<Boolean>, right: Operator.RightOperator? = null, left: Operator.LeftOperator? = null) : Expression(right, left), ExpressionValue<Boolean> {

    override fun getValue(): Boolean {
        if (left != null) {
            when (left) {
                is Operator.NOT -> {
                    return !value.getValue()
                }
                else -> throw IllegalArgumentException("Unsupported operator: ${left::class.simpleName}")
            }
        }
        return value.getValue()
    }

}

