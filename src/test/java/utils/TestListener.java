package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG listener that automatically logs every test outcome (start, pass,
 * fail, skip)
 * to the log file. This means assertion failures are captured even if a test
 * method
 * has no try-catch block.
 *
 * Registered globally on BaseClass via @Listeners(TestListener.class).
 */
public class TestListener implements ITestListener {

    // One logger per listener instance — the class name appears in log output
    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        log.info("▶ STARTED  : {}.{}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("✔ PASSED   : {}.{} ({}ms)",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                result.getEndMillis() - result.getStartMillis());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable cause = result.getThrowable();

        // Distinguish between assertion failures and unexpected exceptions
        if (cause instanceof AssertionError) {
            log.error("✘ ASSERTION FAILED : {}.{} — {}",
                    result.getTestClass().getRealClass().getSimpleName(),
                    result.getMethod().getMethodName(),
                    cause.getMessage());
        } else {
            log.error("✘ FAILED (exception): {}.{} — {}",
                    result.getTestClass().getRealClass().getSimpleName(),
                    result.getMethod().getMethodName(),
                    cause != null ? cause.getMessage() : "unknown error",
                    cause);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⚠ SKIPPED  : {}.{} — {}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "no reason given");
    }

    @Override
    public void onStart(ITestContext context) {
        log.info("══════════════════════════════════════════════════════");
        log.info("TEST SUITE STARTED : {}", context.getName());
        log.info("══════════════════════════════════════════════════════");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("══════════════════════════════════════════════════════");
        log.info("TEST SUITE FINISHED : {} | Passed: {} | Failed: {} | Skipped: {}",
                context.getName(),
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
        log.info("══════════════════════════════════════════════════════");
    }
}
