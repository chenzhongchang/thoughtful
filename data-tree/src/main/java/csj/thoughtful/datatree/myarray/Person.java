package csj.thoughtful.datatree.myarray;

public class Person {

    private String lastName;
    private String firstName;
    private int age;

    public Person(String last,String first, int a){
        lastName=last;
        firstName=first;
        age=a;
    }

    public void displayPerson(){
        System.out.println("    last name: "+lastName);
        System.out.println("    first name: "+firstName);
        System.out.println("    age name: "+age);
    }

    public String getLast(){
        return lastName;
    }



}
