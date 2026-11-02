import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<TestStep> testSteps = TestPlanScanner.buildPlan(Checklist.class);

        if (testSteps.isEmpty()) {
            System.out.println("Кейсов нет");
        } else {
            System.out.println("План прогона для " + Checklist.class.getName() + ":");
            testSteps.forEach((testStep -> System.out.println("\t" + testStep.order() + ". " + testStep.id() + ": " + testStep.methodName())));
        }
    }
}
