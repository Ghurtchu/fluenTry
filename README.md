#### `fluenTry` is a java library that enables you to handle common `try/catch` procedures in a declarative way.

The main class in the library is called `Try`

`Try` has a few overloaded static constructors for lazy computations which is called `Try.evaluate()`.
`Try.evaluate()` may take `Runnable`, `Consumer<T> with argument`, `Callable<T>` or `Function<T, V with argument`

`Try` has two sub-classes: `Succes<T>` and `Failure<Exception>` for representing successful and failed computations respectively.

`Try<T>` class suggests a few methods for handling some common situations associated with try/catch procedures.
These situations can be:
 * folding the computation with success `Function<T, V>` or client-defined default value.
 * finalizing computation with client-specific `Runnable` tasks.
 * catching client-specified exception and returning default value / throwin another exception, or else continuing with success value.
 * catching client-specified exception and running some `Runnable` task afterwards / throwing another exception, or else do nothing.

### Examples below: 
### #1 `Try#fold` - Returns the value based on if the main computation succeeded or failed
try division, if fails return 0, or multiply the result by 10

with pure java
```java
  int result;
  try {
      result = 42 / 0;
      result = result * 10;
  } catch (Exception e) {
      result = 0;
  }
  return result;
```

with `fluenTry`
```java
int result = Try.evaluate(() -> 42 / 0).fold(num -> num * 10, 0);
```

try extracting the substring, if it fails return "Default String" or else return the success

with pure java
```java
  String result;
  try {
      result = "A big enormous string".substring(0, 100000);
  } catch (Exception e) {
      result = "Default String";
  }
  return result;
```

with `fluenTry`
```java
  String result = Try.evaluate(() -> "A big enormous string".substring(0, 100000)).fold(Function.identity(), "Default String");
```

try getting the element from the list, if it fails return 0, or else return the square of the success value

with pure java
```java
    int result;
    try {
        int number = List.of(1, 2, 3, 4, 5).get(10000);
        result = number * number;
    } catch (Exception e) {
        result = 0;
    }
    return result;
```

with `fluenTry`
```java
  int result = Try.evaluate(() -> List.of(1, 2, 3, 4, 5).get(100000)).fold(i -> i * i, 0);
```

