package jom.com.softserve.s2.task4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task4Test {

    private static final  String PACKAGE = "jom.com.softserve.s2.task4.";

    @DisplayName("Check that Classes are present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypePresent(String cl) {
        try {
            assertNotNull(Class.forName(PACKAGE + cl));
            assertEquals(cl, Class.forName(PACKAGE + cl).getSimpleName());
        } catch (ClassNotFoundException e) {
            fail("There is no class " + cl);
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(Arguments.of("MyUtils"), Arguments.of("Manager"), Arguments.of("Employee"));
    }

    @DisplayName("Check that classes are concrete")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()));
        } catch (ClassNotFoundException e) {
            fail("There is no " + cl + " class");
        }
    }

    @DisplayName("Check that constructors are public")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void isConstructorPublic(String clas, String[] parameterTypesName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            boolean isConstructorCorrect = false;

            for (Constructor<?> constructor : constructors) {
                Type[] types = constructor.getGenericParameterTypes();
                String[] parameterTypes = Arrays.stream(types)
                        .map(type -> type.getTypeName().split("\\.")[type.getTypeName().split("\\.").length - 1])
                        .toArray(String[]::new);

                if (Arrays.equals(parameterTypes, parameterTypesName)) {
                    isConstructorCorrect = true;
                    assertTrue(Modifier.isPublic(constructor.getModifiers()));
                    break;
                }
            }
            assertTrue(isConstructorCorrect, "No matching constructor found");
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " not found");
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(
                Arguments.of("Employee", new String[]{"String", "int", "BigDecimal"}),
                Arguments.of("Manager", new String[]{"String", "int", "BigDecimal", "double"})
        );
    }

    @DisplayName("Check that classes contain specific methods")
    @ParameterizedTest
    @MethodSource("listClassesAndMethods")
    void isMethodPresent(String cl, String methodName) {
        try {
            Method[] methods = Class.forName(PACKAGE + cl).getDeclaredMethods();
            assertTrue(Arrays.stream(methods).anyMatch(m -> m.getName().equals(methodName)),
                    "Method " + methodName + " not found in class " + cl);
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " not found");
        }
    }

    private static Stream<Arguments> listClassesAndMethods() {
        return Stream.of(
                Arguments.of("MyUtils", "largestEmployees"),
                Arguments.of("Employee", "getName"),
                Arguments.of("Employee", "getExperience"),
                Arguments.of("Employee", "getBasePayment"),
                Arguments.of("Manager", "getCoefficient")
        );
    }

    @DisplayName("Check that Manager extends Employee")
    @Test
    void managerExtendsEmployee() {
        try {
            Class<?> employeeClass = Class.forName(PACKAGE + "Employee");
            Class<?> managerClass = Class.forName(PACKAGE + "Manager");
            assertTrue(employeeClass.isAssignableFrom(managerClass), "Manager does not extend Employee");
        } catch (ClassNotFoundException e) {
            fail("Class not found");
        }
    }

    @DisplayName("Check that fields are private")
    @ParameterizedTest
    @MethodSource("listPrivateFields")
    void isFieldPrivate(String clas, String fieldName) {
        try {
            Field field = Class.forName(PACKAGE + clas).getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()), "Field " + fieldName + " should be private");
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field or class not found");
        }
    }

    private static Stream<Arguments> listPrivateFields() {
        return Stream.of(
                Arguments.of("Employee", "name"),
                Arguments.of("Employee", "experience"),
                Arguments.of("Employee", "basePayment"),
                Arguments.of("Manager", "coefficient")
        );
    }

//    @DisplayName("Check Manager's payment calculation with coefficient")
//    @Test
//    void checkManagerPaymentCalculation() {
//        Manager manager = new Manager("Petro", 9, new BigDecimal("3000.0"), 1.5);
//        BigDecimal expectedPayment = new BigDecimal("4500.0");
//        assertEquals(expectedPayment.stripTrailingZeros(), manager.getPayment().stripTrailingZeros(), "Incorrect Manager payment calculation");
//    }

//    @DisplayName("Check that the original list remains unchanged")
//    @Test
//    void checkOriginalListUnchanged() {
//        List<Employee> originList = List.of(
//                new Employee("Ivan", 10, new BigDecimal("3000.0")),
//                new Manager("Petro", 9, new BigDecimal("3000.0"), 1.5)
//        );
//        List<Employee> copyList = new ArrayList<>(originList);
//
//        new MyUtils().largestEmployees(copyList);
//
//        assertEquals(originList, copyList, "Original list should remain unchanged");
//    }
//
//    @DisplayName("Check null handling in employee list")
//    @Test
//    void checkNullInList() {
//        List<Employee> employees = Arrays.asList(
//                new Employee("Ivan", 10, new BigDecimal("3000.0")),
//                null,
//                new Manager("Petro", 9, new BigDecimal("3000.0"), 1.5)
//        );
//        List<Employee> result = new MyUtils().largestEmployees(employees);
//        assertFalse(result.contains(null), "Result should not contain null");
//    }

    @DisplayName("Check empty list handling")
    @Test
    void checkEmptyList() {
        List<Employee> employees = new ArrayList<>();
        List<Employee> result = new MyUtils().largestEmployees(employees);
        assertTrue(result.isEmpty(), "Result should be empty for empty input");
    }
}
