## `fluenTry` is a java library that enables you to handle common `try/catch` procedures in a declarative way.

## Examples:
#### `Try#fold` - Returns the value based on if the main computation succeeded or failed
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
int result = Try.evaluate(() -> 42 / 0).fold(num -> num * 10, 0); // the result is 10
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
