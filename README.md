#### `fluenTry` is a java library that enables you to handle common `try/catch` procedures in a declarative, composable way.

Declarative style of programming often goes hand in hand with functional style of programming where we describe composable computations on the high level
and in the end we run it. Java has somewhat decent support for functional style of programming which enables us to raise the abstraction level even higher.

The main class in the library is called `Try`

`Try` has a few overloaded static constructors for lazy computations which is called `Try.of()`.
`Try.of()` may take `Runnable`, `Consumer<T> with arg`, `Callable<T>` or `Function<T, V> with arg` and `BiFunction<T, U, V> with two args`

`Try` has two sub-classes: `Success<T>` which holds the successful value and `Failure` which holds the exception.

`Try<T>` can be used in two ways:
  - composing potentially fallible computations with the help of combinators such as `map`, `flatMap`, `filter`.
  - finalizing the Try computations by either extracting the inner value of Try or running any side-effecting functions: `get`, `fold`, `endWith` and many others. 

`Try<T>` class suggests a few methods for handling some common situations associated with try/catch procedures.
These situations can be:
 * folding the computation with successful or client-defined default value.
 * finalizing computation with client-specific side-effecting tasks (Runnables, Exception Consumers).
 * catching client-specified exception and returning default value / throwin another exception, or else continuing with success value.
 * catching client-specified exception and running some side-effecting task afterwards / throwing another exception, or else do nothing.

### Examples below: 

### #1 `Try#map & Try#fold` - `Try#map` tranforms the value into new value if the initial computation was successful and returns `Success(newValue)`, or else returns the `Failure(exception)`. `Try#fold` is used to unwrap the `T` from `Try<T>` in a pure way. 

try getting the element from the list, if it fails return 0, or else return the square of the success value

with pure java
```java
   public static int getAndThenDouble(List<Integer> list, int index) {
       String result;
       try {
           int number = list.get(index);
           result = String.valueOf(number * 2);
       } catch (Exception e) {
           result = "0";
       }
       return result;
   }
```

with `fluenTry` - takes two arguments with `BiFunction` and is composed via `map` and `fold` combinators. May also take 3 args and `TriFunction`, but in rare cases :)
```java
   public static int getAndThenDouble(List<Integer> list, int index) {
       return Try.of(list, index, List::get).map(n -> n * 2).fold(Function.identity(), 0);
   }
```

`fluenTry` enables you to maximize the perks of functional programming and make your functions total, compose possibly failable computations fearlessly and forget about throwing exceptions. Let's see an example below:

Failed computation which returns Failure(ArithmeticException) instead of blowing up the calling stack by throwing an exception and succeeds with pure value with the help of `fold` combinator.
```java
   double result = Try.of(() -> Math.random() * 2 * 5)
           .flatMap(n -> Try.of(() -> n + 1))
           .flatMap(n -> Try.of(() -> 55 / 0)) // This is where the `Success` turns into `Failure`
           .map(Math::sqrt)
           .fold(Function.identity(), 0.0); // but with the help of `fold` combinator we turn that into pure value (0.0)
```

Let's see another example:

with pure java:
```java
   public static Optional<Person> parseJson(String json) {
       var jsonParser = new JsonParser<Person>();
       Optional<Person> result;
       try {
           result = Optional.of(jsonParser.fromJson(json));
       } catch (JsonParsingException jpe) {
           result = Optional.empty();
       }
       return result;
   }
```

with `fluenTry`:
```java
   public static Optional<Person> parseJson(String json) {
       var parser = new JsonParser<Person>();
       return Try.of(json, parser::fromJson).toOption();
   }
```
# TO BE CONTINUED !!!




