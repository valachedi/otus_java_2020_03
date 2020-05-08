package hw03;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.UnsupportedOperationException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;

public class TestRunner {
    private static final int INITIAL_METHODLIST_SIZE = 10;

    public static void runByClassName(String className) throws Exception {
        Class<?> testClass = Class.forName(className);
        Constructor<?> constructor = testClass.getConstructor();
        ArrayList<Method> methodsOfBefore = new ArrayList<Method>(INITIAL_METHODLIST_SIZE);
        ArrayList<Method> methodsOfTest = new ArrayList<Method>(INITIAL_METHODLIST_SIZE);
        ArrayList<Method> methodsOfAfter = new ArrayList<Method>(INITIAL_METHODLIST_SIZE);

        for(Method method : testClass.getMethods()) {
            if(method.getAnnotationsByType(Before.class).length != 0) {
                methodsOfBefore.add(method);
            }

            if(method.getAnnotationsByType(Test.class).length != 0) {
                methodsOfTest.add(method);
            }

            if(method.getAnnotationsByType(After.class).length != 0) {
                methodsOfAfter.add(method);
            }
        }

        ArrayList failedMethodNames = new ArrayList(INITIAL_METHODLIST_SIZE);

        for(Method testMethod : methodsOfTest) {
            try {
                var object = constructor.newInstance();

                for(Method beforeMethod : methodsOfBefore) {
                    beforeMethod.invoke(object);
                }

                System.out.println("\n * Test / " + testMethod.getName() + " *");
                testMethod.invoke(object);

                for(Method afterMethod : methodsOfAfter) {
                    afterMethod.invoke(object);
                }
            } catch (Exception e) {
                System.out.println(" * (!) Test failed *");
                failedMethodNames.add(testMethod.getName());
                continue;
            }
        }

        System.out.println("\nTotal tests: " + methodsOfTest.size());
        System.out.println("Failed tests: " + failedMethodNames.size());

        if(failedMethodNames.size() > 0) {
            System.out.println("Failed methods: " + String.join(", ", failedMethodNames));
        }
    }
}
