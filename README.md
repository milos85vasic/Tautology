# Tautology
Library used for resolving simple logical problems.

# How to use
Tautology use is presented with the following examples:

a < b
```
var result = expression(a < b).evaluate()
Assert.assertTrue(result)
```

a > b || c > b
```
var expression = expression(
        a > b,
        Operator.OR(),
        c > b
)
result = expression.evaluate()
Assert.assertTrue(result)
```

a > b && c > b
```
expression = expression(
        a > b,
        Operator.AND(),
        c > b
)
result = expression.evaluate()
Assert.assertFalse(result)
```

the same expression can be done like this:
```
expression = expression(
        a > b,
        c > b
)
```

(b > a) && (d > c && e > d) && (a > e || b > a) || c > e
```
expression = expression(
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
result = expression.evaluate()
Assert.assertTrue(result)
```