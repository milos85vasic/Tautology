package net.milosvasic.tryout.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.Tautology
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator

fun main(args: Array<String>) {

    val logger = SimpleLogger()
    val tautology = Tautology()

    val booleanExpressionValue = object : ExpressionValue<Boolean> {
        override fun get(): Boolean {
            return false
        }
    }

    val simpleBoolean = BooleanExpression(booleanExpressionValue)
    val simpleBooleanWithNegation = BooleanExpression(booleanExpressionValue, Operator.NOT())

    tautology.expressions.add(simpleBoolean)
    logger.v("", "Evaluating simple boolean [ ${tautology.evaluate()} ]")

    tautology.expressions.clear()
    tautology.expressions.add(simpleBooleanWithNegation)
    logger.v("", "Evaluating simple boolean with negation [ ${tautology.evaluate()} ]")

}

