package net.milosvasic.tautology

import net.milosvasic.tautology.operator.Operator
import org.junit.Assert
import org.junit.Test


class TautologyMiscTest {

    val a = 1
    val b = 2
    val c = 3
    val d = 4
    val e = 5

    @Test
    fun testMisc() {
        var result = expression(a < b).evaluate()
        Assert.assertTrue(result)

        result = expression(b > c).evaluate()
        Assert.assertFalse(result)

        var expression = expression(
                a > b,
                Operator.OR(),
                c > b
        )
        result = expression.evaluate()
        Assert.assertTrue(result)

        expression = expression(
                a > b,
                Operator.AND(),
                c > b
        )
        result = expression.evaluate()
        Assert.assertFalse(result)

        expression = expression(
                expression(
                        a > b,
                        Operator.AND(),
                        c > b
                ),
                Operator.OR(),
                expression(
                        d > c,
                        d > b,
                        d > a
                )
        )
        result = expression.evaluate()
        Assert.assertTrue(result)

        expression = expression(
                expression(
                        a > b,
                        Operator.AND(),
                        c > b
                ),
                Operator.AND(),
                expression(
                        d > c,
                        d > b,
                        d > a
                )
        )
        result = expression.evaluate()
        Assert.assertFalse(result)

        expression = expression(
                b > a,
                expression(
                        d > c,
                        e > d
                ),
                expression(
                        a > e,
                        Operator.OR(),
                        b > a
                )
        )
        result = expression.evaluate()
        Assert.assertTrue(result)

        expression = expression(
                b > a,
                expression(
                        d > c,
                        e > d
                ),
                expression(
                        a > e,
                        Operator.OR(),
                        b > a
                ),
                Operator.OR(),
                c > e
        )
        result = expression.evaluate()
        Assert.assertTrue(result)

        expression = expression(
                b > a,
                expression(
                        d > c,
                        e > d
                ),
                expression(
                        a > e,
                        Operator.OR(),
                        b > a
                ),
                Operator.AND(),
                c > e
        )
        result = expression.evaluate()
        Assert.assertFalse(result)
    }


}

