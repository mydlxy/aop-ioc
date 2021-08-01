package parse.model;

/**
 * @author myd
 * @date 2021/7/29  5:27
 */

public class User {

    private String name;

    private String id;

    private int age;

    private Teacher teacher;

    public User(String name, String id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", age=" + age ;
    }

    public User(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public User(Teacher teacher) {
        this.teacher = teacher;
    }

    public User() {
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public User setTeacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public int getAge() {
        return age;
    }

    public User setAge(int age) {
        this.age = age;
        return this;
    }
}
