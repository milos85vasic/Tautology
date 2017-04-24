package net.milosvasic.tautology.parser


import net.milosvasic.tautology.Expressions
import net.milosvasic.tautology.expression.builder.ExpressionBuilder
import net.milosvasic.tautology.operator.Operator

class TautologyParser(val delegate: TautologyParserDelegate) {

    private val operatorOr = Operator.OR()
    private val operatorAnd = Operator.AND()
    private val operatorNot = Operator.NOT()

    fun parse(line: String): Expressions {
        val builder = ExpressionBuilder()
        line
                .split(operatorAnd.value)
                .forEach {
                    element ->
                    var check = element.trim()
                    if (check.startsWith(operatorNot.value)) {
                        check = check.replace(operatorNot.value, "")
                        builder.append(delegate.getExpressionValue(check))
                        builder.append(operatorNot)
                    } else {
                        builder.append(delegate.getExpressionValue(check))
                    }
                }
        return builder.build()
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