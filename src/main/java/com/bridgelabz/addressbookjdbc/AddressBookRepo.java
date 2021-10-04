package com.bridgelabz.addressbookjdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookRepo {
    Connection connection;

    private static Connection getConnection() {
        String url = "jdbc:mysql://127.0.0.1:3306/AddressBookService";
        String username = "root";
        String password = "rootpassword";
        Connection connection = null;
        try {

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Contacts> retrieveData() {
        ResultSet resultSet = null;
        List<Contacts> addressBookList = new ArrayList<Contacts>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from AddressBook";
            resultSet = statement.executeQuery(sql);
            int count = 0;
            while (resultSet.next()) {
                Contacts contactInfo = new Contacts();
                contactInfo.setFirstName(resultSet.getString("firstName"));
                contactInfo.setLastName(resultSet.getString("Lastname"));
                contactInfo.setAddress(resultSet.getString("address"));
                contactInfo.setCity(resultSet.getString("city"));
                contactInfo.setState(resultSet.getString("state"));
                contactInfo.setZip(resultSet.getInt("zip"));
                contactInfo.setPhoneNumber(resultSet.getString("phoneNumber"));
                contactInfo.setEmailId(resultSet.getString("email"));
                contactInfo.setBookName(resultSet.getString("bookName"));
                contactInfo.setContactType(resultSet.getString("contactType"));
                contactInfo.setDateAdded(resultSet.getDate("Date_added").toLocalDate());

                addressBookList.add(contactInfo);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return addressBookList;

    }

    public void updateCityByZip(String address, String city, String state, int zip, int srNo) {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String query = "Update addressBook set address=" + "'" + address + "'" + ", " + "city=" +"'" + city + "'" + ", " + "state=" + "'" + state + "'" + ", " + "zip=" + zip + " where srNo=" + srNo + ";";
            int result = statement.executeUpdate(query);
            System.out.println(result);
            if (result > 0) {
                System.out.println("Address Updated Successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
