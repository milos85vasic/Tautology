package net.milosvasic.tautology

import org.junit.Before
import org.junit.Test

class TautologyMultipleExpressionTest {

    val tautology = Tautology()
    val dataSets = mutableListOf<DataSet>()

    @Before
    fun prepareDataSets() {
        // ((1 == 1) || (1 == 1)) && (1 >= 1)
        dataSets.add(DataSet(1, 1, 1, true))
    }

    @Test
    fun testTautologyMultipleExpression() {
        // ((a == b) || (a == c)) && (c >= a)
        dataSets.forEach {
            (a, b, c, result) ->
            println("[ $a ][ $b ][ $c ][ $result ]")
            
        }
    }

    data class DataSet(
            val a: Int,
            val b: Int,
            val c: Int,
            val result: Boolean
    )

}

