package net.milosvasic.tautology.operator

abstract class Operator(var value: String) {

    abstract class LeftOperator(value: String) : Operator(value)

    abstract class RightOperator(value: String) : Operator(value)

    class AND : RightOperator("&&")

    class OR : RightOperator("||")

    class NOT : LeftOperator("!")

}

