package ru_kpfu_itis_group905_kiyamdinov.repositories;

import ru_kpfu_itis_group905_kiyamdinov.models.Mentor;
import ru_kpfu_itis_group905_kiyamdinov.models.Student;
import java.util.List;

/**
 * 10.07.2020
 * 01. Database
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public interface StudentsRepository extends CrudRepository<Student, Mentor> {
    List<Student> findAllByAge(int age);
}

