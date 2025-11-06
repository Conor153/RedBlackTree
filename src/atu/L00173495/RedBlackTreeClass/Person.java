package atu.L00173495.RedBlackTreeClass;

/**
* This is a Person class
* 
* 
* @author Conor.Callaghan
*
* @param <T>
*/

public class Person implements Comparable<Person> {
    private String firstName;
    private String surName;
    private int age;

    public Person(String firstName, String surName, int age)
    {
        this.firstName = firstName;
        this.surName = surName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurName() {
        return surName;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [firstName=" + firstName + ", surName=" + surName + ", age=" + age + "]";
    }

    public int compareTo(Person other) {
        return this.surName.compareTo(other.surName);
    }

    
}
