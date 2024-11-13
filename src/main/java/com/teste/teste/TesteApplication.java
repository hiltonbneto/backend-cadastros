package com.teste.teste;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesteApplication {

	public static void main(String[] args) {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "murphy123");
			statement = connection.createStatement();
			statement.executeQuery("SELECT count(*) FROM pg_database WHERE datname = 'cadastros'");
			ResultSet resultSet = statement.getResultSet();
			resultSet.next();
			int count = resultSet.getInt(1);

			if (count <= 0) {
				statement.executeUpdate("CREATE DATABASE cadastros");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		SpringApplication.run(TesteApplication.class, args);
	}

}
