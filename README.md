#### An attempt to port `scala.util.Try` to Java.

The main class is called `Try`.

`Try` has a few overloaded static constructors for instantiating lazy computations which is called `Try.of()`.

`Try` instance may be constructed via one of these five arguments:
 - `Runnable`
 - `Consumer<T> with arg`,
 - `Supplier<T>`,
 - `Function<T, V> with arg`,
 - `BiFunction<T, U, V> with two args`

`Try` has two sub-classes: `Success<T>` which holds the successful value and `Failure` which holds the exception.

`Try` may be used in different ways, so without further ado let's see the examples below:

problem description: try getting the element from the list, if it fails return stringified 0, or else return the stringified square of the success value.

solution with pure java:
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

solution with `fluenTry`:
```java
   public static String getAndThenDoubleAndThenStringify(List<Integer> list, int index) {
       return Try.of(list, index, List::get).map(n -> n * 2).fold(String::valueOf, "0");
   }
```

Here is a Failed computation which returns `Failure(ArithmeticException)` instead of blowing up the calling stack by throwing an exception and succeeds with pure value with the help of `fold` combinator:
```java
   double result = Try.of(() -> Math.random() * 2 * 5)
           .flatMap(n -> Try.of(() -> n + 1))
           .flatMap(n -> Try.of(() -> 55 / 0)) // This is where the `Success` turns into `Failure`
           .map(Math::sqrt)
           .getOrElse(0.0); // but with the help of `getOrElse` we turn that into pure value
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
