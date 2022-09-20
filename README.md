#### `fluenTry` is a lightweight java library that enables you to handle common `try/catch` procedures in a declarative, composable way.

Declarative style of programming often goes hand in hand with functional style of programming where we describe composable computations on the high level
and in the end we run it. Java has somewhat decent support for functional style of programming which enables us to raise the abstraction level even higher.

The main class in the library is called `Try`.

`Try` has a few overloaded static constructors for instantiating lazy computations which is called `Try.of()`.

`Try` instance may be constructed via one of these five arguments:
 - `Runnable`
 - `Consumer<T> with arg`,
 - `Supplier<T>`,
 - `Function<T, V> with arg`,
 - `BiFunction<T, U, V> with two args`

`Try` has two sub-classes: `Success<T>` which holds the successful value and `Failure` which holds the exception.

`Try` may be used in different ways, so withot further ado let's see the examples below:

#### #1 A combination of `Try#map and Try#fold` - `Try#map` tranforms the value into new value if the initial computation was successful and returns `Success` with the transformed value in it, or else returns the `Failure` instance which holds the exception. `Try#fold` is used to unwrap the `T` from `Try<T>` in a pure way. 

problem description: try getting the element from the list, if it fails return stringified 0, or else return the stringified square of the success value.

solution with pure java
```java
   public static String getAndThenDoubleAndThenStringify(List<Integer> list, int index) {
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

solution with `fluenTry` - takes three arugments in total two of which are arguments to the third argument, which is a  `BiFunction` instance and is composed via `map` and `fold` combinators. May be also extended with `Tri-Quad-Quint Function` instances, but in rare cases :).
```java
   public static String getAndThenDoubleAndThenStringify(List<Integer> list, int index) {
       return Try.of(list, index, List::get).map(n -> n * 2).fold(String::valueOf, "0");
   }
```

`fluenTry` enables you to maximize the perks of functional programming and turn your partial functions into total ones, compose possibly failable computations fearlessly and forget about throwing exceptions. Let's see another example below:

Here is a Failed computation which returns `Failure(ArithmeticException)` instead of blowing up the calling stack by throwing an exception and succeeds with pure value with the help of `fold` combinator.
```java
   double result = Try.of(() -> Math.random() * 2 * 5)
           .flatMap(n -> Try.of(() -> n + 1))
           .flatMap(n -> Try.of(() -> 55 / 0)) // This is where the `Success` turns into `Failure`
           .map(Math::sqrt)
           .fold(Function.identity(), 0.0); // but with the help of `fold` combinator we turn that into pure value (0.0)
```

Let's see another example:

problem description: try to parse json string into `Person` instance, if succeeds return true or else return false.

solution with pure java:
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

solution with `fluenTry` - takes a string argument and the `Function<T, V>` instance as a second argument, finally calles `toOptional()` which converts the `Try<T>` into `Optional<T>`:
```java
   public static Optional<Person> parseJson(String json) {
       var parser = new JsonParser<Person>();
       return Try.of(json, parser::fromJson).toOptional();
   }
```

Let's see something related to java Enums:

problem description: Write a method on Weekend enum which returns true of the passed string is a Weekend day or else return false.

solution with pure java:
```java
    public enum Weekend {
    
        SATURDAY,
        SUNDAY;

        public static boolean isWeekend(String day) {
            try {
                String normalized = day.trim().toUpperCase();
                Weekend.valueOf(normalized);
                return true;
            } catch (IllegalArgumentException iae) {
                return false;
            }
        }

   }
```

with `fluenTry:` - takes a `Supplier<String>` as an argument, then `maps` and finally `folds` to either `true` or `false`:
```java
    public enum Weekend {
    
        SATURDAY,
        SUNDAY;

        public static boolean isWeekend(String day) {
            return Try.of(() -> day.trim().toUpperCase()).map(Weekend::valueOf).fold(d -> true, false);
        }
   }
```   

# TO BE CONTINUED !!!




