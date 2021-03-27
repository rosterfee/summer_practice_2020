package ru_kpfu_itis_group905_kiyamdinov;



import ru_kpfu_itis_group905_kiyamdinov.repositories.StudentsRepository;
import ru_kpfu_itis_group905_kiyamdinov.repositories.StudentsRepositoryJdbcImpl;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "gev56poison";


    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        StudentsRepository studentsRepository = new StudentsRepositoryJdbcImpl(connection);
        System.out.println(studentsRepository.findById(2L));


        connection.close();
    }
}

