package net.milosvasic.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExpressionsOperatorsAppendingTest {

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
            val expressions = expression(
                    a == b,
                    Operator.OR(),
                    a == c,
                    Operator.OR(),
                    b == c
            )
            if (result) {
                Assert.assertTrue(tautology.evaluate(expressions))
            } else {
                Assert.assertFalse(tautology.evaluate(expressions))
            }
        }
    }

    @Test
    fun testOperatorsAppendingWithMultipleExpression() {
        val expectedResults = listOf(false, false, true, false, true, false)
        dataSets.forEachIndexed {
            index, (a, b, c, _) ->
            val expressions = expression(
                    expression(
                            a == b,
                            Operator.OR(),
                            a == c,
                            Operator.OR(),
                            b == c
                    ),
                    expression(
                            a > c,
                            Operator.OR(),
                            c > b
                    )
            )

            val expected = expectedResults[index]
            val result = tautology.evaluate(expressions)

            if (expected) {
                Assert.assertTrue(result)
            } else {
                Assert.assertFalse(result)
            }
        }
    }

}

