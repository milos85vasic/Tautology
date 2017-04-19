package net.milosvasic.tautology

import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExpressionBuilderTest {

    val tautology = Tautology()
    val dataSets = mutableListOf<DataSet>()

    @Before
    fun prepareDataSets() {
        dataSets.add(DataSet(0, 0, 0, true))
        dataSets.add(DataSet(1, 1, 1, true))
        dataSets.add(DataSet(1, 1, 2, false))
        dataSets.add(DataSet(1, 2, 1, false))
        dataSets.add(DataSet(2, 1, 1, false))
    }

    @Test
    fun testExpressionBuilder() {
        dataSets.forEach {
            (a, b, c, result) ->
            val builder = ExpressionBuilder()
                    .append(object : ExpressionValue<Boolean> {
                        override fun getValue(): Boolean {
                            return a == b
                        }
                    })
                    .append(object : ExpressionValue<Boolean> {
                        override fun getValue(): Boolean {
                            return a == c
                        }
                    })
                    .append(object : ExpressionValue<Boolean> {
                        override fun getValue(): Boolean {
                            return b == c
                        }
                    })

            println("[ $builder ] expects: $result")

            val expressions = builder.build()
            if (result) {
                Assert.assertTrue(tautology.evaluate(expressions))
            } else {
                Assert.assertFalse(tautology.evaluate(expressions))
            }
        }
    }

}

