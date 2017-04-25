package net.milosvasic.tautology.parser


import net.milosvasic.tautology.Expressions
import net.milosvasic.tautology.Tautology
import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import net.milosvasic.tautology.operator.Operator
import java.util.regex.Pattern

class TautologyParser(val delegate: TautologyParserDelegate) {

    private val tautology = Tautology()
    private val operatorOr = Operator.OR()
    private val operatorAnd = Operator.AND()
    private val operatorNot = Operator.NOT()

    fun parse(line: String): Expressions {
        val expressionMembers = mutableMapOf<String, ExpressionMember>()
        val localDelegate = object : TautologyParserDelegate {
            override fun getExpressionValue(key: String): ExpressionValue? {
                var result = delegate.getExpressionValue(key)
                if (result == null) {
                    val expressionMember = expressionMembers[key]
                    if (expressionMember != null) {
                        result = object : ExpressionValue {
                            override fun getValue(): Boolean {
                                val expressions = parse(expressionMember.exprTrimmed)
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
        val m = Pattern.compile("\\(([^)]+)\\)").matcher(line)
        while (m.find()) {
            val expr = m.group(0)
            val exprTrimmed = m.group(1)
            val key = "${ExpressionMember.MEMBER_KEY}${expressionMembers.size}"
            expressionMembers.put(key, ExpressionMember(expr, exprTrimmed))
            processedLine = processedLine.replace(expr, key)
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
                            throw IllegalArgumentException("Could not resolve key '$check'")
                        }
                    } else {
                        val result = localDelegate.getExpressionValue(check)
                        if (result != null) {
                            builder.append(result)
                        } else {
                            throw IllegalArgumentException("Could not resolve key '$check'")
                        }
                    }
                }

        return builder.build()
    }

    private data class ExpressionMember(val expr: String, val exprTrimmed: String) {
        companion object {
            val MEMBER_KEY = "MEMBER_"
        }
    }

//    fun evaluate(message: String): Boolean {
//        if (pattern.isEmpty()) {
//            return true
//        }
//        if (evaluable(pattern)) {
//            if (!evaluable(pattern, operatorOr)) {
//                return evaluateAnd(message, pattern)
//            } else {
//                val elements = pattern.split(operatorOr.value)
//                for (element in elements) {
//                    val trimmed = element.trim()
//                    if (evaluable(trimmed, operatorAnd)) {
//                        if (evaluateAnd(message, trimmed)) {
//                            return true
//                        }
//                    } else {
//                        if (evaluateOr(message, trimmed)) {
//                            return true
//                        }
//                    }
//                }
//                return false
//            }
//        } else {
//            if (pattern.startsWith(operatorNot.value)) {
//                val check = pattern.replace(operatorNot.value, "")
//                if (message.containsIgnoreCase(check)) {
//                    return false
//                }
//            } else {
//                if (!message.containsIgnoreCase(pattern)) {
//                    return false
//                }
//            }
//            return true
//        }
//    }
//
//    fun evaluable(elements: List<String>): Boolean {
//        for (element in elements) {
//            if (element.contains(operatorAnd.value) || element.contains(operatorOr.value)) {
//                return true
//            }
//        }
//        return false
//    }
//
//    fun evaluable(elements: List<String>, operator: Operator): Boolean {
//        for (element in elements) {
//            if (element.contains(operator.value)) {
//                return true
//            }
//        }
//        return false
//    }
//
//    fun evaluateAnd(line: String): Boolean {
//
//        return true
//    }
//
//    fun evaluateOr(line: String, pattern: String): Boolean {
//        pattern
//                .split(operatorOr.value)
//                .forEach {
//                    var check = item.trim()
//                    if (check.startsWith(operatorNot.value)) {
//                        check = check.replace(operatorNot.value, "")
//                        if (!line.containsIgnoreCase(check)) {
//                            return true
//                        }
//                    } else {
//                        if (line.containsIgnoreCase(check)) {
//                            return true
//                        }
//                    }
//                }
//        return false
//    }

}