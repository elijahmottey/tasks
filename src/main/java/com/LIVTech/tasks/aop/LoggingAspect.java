package com.LIVTech.tasks.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging the execution of Spring components like controllers and services.
 * This aspect provides detailed logs for method entry, exit, execution time,
 * and exceptions, which is invaluable for debugging and monitoring application behavior.
 *
 * @author Elijah
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * Logger instance for this aspect.
     * It's a standard practice to use a static final logger for performance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Defines a reusable pointcut that targets all public methods within any class
     * in the com.LIVTech.tasks.service package and its subpackages.
     * This allows us to specifically apply logging to the service layer.
     */
    @Pointcut("execution(public * com.LIVTech.tasks.service..*.*(..))")
    public void serviceLayerPointcut() {
        // This method is empty because it's just a signature for the pointcut.
    }

    /**
     * Defines a reusable pointcut that targets all public methods within any class
     * in the com.LIVTech.tasks.controller package and its subpackages.
     * This allows us to specifically apply logging to the controller layer.
     */
    @Pointcut("execution(public * com.LIVTech.tasks.controller..*.*(..))")
    public void controllerLayerPointcut() {
        // This method is empty because it's just a signature for the pointcut.
    }

    /**
     * An @Around advice that wraps the execution of targeted methods (controllers and services).
     * It logs method entry, arguments, exit, return value, execution time, and any exceptions.
     *
     * @param joinPoint The join point gives us access to the method being called.
     * @return The result of the original method call.
     * @throws Throwable if the advised method throws an exception, it is re-thrown.
     */
    @Around("controllerLayerPointcut() || serviceLayerPointcut()")
    public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log method entry: Class, method name, and arguments.
        LOGGER.info("Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

        // Record start time to calculate execution duration.
        long startTime = System.currentTimeMillis();

        try {
            // Execute the original method.
            Object result = joinPoint.proceed();

            // Record end time and calculate duration.
            long timeTaken = System.currentTimeMillis() - startTime;

            // Log method exit: Class, method name, return value, and execution time.
            LOGGER.info("Exit: {}.{}() with result = {}. Execution time: {} ms",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    result,
                    timeTaken);

            // Return the result of the original method.
            return result;
        } catch (Exception e) {
            // Log the exception if the method throws one.
            LOGGER.error("Exception in {}.{}(): {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    e.getMessage());

            // Re-throw the exception so that it can be handled by global exception handlers.
            throw e;
        }
    }
}