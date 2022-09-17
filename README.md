## `fluenTry` is a java library that enables you to handle common `try/catch` procedures in a declarative way.

## Examples:
#### `Try#fold` - Returns the value based on if the main computation succeeded or failed
try division, if fails return 0, or multiply the result by 10

without `fluenTry`
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
int result = Try.evaluate(() -> 42 / 0).fold(num -> num * 10, 0); // the result is 10
```
