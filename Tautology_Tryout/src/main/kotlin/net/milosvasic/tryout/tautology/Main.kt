package net.milosvasic.tryout.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.Tautology
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.ExpressionValue

fun main(args: Array<String>){

    val logger = SimpleLogger()
    val tautology = Tautology()

    val simpleBoolean = BooleanExpression(object : ExpressionValue<Boolean> {
        override fun get(): Boolean {
            return true
        }
    })

    tautology.expressions.add(simpleBoolean)

    logger.v("", "Evaluating simple boolean [ ${tautology.evalute()} ]")

}

