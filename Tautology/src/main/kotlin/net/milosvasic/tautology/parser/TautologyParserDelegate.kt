package net.milosvasic.tautology.parser


import net.milosvasic.tautology.expression.ExpressionValue

interface TautologyParserDelegate {

    fun getExpressionValue(key: String): ExpressionValue

}