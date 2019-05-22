package DAO;

import org.bson.types.ObjectId;

public final class User {
    private ObjectId id;
    private String name;
    private int age;
    private Address address;

    public User(){

    }

    public User(String name, int age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" Name: ");
        s.append(this.name);
        s.append(" Age: ");
        s.append(this.age);
        s.append(" Address: ");
        s.append(this.address);
        return s.toString();
    }
}
