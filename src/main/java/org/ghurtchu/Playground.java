package org.ghurtchu;

import org.ghurtchu.impl.Try;

public class Playground {

    static class JsonParser<T> {
        public String toJson(T object) {
            return object.toString();
        }
    }

    static class Data {
        int x, y;

        public Data(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {

        Try.run(() -> new JsonParser<Data>())
                .flatMap(jsonParser -> Try.run(() -> jsonParser.toJson(new Data(1, 2))))
                .filter(json -> json.contains("yle var"))
                .endWithTasks(System.out::println, System.out::println);


    }
}
