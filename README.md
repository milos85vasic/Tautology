# Tautology
Library used for resolving simple logical problems.

# How to use
Tautology use is presented with the following examples:

a < b
```kotlin
val result = expression(a < b).evaluate()
// result == true
```

a > b or c > b
```kotlin
val expression = expression(
        a > b,
        Operator.OR(),
        c > b
)
val result = expression.evaluate()
// result == true
```

a > b and c > b
```kotlin
val expression = expression(
        a > b,
        Operator.AND(),
        c > b
)
val result = expression.evaluate()
// result == false
```

the same expression can be done like this:
```kotlin
val expression = expression(
        a > b,
        c > b
)
```

(b > a) and (d > c and e > d) and (a > e or b > a) or c > e
```kotlin
val expression = expression(
        b > a,
        expression(
                d > c,
                e > d
        ),
        expression(
                a > e,
                Operator.OR(),
                b > a
        ),
        Operator.OR(),
        c > e
)
val result = expression.evaluate()
// result == true
```

## Negation
!(a > b)
```kotlin
val expression = expression(
        a > b,
        Operator.NOT()
)
val result = expression.evaluate()
// result == true
```

!(a > b) and !(a > b or a > e)
```kotlin
val expression = expression(
        expression(
                a > b,
                Operator.NOT()
        ),
        expression(
                a > d,
                Operator.OR(),
                a > e
        ),
        Operator.NOT()
)
val result = expression.evaluate()
// result == true
```

## Complex example
b > a and !(d < c and e < d) and !(c > e)
```kotlin
val expression = expression(
        b > a,
        expression(
                expression(
                        d < c,
                        e < d
                ),
                Operator.NOT()
        ),
        expression(
                expression(
                        a > e,
                        Operator.OR(),
                        a > b
                ),
                Operator.NOT()
        ),
        expression(
                c > e,
                Operator.NOT()
        )
)
val result = expression.evaluate()
// result == true
```

# Tautology parser
Used to parse tautology strings into expressions.

See example of use:
```kotlin
// Some constants
val TRUE_1 = "TRUE_1"
val TRUE_2 = "TRUE_2"
val TRUE_3 = "TRUE_3"
val NOT_TRUE_1 = "NOT_TRUE_1"
val NOT_TRUE_2 = "NOT_TRUE_2"
val NOT_TRUE_3 = "NOT_TRUE_3"

// Some expression values
val soTrue = object : ExpressionValue {
        override fun getValue(): Boolean {
            return true
        }
    }

val notTrue = object : ExpressionValue {
    override fun getValue(): Boolean {
        return false
    }
}

// Delegate responsible for obtaining expression value by the key
val delegate = object : TautologyParserDelegate {
    override fun getExpressionValue(key: String): ExpressionValue? {
        return when (key) {
            TRUE_1, TRUE_2, TRUE_3 -> soTrue
            NOT_TRUE_1, NOT_TRUE_2, NOT_TRUE_3 -> notTrue
            else -> null
        }
    }
}

val tautology = Tautology()
val parser = TautologyParser(delegate)

// Expression to parse: (!true or true) and (false or !false)
val expressions = parser.parse("(!$TRUE_1 || $TRUE_2) && !($NOT_TRUE_1 || $NOT_TRUE_2)")
val result = tautology.evaluate(expressions) // Result is: true
```