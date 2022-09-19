package org.ghurtchu.impl;

import org.ghurtchu.protocols.*;

import java.util.*;
import java.util.function.*;

/**
 * Try serves as an abstraction of try/catch procedures.
 * It has two subclasses: Success and Failure which represent successful and failed computations.
 * Success holds the value.
 * Failure holds the exception.
 * Try has a few methods for handling try/catch procedures in a declarative way.
 * First type of methods are "combinators" which enable you to compose Try computations (map, flatMap, filter etc..)
 * Second type of methods are "finalizers" which enable you to either extract the value from Try or register side-effecting functions (get, fold, endWith etc..)
 */
public abstract class Try<T> implements
        Extractable<T>,
        Mappable<T>,
        FlatMappable<T>,
        Filterable<T>,
        Foldable<T>,
        RunnableFinalizable,
        ConsumerFinalizable<T>,
        PureCatchableMappable<T>,
        ImpureCatchableMappable<T>,
        ToOptionalConvertable<T>,
        CatchableConsumable {

    private Try () {

    }

    /**
     * Try Constructor which takes the Supplier instance.
     * Returns either Success or Failure instance based on the evaluation result of the computation.
     * @param supplier a lazy computation which is not evaluated on the call site, instead it's evaluated here.
     */
    public static <T> Try<T> of(Supplier<? extends T> supplier) {
        try {
            return new Success<>(Optional.of(supplier.get()));
        } catch (Exception e) {
            return (Try<T>) new Failure(e);
        }
    }

    /**
     * Try Constructor which takes Runnable instance.
     * Returns either Success or Failure instance based on the evaluation result of the computation.
     * It's mainly used for running side-effecting procedures, the Success return value is not so useful, however Failure value might be useful.
     * @param runnable a lazy task which is not evaluated on the call site, instead it's evaluated here.
     */
    public static <T> Try<T> of(Runnable runnable) {
        try {
            runnable.run();
            return new Success<>(Optional.empty());
        } catch (Exception e) {
            return (Try<T>) new Failure(e);
        }
    }

    /**
     * Try Constructor which takes an argument for the Consumer and a Consumer instance itself.
     * Returns either Success or Failure instance based on computation.
     * It's mainly used for running side-effecting procedures, the Success return value is not so useful, however Failure value might be useful.
     * @param arg      an argument for Consumer.
     * @param consumer a lazy single-param function value which consumes argument for side effects. It's not evaluated on the call site, instead it's evaluated here.
     */
    public static <T> Try<T> of(T arg, Consumer<T> consumer) {
        try {
            consumer.accept(arg);
            return new Success<>(Optional.empty());
        } catch (Exception e) {
            return (Try<T>) new Failure(e);
        }
    }

    /**
     * Try Constructor which takes an argument for the Function and a Function instance itself.
     * Returns either Success or Failure instance based on computation.
     * @param arg      an argument for Function.
     * @param function a function value which consumes argument and may succeed or fail. It's not evaluated on the call site, instead it's evaluated here.
     */
    public static <T, V> Try<V> of(T arg, Function<T, ? extends V> function) {
        try {
            return new Success<>(Optional.of(function.apply(arg)));
        } catch (Exception e) {
            return (Try<V>) new Failure(e);
        }
    }

    /**
     * Try Constructor which takes two arguments for the BiFunction and a BiFunction instance itself.
     * Returns either Success or Failure instance based on computation.
     * @param arg1       the first argument for BiFunction.
     * @param arg2       the second argument for BiFunction.
     * @param biFunction a BiFunction value which consumes two arguments and may succeed or fail. It's not evaluated on the call site, instead it's evaluated here.
     */
    public static <T, U, V> Try<V> of (T arg1, U arg2, BiFunction<T, U, ? extends V> biFunction) {
        try {
            return new Success<>(Optional.of(biFunction.apply(arg1, arg2)));
        } catch (Exception e) {
            return (Try<V>) new Failure(e);
        }
    }

    /**
     * get can be used to retrieve the content of Try, but be wary! Failure case will throw an exception.
     */
    @Override
    public final T get() {
        if (this instanceof Success)
            return this.getValue();
        throw new NoSuchElementException(((Exception) this.getValue()).getMessage());
    }

    /**
     * map can be used to transform the result of the computation inside, it either succeeds with Success(transformed) or else returns Failure(exception).
     * @param mapper a function which transforms the inner content of Try and returns a new Try.
     */
    @Override
    public final <V> Try<V> map(Function<? super T, ? extends V> mapper) {
        if (this instanceof Success) {
            Success<T> success = (Success<T>) this;
            T value            = success.getValue();
            try {
                return new Success<>(Optional.of(mapper.apply(value)));
            } catch (Exception e) {
                return (Try<V>) new Failure(e);
            }
        } else {
            return (Try<V>) this;
        }
    }

    /**
     * flatMap is almost the same as map, although it can be used to chain Try computations one after another.
     * @param flatMapper a function which transforms the inner content of Try and returns a new Try.
     */
    @Override
    public final <V> Try<V> flatMap(Function<? super T, ? extends Try<V>> flatMapper) {
        if (this instanceof Success) {
            Success<T> success = (Success<T>) this;
            T value            = success.getValue();
            try {
                return flatMapper.apply(value);
            } catch (Exception e) {
                return (Try<V>) new Failure(e);
            }
        } else {
            return (Try<V>) this;
        }
    }

    /**
     * filter can be used to filter the content of Try, if successful it returns new Success, or else Failure(exception).
     * @param predicate a function which checks whether the content of Try conforms to a certain property.
     */
    @Override
    public final Try<T> filter(Predicate<? super T> predicate) {
        if (this instanceof Success) {
            Success<T> success = (Success<T>) this;
            T value            = success.getValue();
            try {
                return predicate.test(value)
                        ? new Success<>(Optional.of(this.getValue()))
                        : (Try<T>) new Failure(new NoSuchElementException("predicate does not hold for " + value));
            } catch (Exception e) {
                return (Try<T>) new Failure(e);
            }
        } else {
            return this;
        }
    }

    /**
     * Returns the value based on if the main computation succeeded or failed.
     * @param successMapper a function which can transform the successful value, or leave it as it is (Function.identity()).
     * @param defaultValue  a default value which will be returned if the computation fails.
     */
    @Override
    public final <V> V fold(Function<? super T, ? extends V> successMapper, V defaultValue) {
        if (this instanceof Success) {
            Success<T> success = (Success<T>) this;
            T value = success.getValue();
            if (value == null) {
                value = (T) new Object();
            }
            return successMapper.apply(value);
        } else {
            return defaultValue;
        }
    }

    /**
     * Runs the tasks based on if the main computation succeeded or failed.
     * @param onSuccess a runnable which will be executed in case of successful computation.
     * @param onFailure a runnable which will be executed in case of failed computation.
     */
    @Override
    public final void endWith(Runnable onSuccess, Runnable onFailure) {
        if (this instanceof Success) {
            onSuccess.run();
        } else {
            onFailure.run();
        }
    }

    /**
     * Runs the consumers based on if the main computation succeeded or failed.
     * @param successConsumer a consumer instance for success case.
     * @param failureConsumer a consumer instance for failed case.
     */
    @Override
    public final void endWith(Consumer<T> successConsumer, Consumer<Exception> failureConsumer) {
        if (this instanceof Success) {
            successConsumer.accept(this.getValue());
        } else {
            failureConsumer.accept((Exception) this.getValue());
        }
    }

    /**
     * Returns the default value if the evaluation procedure will catch the user-specified exception,
     * otherwise returns the successive value which can be mapped further into different values.
     * @param successMapper    a function for transforming success further, or else returning self by using Function.identity().
     * @param defaultValue     a default value to be returned in case of user-specified failure.
     * @param clientExceptions a user specified exception array some of which can be caught.
     */
    @SafeVarargs
    @Override
    public final <V> V ifThrowsThenGetDefaultOrElseMap(Function<? super T, ? extends V> successMapper, V defaultValue, Class<? extends Exception>... clientExceptions) {
        if (this instanceof Failure) {
            Failure failure = (Failure) this;
            Class<? extends Exception> currentException       = failure.getValue().getClass();
            List<Class<? extends Exception>> parentExceptions = TryUtils.getParentExceptions(failure.getValue());
            parentExceptions.add(currentException);
            if (Arrays.stream(clientExceptions).anyMatch(exception -> parentExceptions.stream().anyMatch(exception::equals))) {
                return defaultValue;
            } else {
                throw new UncaughtException();
            }
        } else {
            Success<T> success = (Success<T>) this;
            return successMapper.apply(success.getValue());
        }
    }

    /**
     * Returns the value if the evaluation was successful, else runs the consumer and returns null
     * consumer may do whatever client likes, log something, throw another exception (common)
     * @param consumer   an exception consumer for running the task
     * @param exceptions a user specified exception array some of which can be caught
     */
    @Override
    @SafeVarargs
    public final T ifThrowsThenRunTaskOrElseGet(Consumer<? super Exception> consumer, Class<? extends Exception>... exceptions) {
        if (this instanceof Failure) {
            Failure failure = (Failure) this;
            Class<? extends Exception> currentException       = failure.getValue().getClass();
            List<Class<? extends Exception>> parentExceptions = TryUtils.getParentExceptions(failure.getValue());
            parentExceptions.add(currentException);
            if (Arrays.stream(exceptions).anyMatch(exception -> parentExceptions.stream().anyMatch(exception::equals))) {
                consumer.accept(failure.getValue());
                return (T) new Object();
            } else {
                throw new UncaughtException();
            }
        } else {
            Success<T> success = (Success<T>) this;
            T value = success.getValue();
            if (value == null) {
                value = (T) new Object();
            }
            return value;
        }
    }

    /**
     * Converts the Try instance to the Optional instance in order to continue working with other code that uses Optional.
     */
    @Override
    public Optional<T> toOptional() {
        if (this instanceof Success) {
            Success<T> success = (Success<T>) this;
            T value            = success.getValue();
            if (value == null) {
                return Optional.empty();
            } else {
                return Optional.of(value);
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Runs the user specified task if the evaluation procedure will catch at least one of the user-specified exceptions otherwise it won't run any tasks.
     * @param errorConsumer  an exception consumer for running the task
     * @param clientExceptions a user specified exceptions which can be caught
     */
    @SafeVarargs
    @Override
    public final void ifThrowsThenRunTask(Consumer<? super Exception> errorConsumer, Class<? extends Exception>... clientExceptions) {
        if (this instanceof Failure) {
            Failure failure = (Failure) this;
            Class<? extends Exception> currentException       = failure.getValue().getClass();
            List<Class<? extends Exception>> parentExceptions = TryUtils.getParentExceptions(failure.getValue());
            parentExceptions.add(currentException);
            if (Arrays.stream(clientExceptions).anyMatch(exception -> parentExceptions.stream().anyMatch(exception::equals))) {
                errorConsumer.accept(failure.getValue());
            }
        }
    }

    /**
     * Returns true if the computation succeeded
     */
    public boolean isSuccess() {
        return this instanceof Success;
    }

    /**
     * Returns true of the computation failed
     */
    public boolean isFailure() {
        return !isSuccess();
    }

    /**
     * Returns a new successful value wrapped in Try if the preceding computations fail
     */
    public Try<T> orElse(T value) {
        return new Success<>(Optional.of(value));
    }

    /**
     * abstract method which must be implemented by the children of org.ghurtchu.impl.Try
     */
    protected abstract T getValue();

    /**
     * A success case which encapsulates the successful computation
     */
    private static final class Success<T> extends Try<T> {

        private final Optional<T> value;

        public Success(Optional<T> value) {
            this.value = value;
        }

        @Override
        protected T getValue() {
            try {
                return value.get();
            } catch (NoSuchElementException nse) {
                return null;
            }
        }

        @Override
        public String toString() {
            return "Success{" +
                    "value=" + value +
                    '}';
        }
    }

    /**
     * A failed case which encapsulates the failed computation
     */
    private static final class Failure extends Try<Exception> {

        private final Exception exception;

        public Failure(Exception exception) {
            this.exception = exception;
        }

        @Override
        protected Exception getValue() {
            return exception;
        }

        @Override
        public String toString() {
            return "Failure{" +
                    "exception=" + exception +
                    '}';
        }
    }

    /**
     * A special exception for indicating that user could not catch it
     */
    public static final class UncaughtException extends RuntimeException {

        @Override
        public String getMessage() {
            return "UncaughtException";
        }
    }

    /**
     * The utility class for working with secondary matters.
     */
    private static class TryUtils {

        /**
         * returns the list of parent exceptions for a given exception
         * @param exception an exception
         */
        public static List<Class<? extends Exception>> getParentExceptions(Exception exception) {
            List<Class<? extends Exception>> superExceptions = new ArrayList<>();
            Class<?> superExc                                = exception.getClass().getSuperclass();
            while (true) {
                if (superExc == null) {
                    break;
                } else {
                    superExceptions.add((Class<? extends Exception>) superExc);
                    superExc = superExc.getSuperclass();
                }
            }
            return superExceptions;
        }

    }


}
