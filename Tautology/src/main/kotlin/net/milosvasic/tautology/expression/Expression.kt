package net.milosvasic.tautology.expression

import net.milosvasic.tautology.operator.Operator

abstract class Expression(val right: Operator.RightOperator? = null, val left: Operator.LeftOperator? = null)