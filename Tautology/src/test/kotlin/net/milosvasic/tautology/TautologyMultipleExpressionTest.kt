package net.milosvasic.tautology

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.MultipleExpression
import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TautologyMultipleExpressionTest {

    val tautology = Tautology()
    val dataSets = mutableListOf<DataSet>()

    @Before
    fun prepareDataSets() {
        // ((1 == 1) || (1 == 1)) && (1 >= 1)
        dataSets.add(DataSet(1, 1, 1, true))
        // ((1 == 2) || (1 == 3)) && (3 >= 1)
        dataSets.add(DataSet(1, 2, 3, false))
        // ((3 == 2) || (3 == 3)) && (3 >= 1)
        dataSets.add(DataSet(3, 2, 3, true))
        // ((3 == 4) || (3 == 3)) && (3 >= 1)
        dataSets.add(DataSet(3, 4, 3, true))
    }

    @Test
    fun testTautologyMultipleExpression() {
        // ((a == b) || (a == c)) && (c >= a)
        dataSets.forEach {
            (a, b, c, result) ->
            println("(($a == $b) || ($a == $c)) && ($c >= $a) expects: $result")

            val expressions = mutableListOf<Expression>()

            val abValue = object : ExpressionValue<Boolean> {
                override fun getValue(): Boolean {
                    return a == b
                }
            }
            val acValue = object : ExpressionValue<Boolean> {
                override fun getValue(): Boolean {
                    return a == c
                }
            }
            val caValue = object : ExpressionValue<Boolean> {
                override fun getValue(): Boolean {
                    return c >= a
                }
            }

            val abExpression = BooleanExpression(abValue, Operator.OR())
            val acExpression = BooleanExpression(acValue)
            val caExpression = BooleanExpression(caValue)

            val multiple = MultipleExpression()
            multiple.expressions.add(abExpression)
            multiple.expressions.add(acExpression)

            val leftExpression = BooleanExpression(multiple, Operator.AND())
            expressions.add(leftExpression)
            expressions.add(caExpression)

            if (result) {
                Assert.assertTrue(tautology.evaluate(expressions))
            } else {
                Assert.assertFalse(tautology.evaluate(expressions))
            }
        }
    }

}

