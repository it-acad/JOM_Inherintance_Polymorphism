package jom.com.softserve.s2.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    final private static String PACKAGE = "jom.com.softserve.s2.task1.";

    @DisplayName("Check if classes are present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClasses(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertNotNull(clazz, "Class " + cl + " should exist");
            assertEquals(cl, clazz.getSimpleName(), "Class name should match");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(Arguments.of("Person"), Arguments.of("Child"), Arguments.of("Adult"));
    }

    @DisplayName("Check if child class extends parent class")
    @ParameterizedTest
    @MethodSource("listOfChildren")
    void extendsTypeClass(String parent, String child) {
        try {
            final Class<?> parentClazz = Class.forName(PACKAGE + parent);
            final Class<?> childClazz = Class.forName(PACKAGE + child);
            assertTrue(parentClazz.isAssignableFrom(childClazz), child + " should extend " + parent);
        } catch (ClassNotFoundException e) {
            fail("Class " + child + " or " + parent + " does not exist");
        }
    }

    private static Stream<Arguments> listOfChildren() {
        return Stream.of(Arguments.of("Person", "Child"), Arguments.of("Person", "Adult"));
    }

    @DisplayName("Check if class has declared fields")
    @ParameterizedTest
    @MethodSource("listOfClassesAndFields")
    void hasTypeDeclaredField(String clas, String field) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            assertNotNull(clazz.getDeclaredField(field), "Field " + field + " should exist in " + clas);
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        } catch (NoSuchFieldException e) {
            fail("Field " + field + " does not exist in " + clas);
        }
    }

    private static Stream<Arguments> listOfClassesAndFields() {
        return Stream.of(
                Arguments.of("Person", "age"),
                Arguments.of("Person", "healthInfo"),
                Arguments.of("Person", "name"),
                Arguments.of("Adult", "passportNumber"),
                Arguments.of("Child", "childIDNumber")
        );
    }

    @DisplayName("Check if fields are of type String")
    @ParameterizedTest
    @MethodSource("listOfStringFields")
    void hasFieldTypeString(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field field = clazz.getDeclaredField(fieldName);
            assertEquals(String.class, field.getType(), "Field " + fieldName + " should be of type String");
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        } catch (NoSuchFieldException e) {
            fail("Field " + fieldName + " does not exist in " + clas);
        }
    }

    private static Stream<Arguments> listOfStringFields() {
        return Stream.of(
                Arguments.of("Person", "healthInfo"),
                Arguments.of("Person", "name"),
                Arguments.of("Adult", "passportNumber"),
                Arguments.of("Child", "childIDNumber")
        );
    }

    @DisplayName("Check if 'age' field in Person class is of type int")
    @Test
    void hasFieldTypeInt() {
        try {
            Class<?> clazz = Class.forName(PACKAGE + "Person");
            Field field = clazz.getDeclaredField("age");
            assertEquals(int.class, field.getType(), "Field 'age' should be of type int");
        } catch (ClassNotFoundException e) {
            fail("Class Person does not exist");
        } catch (NoSuchFieldException e) {
            fail("Field 'age' does not exist in Person");
        }
    }

    @DisplayName("Check if fields are private")
    @ParameterizedTest
    @MethodSource("listPrivateFields")
    void isFieldPrivate(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()), "Field " + fieldName + " should be private");
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        } catch (NoSuchFieldException e) {
            fail("Field " + fieldName + " does not exist in " + clas);
        }
    }

    private static Stream<Arguments> listPrivateFields() {
        return Stream.of(
                Arguments.of("Adult", "passportNumber"),
                Arguments.of("Child", "childIDNumber")
        );
    }

    @DisplayName("Check if fields are package-private")
    @ParameterizedTest
    @MethodSource("listPackagePrivateFields")
    void isFieldPackagePrivate(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field field = clazz.getDeclaredField(fieldName);

            boolean isPackagePrivate =
                    !Modifier.isPublic(field.getModifiers()) &&
                            !Modifier.isProtected(field.getModifiers()) &&
                            !Modifier.isPrivate(field.getModifiers());

            assertTrue(isPackagePrivate, "Field " + fieldName + " should be package-private");
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        } catch (NoSuchFieldException e) {
            fail("Field " + fieldName + " does not exist in " + clas);
        }
    }

    private static Stream<Arguments> listPackagePrivateFields() {
        return Stream.of(
                Arguments.of("Person", "age"),
                Arguments.of("Person", "healthInfo"),
                Arguments.of("Person", "name")
        );
    }

    @DisplayName("Check if Person class has 'getHealthStatus' method")
    @Test
    void hasGetHealthStatusMethod() {
        try {
            Method method = Class.forName(PACKAGE + "Person").getDeclaredMethod("getHealthStatus");
            assertNotNull(method, "'getHealthStatus' method should exist");
        } catch (ClassNotFoundException e) {
            fail("Class Person does not exist");
        } catch (NoSuchMethodException e) {
            fail("'getHealthStatus' method does not exist in Person");
        }
    }

    @DisplayName("Check if 'getHealthStatus' method returns String")
    @Test
    void hasMethodReturnType() {
        try {
            Method method = Class.forName(PACKAGE + "Person").getDeclaredMethod("getHealthStatus");
            assertEquals(String.class, method.getReturnType(), "'getHealthStatus' should return String");
        } catch (ClassNotFoundException e) {
            fail("Class Person does not exist");
        } catch (NoSuchMethodException e) {
            fail("'getHealthStatus' method does not exist in Person");
        }
    }

    @DisplayName("Check declared constructors")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void hasTypeDeclaredConstructor(String clas, String[] parameterTypesName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            boolean isConstructorFound = Arrays.stream(constructors)
                    .anyMatch(c -> Arrays.equals(
                            Arrays.stream(c.getParameterTypes()).map(Class::getSimpleName).toArray(),
                            parameterTypesName
                    ));
            assertTrue(isConstructorFound, "Constructor with specified parameters should exist in " + clas);
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(
                Arguments.of("Person", new String[]{"int", "String", "String"}),
                Arguments.of("Adult", new String[]{"int", "String", "String", "String"}),
                Arguments.of("Child", new String[]{"int", "String", "String", "String"})
        );
    }

}