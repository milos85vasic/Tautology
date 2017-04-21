package net.milosvasic.tautology

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Test

class TautologyTest {

    val tautology = Tautology()

    @Test
    fun testTautology(){
        val expressions = mutableListOf<Expression>()

        val falseBooleanExpressionValue = object : ExpressionValue {
            override fun getValue(): Boolean {
                return false
            }
        }

        val trueBooleanExpressionValue = object : ExpressionValue {
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
        expressions.add(simpleBoolean)
        Assert.assertFalse(tautology.evaluate(expressions))

        // !false == true
        expressions.clear()
        expressions.add(simpleBooleanWithNegation)
        Assert.assertTrue(tautology.evaluate(expressions))

        // false && true == false
        expressions.clear()
        expressions.add(simpleBooleanWithAndOperation)
        expressions.add(simpleBoolean2)
        Assert.assertFalse(tautology.evaluate(expressions))

        // false || true == true
        expressions.clear()
        expressions.add(simpleBooleanWithOrOperation)
        expressions.add(simpleBoolean2)
        Assert.assertTrue(tautology.evaluate(expressions))

        // false && false == false
        expressions.clear()
        expressions.add(simpleBooleanWithAndOperation)
        expressions.add(simpleBoolean)
        Assert.assertFalse(tautology.evaluate(expressions))

        // false || false == false
        expressions.clear()
        expressions.add(simpleBooleanWithOrOperation)
        expressions.add(simpleBoolean)
        Assert.assertFalse(tautology.evaluate(expressions))

    }

}