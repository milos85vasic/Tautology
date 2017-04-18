package net.milosvasic.tryout.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.Tautology
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator

fun main(args: Array<String>) {

    val logger = SimpleLogger()
    val tautology = Tautology()

    val falseBooleanExpressionValue = object : ExpressionValue<Boolean> {
        override fun getValue(): Boolean {
            return false
        }
    }

    val trueBooleanExpressionValue = object : ExpressionValue<Boolean> {
        override fun getValue(): Boolean {
            return true
        }
    }

    val simpleBoolean = BooleanExpression(falseBooleanExpressionValue)
    val simpleBoolean2 = BooleanExpression(trueBooleanExpressionValue)
    val simpleBooleanWithNegation = BooleanExpression(falseBooleanExpressionValue, null, Operator.NOT())
    val simpleBooleanWithAndOperation = BooleanExpression(falseBooleanExpressionValue, Operator.AND())

    // false == false
    tautology.expressions.add(simpleBoolean)
    logger.v("", "Evaluating simple boolean [ ${tautology.evaluate()} ]")

    // !false == true
    tautology.expressions.clear()
    tautology.expressions.add(simpleBooleanWithNegation)
    logger.v("", "Evaluating simple boolean with negation [ ${tautology.evaluate()} ]")

    // false && true == false
    tautology.expressions.clear()
    tautology.expressions.add(simpleBooleanWithAndOperation)
    tautology.expressions.add(simpleBoolean2)
    logger.v("", "Evaluating simple boolean with 'AND' operation [ ${tautology.evaluate()} ]")

}

