package net.milosvasic.tautology.expression

import net.milosvasic.tautology.Tautology
import net.milosvasic.tautology.operator.Operator

class MultipleExpression(right: Operator.RightOperator? = null, left: Operator.LeftOperator? = null) : Expression(right, left), ExpressionValue<Boolean> {

    private val tautology = Tautology()
    val expressions = mutableListOf<Expression>()

    override fun getValue(): Boolean {
        return tautology.evaluate(expressions)
    }

}