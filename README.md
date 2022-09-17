## `fluenTry` is a java library that enables you to handle common `try/catch` procedures in a declarative way.

### The main data structure is called `Try<T>`.
### `Try<T>` is a generic data structure for encapsulating the common `try/catch` procedures.
#### It is completely polymorphic in success and error channels and enables to perform user-specified operations based on the result of the evaluation of the main lazy computation which is not evaluated at call site, instead it's evaluated inside the instance of `Try<T>`.

#### `Try<T>` has two cases: `Success<T>` and `Failure<Exception>`, `Success<T>` represents the successful computation, `Failure<Exception>` represents the failed computation respectively.

#### `Try<T>` has a few methods for handling common `try/catch` patterns more fluently, with more concise and elegant code.

### Typical usage patterns can be the following:

#### `fold` - handling both success and failed cases and returning the useful value, successful value can be mapped further with the help of Function<T, V> interface.

#### `finalizeWith` - registers the side effects for both success and failure cases, runs either one of them based on the evaluation of computation.

#### `ifThrowsThenGetDefaultOrElseMap` - if it catches one of the user-specified exceptions it can return a default value / throw any other exception, or else returns the successful value.

#### `ifThrowsCatchAndThenRun` - if it catches one of the user-specified exceptions it can execute any side effects provided by the client or else do nothing.
