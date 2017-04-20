package net.milosvasic.tautology

import net.milosvasic.logger.SimpleLogger
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.MultipleExpression
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExpressionBuilderOperatorsAppendingTest {

    val tautology = Tautology()
    val logger = SimpleLogger()
    val dataSets = mutableListOf<DataSet>()

    @Test
    fun testOperatorsAppending() {
        dataSets.clear()
        dataSets.add(DataSet(0, 0, 0, true))
        dataSets.add(DataSet(1, 1, 1, true))
        dataSets.add(DataSet(1, 1, 2, true))
        dataSets.add(DataSet(1, 2, 1, true))
        dataSets.add(DataSet(2, 1, 1, true))
        dataSets.add(DataSet(1, 2, 3, false))
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
//        dataSets.clear()
//        // ((1 == 1) || (1 == 1)) && (1 >= 1)
//        dataSets.add(DataSet(1, 1, 1, true))
//        // ((1 == 2) || (1 == 3)) && (3 >= 1)
//        dataSets.add(DataSet(1, 2, 3, false))
//        // ((3 == 2) || (3 == 3)) && (3 >= 1)
//        dataSets.add(DataSet(3, 2, 3, true))
//        // ((3 == 4) || (3 == 3)) && (3 >= 1)
//        dataSets.add(DataSet(3, 4, 3, true))
//        // ((a == b) || (a == c)) && (c >= a)
//        dataSets.forEach {
//            (a, b, c, result) ->
//            println("(($a == $b) || ($a == $c)) && ($c >= $a) expects: $result")
//
//
//        }
    }

}

