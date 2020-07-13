package ru_kpfu_itis_group905_kiyamdinov.models;

/**
 * 10.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class Mentor {

    private Long id;
    private String firstName;
    private String lastName;
    private Student student;
    private Integer subject;

    public Mentor(Long id, String firstName, String lastName, Student student, Integer subject) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.student = student;
        this.subject = subject;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {

        String result = "Mentor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'';

        if (student != null)
            return result + ", student=" + student.getId() +
                    ", subject=" + subject +
                    '}';

        else
            return result + ", subject=" + subject +
                    '}';
    }
}

