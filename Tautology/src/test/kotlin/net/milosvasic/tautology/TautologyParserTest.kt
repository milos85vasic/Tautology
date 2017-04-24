package net.milosvasic.tautology

import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.parser.TautologyParser
import net.milosvasic.tautology.parser.TautologyParserDelegate
import org.junit.Assert
import org.junit.Test

class TautologyParserTest {

    val SO_TRUE = "SO_TRUE"
    val NOT_SO_TRUE = "NOT_SO_TRUE"

    val soTrue = object : ExpressionValue {
        override fun getValue(): Boolean {
            return true
        }
    }

    val notSoTrue = object : ExpressionValue {
        override fun getValue(): Boolean {
            return false
        }
    }

    val delegate = object : TautologyParserDelegate {
        override fun getExpressionValue(key: String): ExpressionValue {
            return when (key) {
                SO_TRUE -> soTrue
                NOT_SO_TRUE -> notSoTrue
                else -> throw IllegalArgumentException("No expression for the key: $key")
            }
        }
    }

    val tautology = Tautology()
    val parser = TautologyParser(delegate)

    @Test
    fun testTautologyParser() {
        var expressions = parser.parse(SO_TRUE)
        var result = tautology.evaluate(expressions)
        Assert.assertTrue(result)

        expressions = parser.parse(NOT_SO_TRUE)
        result = tautology.evaluate(expressions)
        Assert.assertFalse(result)
    }

}