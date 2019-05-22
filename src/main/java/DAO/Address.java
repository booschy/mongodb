package DAO;

public class Address {
    private String street;
    private String city;
    private String zip;

    public Address(){

    }

    public Address(String street, String city, String zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" Street: ");
        s.append(street);
        s.append(" City: ");
        s.append(city);
        s.append(" Zip: ");
        s.append(zip);

        return s.toString();

    }
}
