package ru_kpfu_itis_group905_kiyamdinov.repositories;

import ru_kpfu_itis_group905_kiyamdinov.models.Mentor;
import ru_kpfu_itis_group905_kiyamdinov.models.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 10.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class StudentsRepositoryJdbcImpl implements StudentsRepository {

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from student where id = ";
    private static final String SQL_SELECT_BY_AGE = "select * from student where age = ";
    private static final String SQL_SELECT_BY_STUD_ID_MENTOR = "select * from mentor where student_id = ";
    private static final String SQL_SELECT_JOIN_STUD_AND_MENTOR = "select m.id as m_id, \" +\n" +
            "\"m.first_name as m_first_name, \" +\n" +
            "\"m.last_name as m_last_name, \" +\n" +
            "\"m.subject_id as m_subject_id, \" +\n" +
            "\"s.id as s_id, \" +\n" +
            "\"s.first_name as s_first_name, \" +\n" +
            "\"s.last_name as s_last_name, \" +\n" +
            "\"s.age as s_age, \" +\n" +
            "\"s.group_number as s_group_number \" +\n" +
            "\"from student s left join mentor m on s.id = m.id";
    private Connection connection;

    public StudentsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Student> findAllByAge(int age) {
        Statement statement1 = null;
        ResultSet resultStudent = null;

        try {
            statement1 = connection.createStatement();
            resultStudent = statement1.executeQuery(SQL_SELECT_BY_AGE + age);

            List<Student> listStudent = new ArrayList<>();
            Student student;

            while (resultStudent.next()) {
                student = new Student(
                        resultStudent.getLong("id"),
                        resultStudent.getString("first_name"),
                        resultStudent.getString("last_name"),
                        resultStudent.getInt("age"),
                        resultStudent.getInt("group_number"));

                try (Statement statement2 = connection.createStatement()) {

                    ResultSet resultMentor = statement2.executeQuery(SQL_SELECT_BY_STUD_ID_MENTOR + student.getId());
                    List<Mentor> listMentor = new ArrayList<>();

                    while (resultMentor.next()) {
                        listMentor.add(new Mentor(
                                resultMentor.getLong("id"),
                                resultMentor.getString("first_name"),
                                resultMentor.getString("last_name"),
                                student,
                                resultMentor.getInt("subject_id")
                        ));
                    }

                    student.setMentors(listMentor);

                    listStudent.add(student);

                } catch (SQLException e) {
                    throw new IllegalArgumentException(e);
                }
            }
            return listStudent;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (resultStudent != null) {
                try {
                    resultStudent.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
    }

    // Необходимо вытащить список всех студентов, при этом у каждого студента должен быть проставлен список менторов
    // у менторов в свою очередь ничего проставлять (кроме имени, фамилии, id не надо)
    // student1(id, firstName, ..., mentors = [{id, firstName, lastName, null}, {}, ), student2, student3
    // все сделать одним запросом
    @Override
    public List<Student> findAll() {
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_JOIN_STUD_AND_MENTOR);

            List<Student> list = new ArrayList<>();

            long id = 0;

            if (result.next()) {
                id = result.getLong("s_id");
            }

            addStudent(list, id, result);

            while (result.next()) {
                for (Student student : list) {
                    if (student.getId() == id) {
                        student.getMentors().add(new Mentor(
                                result.getLong("s_id"),
                                result.getString("m_first_name"),
                                result.getString("m_last_name"),
                                student,
                                result.getInt("m_subject_id")
                        ));
                    }
                }
                addStudent(list, id, result);
                id = result.getLong("s_id");
            }

            return list;

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
// ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
// ignore
                }
            }
        }
    }

    @Override
    public Student findById(Long id) {
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery(SQL_SELECT_BY_ID + id);
            if (result.next()) {
                return new Student(
                        result.getLong("id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getInt("age"),
                        result.getInt("group_number")
                );
            } else return null;
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    // ignore
                }
            }
        }

    }

    private void addStudent(List<Student> listStudent, long id, ResultSet result) {

        try {
            List<Mentor> listMentor = new ArrayList<>();
            Student student = new Student(
                    id,
                    result.getString("s_first_name"),
                    result.getString("s_last_name"),
                    result.getInt("s_age"),
                    result.getInt("s_group_number")
            );

            listMentor.add(new Mentor(
                    result.getLong("m_id"),
                    result.getString("m_first_name"),
                    result.getString("m_last_name"),
                    student,
                    result.getInt("m_subject_id")
            ));

            listStudent.add(student);

        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    // просто вызывается insert для сущности
    // student = Student(null, 'Марсель', 'Сидиков', 26, 915)
    // studentsRepository.save(student);
    // // student = Student(3, 'Марсель', 'Сидиков', 26, 915)
    @Override
    public void save(Student entity, Mentor mentor) {
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery("insert into student (first_name, last_name, age, group_number) value" +
                    " (" + entity.getFirstName()
                    + ", " + entity.getLastName()
                    + ", " + entity.getAge()
                    + ", " + entity.getGroupNumber() + ");");

            result = statement.executeQuery("insert into student (first_name, last_name, subject_id, student_id) value" +
                    " (" + mentor.getFirstName()
                    + ", " + mentor.getLastName()
                    + ", " + mentor.getSubjectId()
                    + ", " + mentor.getStudentId() + ");");
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }

            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    // для сущности, у которой задан id выполнить обновление всех полей

    // student = Student(3, 'Марсель', 'Сидиков', 26, 915)
    // student.setFirstName("Игорь")
    // student.setLastName(null);
    // studentsRepository.update(student);
    // (3, 'Игорь', null, 26, 915)

    @Override
    public void update(Student entity) {
        Statement statement = null;
        ResultSet result = null;

        try {
            statement = connection.createStatement();
            result = statement.executeQuery("update student set first_name = " + entity.getFirstName() +
                    ", last_name = " + entity.getLastName() +
                    ", age =  " + entity.getAge() +
                    ", group_number = " + entity.getGroupNumber() +
                    " where id = " + entity.getId() + ";");
        } catch (SQLException e) {
            throw new IllegalArgumentException();
        } finally {

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }

            if (result != null) {
                try {
                    result.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }
}

