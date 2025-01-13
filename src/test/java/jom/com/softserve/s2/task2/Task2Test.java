package jom.com.softserve.s2.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class Task2Test {

    private static final String PACKAGE = "jom.com.softserve.s2.task2.";

    @DisplayName("Check if classes are present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypePresent(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertNotNull(clazz, "Class " + cl + " should exist");
            assertEquals(cl, clazz.getSimpleName(), "Class name should match");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(
                Arguments.of("MyUtils"),
                Arguments.of("DrinkRecipe"),
                Arguments.of("DrinkPreparation"),
                Arguments.of("Rating"),
                Arguments.of("Coffee"),
                Arguments.of("Espresso"),
                Arguments.of("Cappuccino")
        );
    }

    @DisplayName("Check if class is an interface")
    @ParameterizedTest
    @MethodSource("listOfInterfaces")
    void isTypeInterface(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertTrue(clazz.isInterface(), "Class " + cl + " should be an interface");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listOfInterfaces() {
        return Stream.of(
                Arguments.of("DrinkRecipe"),
                Arguments.of("DrinkPreparation"),
                Arguments.of("Rating")
        );
    }

    @DisplayName("Check if class is not abstract or interface")
    @ParameterizedTest
    @MethodSource("listOfConcreteClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertFalse(Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface(),
                    "Class " + cl + " should be a concrete class");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listOfConcreteClasses() {
        return Stream.of(
                Arguments.of("MyUtils"),
                Arguments.of("Coffee"),
                Arguments.of("Espresso"),
                Arguments.of("Cappuccino")
        );
    }

    @DisplayName("Check if constructor is public")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void isConstructorPublic(String clas, String[] parameterTypesName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            boolean isConstructorCorrect = Arrays.stream(clazz.getDeclaredConstructors())
                    .anyMatch(constructor -> Arrays.equals(
                            Arrays.stream(constructor.getParameterTypes()).map(Class::getSimpleName).toArray(),
                            parameterTypesName
                    ) && Modifier.isPublic(constructor.getModifiers()));

            assertTrue(isConstructorCorrect, "Class " + clas + " should have a public constructor with specified parameters");
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(
                Arguments.of("Coffee", new String[]{"String", "int"}),
                Arguments.of("Espresso", new String[]{"String", "int"}),
                Arguments.of("Cappuccino", new String[]{"String", "int"})
        );
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
        return Stream.of(
                Arguments.of("Coffee", "Espresso"),
                Arguments.of("Coffee", "Cappuccino")
        );
    }

    @DisplayName("Check if class implements interface")
    @ParameterizedTest
    @MethodSource("listOfImplementedInterfaces")
    void implementsInterface(String interfaceName, String className) {
        try {
            Class<?> interfaceClazz = Class.forName(PACKAGE + interfaceName);
            Class<?> classClazz = Class.forName(PACKAGE + className);
            assertTrue(interfaceClazz.isAssignableFrom(classClazz), className + " should implement " + interfaceName);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " or " + interfaceName + " does not exist");
        }
    }

    private static Stream<Arguments> listOfImplementedInterfaces() {
        return Stream.of(
                Arguments.of("DrinkRecipe", "Coffee"),
                Arguments.of("DrinkPreparation", "Coffee"),
                Arguments.of("Rating", "Coffee")
        );
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
                Arguments.of("Coffee", "name"),
                Arguments.of("Coffee", "rating"),
                Arguments.of("Coffee", "ingredients")
        );
    }

//    @DisplayName("Check Coffee makeDrink() method")
//    @Test
//    void checkCoffeeMakeDrink() {
//        Map<String, Integer> expected = Map.of("Water", 100, "Arabica", 20);
//        Map<String, Integer> actual = new Coffee("Coffee", 1).makeDrink();
//        assertEquals(expected, actual, "Coffee makeDrink() should return correct ingredients");
//    }
//
//    @DisplayName("Check Espresso makeDrink() method")
//    @Test
//    void checkEspressoMakeDrink() {
//        Map<String, Integer> expected = Map.of("Water", 50, "Arabica", 20);
//        Map<String, Integer> actual = new Espresso("Espresso", 1).makeDrink();
//        assertEquals(expected, actual, "Espresso makeDrink() should return correct ingredients");
//    }
//
//    @DisplayName("Check Cappuccino makeDrink() method")
//    @Test
//    void checkCappuccinoMakeDrink() {
//        Map<String, Integer> expected = Map.of("Water", 100, "Arabica", 20, "Milk", 50);
//        Map<String, Integer> actual = new Cappuccino("Cappuccino", 1).makeDrink();
//        assertEquals(expected, actual, "Cappuccino makeDrink() should return correct ingredients");
//    }
}
