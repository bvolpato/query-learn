package org.brunovolpato.calcite.learn;

import java.sql.*;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.tools.Frameworks;

public class L05_CalciteSQLOverSQLite {

  public static void main(String[] args) throws SQLException, ClassNotFoundException {
    Class.forName("org.apache.calcite.jdbc.Driver");

    // Create a connection to Calcite using the model
    Connection connection =
        DriverManager.getConnection(
            "jdbc:calcite:model=" + L04_RelBuilder.class.getResource("/model.json").getFile());
    CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);

    Frameworks.ConfigBuilder configBuilder = Frameworks.newConfigBuilder();

    String sql =
        "SELECT p.*, s.\"salary\" FROM \"Person\" p LEFT JOIN \"Salary\" s ON p.\"position\" = s.\"position\" WHERE \"age\" > 30";

    // Execute SQL queries
    try (Statement statement = calciteConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)) {

      // Loop through the result set and print the data
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");
        String position = resultSet.getString("position");
        int salary = resultSet.getInt("salary");

        System.out.println("ID: " + id);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Email: " + email);
        System.out.println("Position: " + position);
        System.out.println("Salary: " + salary);
        System.out.println();
      }
    }

    // Close the connection when done
    connection.close();
  }
}
