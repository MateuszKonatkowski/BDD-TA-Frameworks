package models;

public class Adress {
    private String firstName;
    private String lastName;
    private String zipCode;

    public Adress(String firstName, String lastName, String zipCode)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipCode = zipCode;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getZipCode()
    {
        return zipCode;
    }

}
