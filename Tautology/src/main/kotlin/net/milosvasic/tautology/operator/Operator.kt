package net.milosvasic.tautology.operator

abstract class Operator(var value: String) {

    class AND : Operator("&&")

    class OR : Operator("||")

    class NOT : Operator("!")

}

