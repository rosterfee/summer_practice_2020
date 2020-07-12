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
    private int subjectId;
    private Student studentId;

    public Mentor(long id, String first_name, String last_name, Student studentId, int subject_id) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.studentId = studentId;
        this.subjectId = subject_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Student getStudentId() {
        return studentId;
    }

    public void setStudentId(Student student) {
        this.studentId = student;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
}

