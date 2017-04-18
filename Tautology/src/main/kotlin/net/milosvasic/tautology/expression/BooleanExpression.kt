package net.milosvasic.tautology.expression

import net.milosvasic.tautology.operator.Operator

class BooleanExpression(val value: ExpressionValue<Boolean>, operator: Operator? = null) : Expression(operator)

