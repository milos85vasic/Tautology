package net.milosvasic.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExpressionBuilderTest {

    val tautology = Tautology()
    val logger = SimpleLogger()
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
    fun simpleAppend() {
        logger.d("", "Simple append [ START ]")
        dataSets.forEach {
            (a, b, c, result) ->
            val builder = ExpressionBuilder()
                    .append(object : ExpressionValue {
                        override fun getValue(): Boolean {
                            return a == b
                        }
                    })
                    .append(object : ExpressionValue {
                        override fun getValue(): Boolean {
                            return a == c
                        }
                    })
                    .append(object : ExpressionValue {
                        override fun getValue(): Boolean {
                            return b == c
                        }
                    })

            logger.v("", "[ $builder ] expects: $result")

            val expressions = builder.build()
            if (result) {
                Assert.assertTrue(tautology.evaluate(expressions))
            } else {
                Assert.assertFalse(tautology.evaluate(expressions))
            }
        }
        logger.d("", "Simple append [ END ]\n")
    }

    @Test
    fun simpleAppendWithShortAppending() {
        logger.d("", "Simple append with short appending [ START ]")
        dataSets.forEach {
            (a, b, c, result) ->
            val builder = ExpressionBuilder()
                    .append(a == b)
                    .append(a == c)
                    .append(b == c)

            logger.v("", "[ $builder ] expects: $result")

            val expressions = builder.build()
            if (result) {
                Assert.assertTrue(tautology.evaluate(expressions))
            } else {
                Assert.assertFalse(tautology.evaluate(expressions))
            }
        }
        logger.d("", "Simple append with short appending [ END ]\n")
    }

}

