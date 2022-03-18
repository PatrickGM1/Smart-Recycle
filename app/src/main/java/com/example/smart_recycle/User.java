package com.example.smart_recycle;

import java.util.Comparator;

public class User {
    public String name, age, email;
    public int garbagePoints;
    public User(){

    }
    public User(String name, String age, String email,int garbagePoints){
        this.name = name;
        this.age=age;
        this.email=email;
        this.garbagePoints = garbagePoints;
    }

}
class Sortbypoints implements Comparator<User>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(User a, User b)
    {
        return a.garbagePoints - b.garbagePoints;
    }
}
