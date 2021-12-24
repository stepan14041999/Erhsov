package second;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MainAppTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "serial 10", "serial 100", "serial 1000", "serial 10000", "serial 100000",
            "parallel 10 2", "parallel 10 3", "parallel 10 5", "parallel 10 10",
            "parallel 100 2", "parallel 100 3", "parallel 100 5", "parallel 100 10",
            "parallel 1000 2", "parallel 1000 3", "parallel 1000 5", "parallel 1000 10",
            "parallel 10000 2", "parallel 10000 3", "parallel 10000 5", "parallel 10000 10",
            "parallel 100000 2", "parallel 100000 3", "parallel 100000 5", "parallel 100000 10",
    })
    void benchmarks(String value) {
        String[] values = value.split(" ");
        long startTime = System.nanoTime();
        System.out.println();
        MainApp.main(values);
        long delta = System.nanoTime() - startTime;
        System.out.println("Заняло наносекунд: " + delta);
        System.out.println("Заняло миллисекунд: " + delta / 1000000);
    }

}
