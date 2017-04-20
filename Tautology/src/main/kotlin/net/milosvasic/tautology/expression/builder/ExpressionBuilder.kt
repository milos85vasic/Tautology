package net.milosvasic.tautology.expression.builder

import net.milosvasic.tautology.expression.BooleanExpression
import net.milosvasic.tautology.expression.Expression
import net.milosvasic.tautology.expression.ExpressionValue
import net.milosvasic.tautology.expression.MultipleExpression
import net.milosvasic.tautology.operator.Operator

class ExpressionBuilder {

    private val expressions = mutableListOf<Expression>()

    fun append(value: Boolean): ExpressionBuilder {
        return append(object : ExpressionValue<Boolean> {
            override fun getValue(): Boolean {
                return value
            }
        })
    }

    fun append(value: ExpressionValue<Boolean>): ExpressionBuilder {
        if (!expressions.isEmpty() && expressions.last().right == null) {
            if (expressions.last() is BooleanExpression) {
                val lastItem = expressions.last() as BooleanExpression
                val updated = BooleanExpression(lastItem.value, Operator.AND())
                expressions[expressions.lastIndex] = updated
            }
        }
        expressions.add(BooleanExpression(value))
        return this
    }

    fun append(operator: Operator): ExpressionBuilder {
        if (expressions.isEmpty()) {
            throw IllegalStateException("No expressions appended.")
        }
        when (operator) {
            is Operator.RightOperator -> {
                if (expressions.last() is BooleanExpression) {
                    val lastItem = expressions.last() as BooleanExpression
                    if (lastItem.right != null) {
                        val msg = StringBuilder()
                                .append("Couldn't apply operator '${operator.value}'.")
                                .append("Expression already has assigned operator '${lastItem.right.value}'.")
                                .toString()
                        throw IllegalStateException(msg)
                    } else {
                        val updated = BooleanExpression(lastItem.value, operator)
                        expressions[expressions.lastIndex] = updated
                    }
                }
            }
            is Operator.LeftOperator -> {
                if (expressions.last() is BooleanExpression) {
                    val lastItem = expressions.last() as BooleanExpression
                    if (lastItem.left != null) {
                        val msg = StringBuilder()
                                .append("Couldn't apply operator '${operator.value}'.")
                                .append("Expression already has assigned operator '${lastItem.left.value}'.")
                                .toString()
                        throw IllegalStateException(msg)
                    } else {
                        val updated = BooleanExpression(lastItem.value, lastItem.right, operator)
                        expressions[expressions.lastIndex] = updated
                    }
                }
            }
            else -> IllegalArgumentException("Unsupported operator: '${operator.value}'")
        }
        return this
    }

    fun append(builder: ExpressionBuilder): ExpressionBuilder {
        val multiple = MultipleExpression()
        multiple.expressions.addAll(builder.build())
        /*
        if (!expressions.isEmpty() && expressions.last().right == null) {
            if (expressions.last() is BooleanExpression) {
                val lastItem = expressions.last() as BooleanExpression
                val updated = BooleanExpression(lastItem.value, Operator.AND())
                expressions[expressions.lastIndex] = updated
            }
        }
        expressions.add(BooleanExpression(value))
        * */
        return this
    }

    fun build(): List<Expression> {
        return expressions
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()
        expressions.forEachIndexed {
            index, expression ->
            if (expression.left != null) {
                stringBuilder.append(expression.left.value)
            }
            if (expression is ExpressionValue<*>) {
                if (index > 0) {
                    stringBuilder.append(" ")
                }
                stringBuilder.append("${expression.getValue()}")
            }
            if (expression.right != null) {
                stringBuilder
                        .append(" ")
                        .append(expression.right.value)
            }
        }
        return stringBuilder.toString()
    }

}

