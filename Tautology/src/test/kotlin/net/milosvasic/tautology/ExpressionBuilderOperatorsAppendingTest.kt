package net.milosvasic.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExpressionBuilderOperatorsAppendingTest {

    val tautology = Tautology()
    val logger = SimpleLogger()
    val dataSets = mutableListOf<DataSet>()

    @Before
    fun prepareDataSets() {
        dataSets.add(DataSet(0, 0, 0, true))
        dataSets.add(DataSet(1, 1, 1, true))
        dataSets.add(DataSet(1, 1, 2, true))
        dataSets.add(DataSet(1, 2, 1, true))
        dataSets.add(DataSet(2, 1, 1, true))
        dataSets.add(DataSet(1, 2, 3, false))
    }

    @Test
    fun testOperatorsAppending() {
        dataSets.forEach {
            (a, b, c, result) ->
            val builder = ExpressionBuilder()
                    .append(a == b)
                    .append(Operator.OR())
                    .append(a == c)
                    .append(Operator.OR())
                    .append(b == c)

            logger.v("", "[ $builder ] expects: $result")

            val expressions = builder.build()
            if (result) {
                Assert.assertTrue(tautology.evaluate(expressions))
            } else {
                Assert.assertFalse(tautology.evaluate(expressions))
            }
        }
    }

    @Test
    fun testOperatorsAppendingWithMultipleExpression() {
        dataSets.forEach {
            (a, b, c, expected) ->
            val builder = ExpressionBuilder()
                    .append(a == b)
                    .append(Operator.OR())
                    .append(a == c)
                    .append(Operator.OR())
                    .append(b == c)
                    .append(Operator.AND())
                    .append(
                            ExpressionBuilder()
                                    .append(a > c)
                                    .append(Operator.OR())
                                    .append(c > b)
                    )

            val expressions = builder.build()
            val result = tautology.evaluate(expressions)
            logger.v("", "[ $builder ] expects: $expected, result: $result")

//            if (result) {
//                Assert.assertTrue(tautology.evaluate(expressions))
//            } else {
//                Assert.assertFalse(tautology.evaluate(expressions))
//            }
        }
    }

}

