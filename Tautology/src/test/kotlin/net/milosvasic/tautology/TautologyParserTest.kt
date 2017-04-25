package net.milosvasic.tautology

import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.parser.TautologyParser
import net.milosvasic.tautology.parser.TautologyParserDelegate
import org.junit.Assert
import org.junit.Test

class TautologyParserTest {

    val TRUE_1 = "TRUE_1"
    val TRUE_2 = "TRUE_2"
    val TRUE_3 = "TRUE_3"
    val NOT_TRUE_1 = "NOT_TRUE_1"
    val NOT_TRUE_2 = "NOT_TRUE_2"
    val NOT_TRUE_3 = "NOT_TRUE_3"

    val soTrue = object : ExpressionValue {
        override fun getValue(): Boolean {
            return true
        }
    }

    val notTrue = object : ExpressionValue {
        override fun getValue(): Boolean {
            return false
        }
    }

    val delegate = object : TautologyParserDelegate {
        override fun getExpressionValue(key: String): ExpressionValue {
            return when (key) {
                TRUE_1, TRUE_2, TRUE_3 -> soTrue
                NOT_TRUE_1, NOT_TRUE_2, NOT_TRUE_3 -> notTrue
                else -> throw IllegalArgumentException("No expression for the key: $key")
            }
        }
    }

    val tautology = Tautology()
    val parser = TautologyParser(delegate)

    @Test
    fun testTautologyParser() {
        var expressions = parser.parse(TRUE_1)
        var result = tautology.evaluate(expressions)
        Assert.assertTrue(result)

        expressions = parser.parse(NOT_TRUE_1)
        result = tautology.evaluate(expressions)
        Assert.assertFalse(result)

        expressions = parser.parse("$TRUE_1 && $TRUE_2")
        result = tautology.evaluate(expressions)
        Assert.assertTrue(result)

        expressions = parser.parse("$TRUE_1 && !$NOT_TRUE_1")
        result = tautology.evaluate(expressions)
        Assert.assertTrue(result)

        expressions = parser.parse("!$NOT_TRUE_1")
        result = tautology.evaluate(expressions)
        Assert.assertTrue(result)

        expressions = parser.parse("!$NOT_TRUE_1 && !$NOT_TRUE_2")
        result = tautology.evaluate(expressions)
        Assert.assertTrue(result)

        expressions = parser.parse("(!$NOT_TRUE_1 && !$NOT_TRUE_2) && $TRUE_1")
        result = tautology.evaluate(expressions)
        Assert.assertTrue(result)
    }

}