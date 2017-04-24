package net.milosvasic.tautology

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
    }


}

