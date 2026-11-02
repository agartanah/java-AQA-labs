import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestPlanScanner {
    public static List<TestStep> buildPlan(Class<?> clazz) {
        List<TestStep> testSteps = new ArrayList<>();
        Method[] methods = Checklist.class.getDeclaredMethods();

        for (var method : methods) {
            if (method.isAnnotationPresent(TestId.class)) {
                TestId testId = method.getAnnotation(TestId.class);

                TestStep testStep = new TestStep(testId.value(), method.getName(), testId.order());
                testSteps.add(testStep);
            }
        }

        testSteps.sort(Comparator.comparingInt(TestStep::order));

        return testSteps;
    }
}
