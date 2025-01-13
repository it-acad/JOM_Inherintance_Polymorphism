package jom.com.softserve.s2.task1;

/**
 * Represents an adult with personal and health-related details.
 * Inherits from the {@link Person} class.
 */
class Adult extends Person {
    int age;
    String healthInfo;
    String passportNumber;
    String name;

    /**
     * Combines the adult's name and health information into a single string.
     *
     * @return A string containing the adult's name and health status.
     */
    String getHealthStatus() {
        return name + " " + healthInfo;
    }
}