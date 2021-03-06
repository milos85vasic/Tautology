package net.milosvasic.tautology.parser


import net.milosvasic.tautology.Expressions
import net.milosvasic.tautology.Tautology
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import net.milosvasic.tautology.operator.Operator
import net.milosvasic.tautology.separator.Separator

class TautologyParser(val delegate: TautologyParserDelegate) {

    private val MEMBER_KEY = "MEMBER_"
    private val tautology = Tautology()
    private val operatorOr = Operator.OR()
    private val operatorAnd = Operator.AND()
    private val operatorNot = Operator.NOT()
    private val separatorOpen = Separator.OPEN()
    private val separatorClose = Separator.CLOSE()

    fun parse(line: String, parserExpressionMembers: MutableMap<String, String> = mutableMapOf()): Expressions {
        val localDelegate = object : TautologyParserDelegate {
            override fun getExpressionValue(key: String): ExpressionValue? {
                var result = delegate.getExpressionValue(key)
                if (result == null) {
                    val expressionMember = parserExpressionMembers[key]
                    if (expressionMember != null) {
                        result = object : ExpressionValue {
                            override fun getValue(): Boolean {
                                val expressions = parse(expressionMember, parserExpressionMembers)
                                return tautology.evaluate(expressions)
                            }
                        }
                    }
                }
                return result
            }
        }

        var processedLine = line
        val builder = ExpressionBuilder()

        var opened = false
        var openedCount = 0
        val iterator = processedLine.iterator()
        var localExpressionMemberBuilder = StringBuilder()
        val localParserExpressionMembers = mutableListOf<String>()
        while (iterator.hasNext()) {
            val item = iterator.next()
            when ("$item") {
                separatorOpen.value -> {
                    if (!opened) {
                        opened = true
                    } else {
                        openedCount++
                        localExpressionMemberBuilder.append(item)
                    }
                }
                separatorClose.value -> {
                    if (!opened) {
                        throw IllegalStateException("Detected '${separatorClose.value}' before '${separatorOpen.value}'.")
                    } else {
                        if (openedCount > 0) {
                            openedCount--
                            localExpressionMemberBuilder.append(item)
                        } else {
                            opened = false
                            localParserExpressionMembers.add(
                                    localExpressionMemberBuilder.toString()
                            )
                            localExpressionMemberBuilder = StringBuilder()
                        }
                    }
                }
                else -> {
                    if (opened) {
                        localExpressionMemberBuilder.append(item)
                    }
                }
            }
        }

        localParserExpressionMembers.forEach {
            member ->
            val key = "$MEMBER_KEY${parserExpressionMembers.size}"
            parserExpressionMembers.put(key, member)
            processedLine = processedLine.replace("($member)", key)
        }

        processedLine
                .split(operatorAnd.value)
                .forEach {
                    element ->
                    var check = element.trim()
                    if (check.startsWith(operatorNot.value)) {
                        check = check.replace(operatorNot.value, "")
                        val result = localDelegate.getExpressionValue(check)
                        if (result != null) {
                            builder.append(
                                    BooleanExpression(
                                            result,
                                            null,
                                            operatorNot
                                    )
                            )
                        } else {
                            ors(element, localDelegate, builder)
                        }
                    } else {
                        val result = localDelegate.getExpressionValue(check)
                        if (result != null) {
                            builder.append(result)
                        } else {
                            ors(element, localDelegate, builder)
                        }
                    }
                }

        return builder.build()
    }

    private fun ors(element: String, localDelegate: TautologyParserDelegate, builder: ExpressionBuilder) {
        val ors = element.split(operatorOr.value)
        ors.forEach {
            orElement ->
            var orCheck = orElement.trim()
            if (orCheck.startsWith(operatorNot.value)) {
                orCheck = orCheck.replace(operatorNot.value, "")
                val orResult = localDelegate.getExpressionValue(orCheck)
                if (orResult != null) {
                    builder.append(
                            BooleanExpression(
                                    orResult,
                                    null,
                                    operatorNot
                            )
                    )
                    if (ors.indexOf(orElement) < ors.lastIndex) {
                        builder.append(operatorOr)
                    }
                } else {
                    throw IllegalArgumentException("Could not resolve key '$orCheck'")
                }
            } else {
                val orResult = localDelegate.getExpressionValue(orCheck)
                if (orResult != null) {
                    builder.append(orResult)
                    if (ors.indexOf(orElement) < ors.lastIndex) {
                        builder.append(operatorOr)
                    }
                } else {
                    throw IllegalArgumentException("Could not resolve key '$orCheck'")
                }
            }
        }
    }

}