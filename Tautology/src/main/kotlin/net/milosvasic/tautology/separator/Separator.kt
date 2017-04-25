package net.milosvasic.tautology.separator

abstract class Separator(var value: String) {

    abstract class OpeningSeparator(value: String) : Separator(value)

    abstract class ClosingSeparator(value: String) : Separator(value)

    class OPEN : OpeningSeparator("(")

    class CLOSE : ClosingSeparator(")")

}