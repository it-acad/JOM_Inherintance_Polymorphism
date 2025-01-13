package jom.com.softserve.s2.task5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task5Test {

    private static final String PACKAGE = "jom.com.softserve.s2.task5.";

    @DisplayName("✅ Check if classes are present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypePresent(String cl) {
        try {
            assertNotNull(Class.forName(PACKAGE + cl), "Class " + cl + " should exist");
            assertEquals(cl, Class.forName(PACKAGE + cl).getSimpleName(), "Class name should match");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(Arguments.of("MyUtils"), Arguments.of("Rectangle"), Arguments.of("Square"));
    }

    @DisplayName("✅ Check if types are classes (not abstract or interfaces)")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertTrue(!Modifier.isAbstract(clazz.getModifiers()) && !Modifier.isInterface(clazz.getModifiers()),
                    cl + " should be a class, not abstract or interface");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    @DisplayName("✅ Check if constructors are public")
    @ParameterizedTest
    @MethodSource("listClassesAndConstructor")
    void isConstructorPublic(String clas, String[] parameterTypesName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
            boolean isConstructorCorrect = false;
            for (Constructor<?> constructor : declaredConstructors) {
                String[] parameterTypes = Arrays.stream(constructor.getGenericParameterTypes())
                        .map(type -> type.getTypeName().substring(type.getTypeName().lastIndexOf('.') + 1))
                        .toArray(String[]::new);

                if (Arrays.equals(parameterTypes, parameterTypesName)) {
                    isConstructorCorrect = true;
                    assertTrue(Modifier.isPublic(constructor.getModifiers()), "Constructor must be public");
                    break;
                }
            }
            assertTrue(isConstructorCorrect, "No matching constructor found for " + clas);
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(
                Arguments.of("Rectangle", new String[]{"double", "double"}),
                Arguments.of("Square", new String[]{"double"})
        );
    }

    @DisplayName("✅ Check if classes contain specific methods")
    @ParameterizedTest
    @MethodSource("listClassesAndMethods")
    void isMethodPresent(String cl, String m) {
        try {
            Method[] methods = Class.forName(PACKAGE + cl).getDeclaredMethods();
            boolean isMethod = Arrays.stream(methods).anyMatch(method -> method.getName().equals(m));
            assertTrue(isMethod, "Method " + m + " not found in class " + cl);
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listClassesAndMethods() {
        return Stream.of(
                Arguments.of("MyUtils", "sumPerimeter"),
                Arguments.of("Rectangle", "getPerimeter"),
                Arguments.of("Square", "getPerimeter")
        );
    }

    @DisplayName("✅ Check if fields in Rectangle are private")
    @ParameterizedTest
    @MethodSource("listPrivateFields")
    void isFieldPrivate(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()), "Field " + fieldName + " should be private");
        } catch (Exception e) {
            fail("Field " + fieldName + " does not exist in class " + clas);
        }
    }

    private static Stream<Arguments> listPrivateFields() {
        return Stream.of(
                Arguments.of("Rectangle", "height"),
                Arguments.of("Rectangle", "width")
        );
    }

//    @DisplayName("✅ Check Rectangle perimeter calculation")
//    @Test
//    void checkRectanglePerimeter() {
//        Rectangle rectangle = new Rectangle(2.0, 3.0);
//        assertEquals(10.0, rectangle.getPerimeter(), 1e-8, "Rectangle perimeter should be 10.0");
//    }
//
//    @DisplayName("✅ Check Square perimeter calculation")
//    @Test
//    void checkSquarePerimeter() {
//        Square square = new Square(2.0);
//        assertEquals(8.0, square.getPerimeter(), 1e-8, "Square perimeter should be 8.0");
//    }
//
//    @DisplayName("✅ Check sumPerimeter method with unique figures")
//    @Test
//    void checkUniqueAll() {
//        List<Rectangle> figures = List.of(new Square(4.0), new Square(5.0), new Rectangle(2.0, 3.0));
//        assertEquals(46.0, new MyUtils().sumPerimeter(figures), 1e-8, "Sum of perimeters should be 46.0");
//    }
//
//    @DisplayName("✅ Check sumPerimeter with duplicate Squares")
//    @Test
//    void checkDuplicateSquare() {
//        List<Square> figures = List.of(new Square(4.0), new Square(4.0));
//        assertEquals(32.0, new MyUtils().sumPerimeter(figures), 1e-8, "Sum of perimeters should be 32.0");
//    }

    @DisplayName("✅ Check sumPerimeter with empty list")
    @Test
    void checkEmptyList() {
        List<Rectangle> figures = new ArrayList<>();
        assertEquals(0.0, new MyUtils().sumPerimeter(figures), 1e-8, "Sum should be 0.0 for empty list");
    }

    @DisplayName("✅ Check sumPerimeter with null content in list")
    @Test
    void checkNullContent() {
        List<Rectangle> figures = new ArrayList<>();
        figures.add(null);
        assertEquals(0.0, new MyUtils().sumPerimeter(figures), 1e-8, "Sum should be 0.0 for null content");
    }
}
