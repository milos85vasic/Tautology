package net.milosvasic.tautology

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Test

class TautologyTest {

    val tautology = Tautology()

    @Test
    fun testTautology(){
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
        val simpleBooleanWithOrOperation = BooleanExpression(falseBooleanExpressionValue, Operator.OR())
        val simpleBooleanWithAndOperation = BooleanExpression(falseBooleanExpressionValue, Operator.AND())

        // false == false
        tautology.expressions.add(simpleBoolean)
        Assert.assertFalse(tautology.evaluate())

        // !false == true
        tautology.expressions.clear()
        tautology.expressions.add(simpleBooleanWithNegation)
        Assert.assertTrue(tautology.evaluate())

        // false && true == false
        tautology.expressions.clear()
        tautology.expressions.add(simpleBooleanWithAndOperation)
        tautology.expressions.add(simpleBoolean2)
        Assert.assertFalse(tautology.evaluate())

        // false || true == true
        tautology.expressions.clear()
        tautology.expressions.add(simpleBooleanWithOrOperation)
        tautology.expressions.add(simpleBoolean2)
        Assert.assertTrue(tautology.evaluate())

        // false && false == false
        tautology.expressions.clear()
        tautology.expressions.add(simpleBooleanWithAndOperation)
        tautology.expressions.add(simpleBoolean)
        Assert.assertFalse(tautology.evaluate())

        // false || false == false
        tautology.expressions.clear()
        tautology.expressions.add(simpleBooleanWithOrOperation)
        tautology.expressions.add(simpleBoolean)
        Assert.assertFalse(tautology.evaluate())

    }

}