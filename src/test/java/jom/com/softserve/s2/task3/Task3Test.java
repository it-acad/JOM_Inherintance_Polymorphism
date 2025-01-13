package jom.com.softserve.s2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Task3Test {

    final private static String PACKAGE = "jom.com.softserve.s2.task3.";

    @DisplayName("Check if classes are present")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypePresent(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertNotNull(clazz, "Class " + cl + " should exist");
            assertEquals(clazz.getSimpleName(), cl, "Class name should match");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
    }

    private static Stream<Arguments> listOfClasses() {
        return Stream.of(
                Arguments.of("MyUtils"),
                Arguments.of("Person"),
                Arguments.of("Student"),
                Arguments.of("Worker")
        );
    }

    @DisplayName("Check if class is concrete (not abstract/interface)")
    @ParameterizedTest
    @MethodSource("listOfClasses")
    void isTypeClass(String cl) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + cl);
            assertFalse(Modifier.isAbstract(clazz.getModifiers()) || clazz.isInterface(),
                    "Class " + cl + " should be a concrete class");
        } catch (ClassNotFoundException e) {
            fail("Class " + cl + " does not exist");
        }
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

            assertTrue(isConstructorCorrect, "Class " + clas + " should have a public constructor");
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        }
    }

    private static Stream<Arguments> listClassesAndConstructor() {
        return Stream.of(
                Arguments.of("Person", new String[]{"String"}),
                Arguments.of("Student", new String[]{"String", "String", "int"}),
                Arguments.of("Worker", new String[]{"String", "String", "int"})
        );
    }

    @DisplayName("Check if fields are private")
    @ParameterizedTest
    @MethodSource("listPrivateFields")
    void isFieldPrivate(String clas, String fieldName) {
        try {
            Class<?> clazz = Class.forName(PACKAGE + clas);
            Field field = clazz.getDeclaredField(fieldName);
            assertTrue(Modifier.isPrivate(field.getModifiers()),
                    "Field " + fieldName + " should be private in " + clas);
        } catch (ClassNotFoundException e) {
            fail("Class " + clas + " does not exist");
        } catch (NoSuchFieldException e) {
            fail("Field " + fieldName + " does not exist in " + clas);
        }
    }

    private static Stream<Arguments> listPrivateFields() {
        return Stream.of(
                Arguments.of("Person", "name"),
                Arguments.of("Student", "studyPlace"),
                Arguments.of("Student", "studyYears"),
                Arguments.of("Worker", "workPosition"),
                Arguments.of("Worker", "experienceYears")
        );
    }

    @DisplayName("Check if child class extends parent class")
    @ParameterizedTest
    @MethodSource("listOfChildren")
    void extendsTypeClass(String parent, String child) {
        try {
            Class<?> parentClazz = Class.forName(PACKAGE + parent);
            Class<?> childClazz = Class.forName(PACKAGE + child);
            assertTrue(parentClazz.isAssignableFrom(childClazz), child + " should extend " + parent);
        } catch (ClassNotFoundException e) {
            fail("Class " + child + " or " + parent + " does not exist");
        }
    }

    private static Stream<Arguments> listOfChildren() {
        return Stream.of(
                Arguments.of("Person", "Student"),
                Arguments.of("Person", "Worker")
        );
    }

    //    @DisplayName("Check if 'maxDuration' method doesn't modify the original list")
//    @Test
//    void checkOriginUnchanged() {
//        List<Person> originalList = List.of(
//                new Person("Ivan"),
//                new Student("Petro", "University", 3),
//                new Worker("Andriy", "Developer", 12)
//        );
//        List<Person> copyList = new ArrayList<>(originalList);
//        try {
//            new MyUtils().maxDuration(copyList);
//            assertEquals(originalList, copyList, "Original list should not be modified");
//        } catch (Exception e) {
//            fail("Original list was modified in 'maxDuration' method");
//        }
//    }
    @DisplayName("Check if 'maxDuration' method returns List")
    @Test
    void checkReturnTypeMaxDuration() {
        try {
            Method method = MyUtils.class.getDeclaredMethod("maxDuration", List.class);
            assertEquals(List.class, method.getReturnType(), "'maxDuration' should return List");
        } catch (NoSuchMethodException e) {
            fail("'maxDuration' method does not exist");
        }
    }

//    @DisplayName("Check correct logic of maxDuration method")
//    @Test
//    void checkMaxDurationLogic() {
//        List<Person> persons = List.of(
//                new Student("Petro", "University", 3),
//                new Student("Ivan", "College", 4),
//                new Worker("Andriy", "Developer", 12)
//        );
//
//        List<Person> expected = List.of(new Worker("Andriy", "Developer", 12), new Student("Ivan", "College", 4));
//        List<Person> actual = new MyUtils().maxDuration(persons);
//
//        assertEquals(new HashSet<>(expected), new HashSet<>(actual));
//    }

//    @DisplayName("Check if Student constructor initializes fields correctly")
//    @Test
//    void checkStudentConstructorInitialization() {
//        Student student = new Student("Petro", "University", 3);
//        assertEquals("Petro", student.getName());
//        assertEquals("University", student.getStudyPlace());
//        assertEquals(3, student.getStudyYears());
//    }


}
