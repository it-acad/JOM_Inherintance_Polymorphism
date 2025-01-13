package jom.com.softserve.s2.task1;

/**
 * Represents a child with specific attributes like age, health information, and name.
 * Inherits from the {@link Person} class.
 */
class Child extends Person {
    int age;
    String healthInfo;
    String name;

    /**
     * Combines the child's name and health information into a single string.
     *
     * @return A string containing the child's name and health status.
     */
    String getHealthStatus() {
        return name + " " + healthInfo;
    }

}