package parse.model;

/**
 * @author myd
 * @date 2021/7/29  5:28
 */

public class Teacher {

    private String  name;

    private String classNo;

    private User user;


    public Teacher(String name, String classNo, User user) {
        this.name = name;
        this.classNo = classNo;
        this.user = user;
    }


    public Teacher(String name, String classNo) {
        this.name = name;
        this.classNo = classNo;
    }

    public Teacher(User user) {
        this.user = user;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", classNo='" + classNo + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public Teacher setName(String name) {
        this.name = name;
        return this;
    }

    public String getClassNo() {
        return classNo;
    }

    public Teacher setClassNo(String classNo) {
        this.classNo = classNo;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Teacher setUser(User user) {
        this.user = user;
        return this;
    }
}
