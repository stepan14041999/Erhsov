package first;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MainAppTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "10 2", "10 3", "10 4", "10 5", "10 10",
            "100 2", "100 3", "100 4", "100 5", "100 10",
            "1000 2", "1000 3", "1000 4", "1000 5", "1000 10",
            "100000 2", "100000 3", "100000 4", "100000 5", "100000 10"
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
