package Listeners;

import io.qameta.allure.Step;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.TestWatcher;

@ExtendWith(AllureJunit5.class)
public class AllureTestWatcher implements TestWatcher {

    @Override
    public void testSuccessful(org.junit.jupiter.api.extension.ExtensionContext context) {
        log("Test passed: " + context.getDisplayName());
    }

    @Override
    public void testFailed(org.junit.jupiter.api.extension.ExtensionContext context, Throwable cause) {
        log("Test failed: " + context.getDisplayName());
    }

    @Override
    public void testAborted(org.junit.jupiter.api.extension.ExtensionContext context, Throwable cause) {
        log("Test aborted: " + context.getDisplayName());
    }

    @Step("Log action: {action}")
    private void log(String action) {
        System.out.println(action);
    }
}