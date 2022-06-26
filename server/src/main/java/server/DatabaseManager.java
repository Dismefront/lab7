package server;

import collection.CollectionData;
import storage.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;

public class DatabaseManager {

    private static Connection connection;

    private static String URL = "jdbc:postgresql://pg/studs";
    private static String USERNAME = "s336187";
    private static String PASSWORD = "rya473";
    private String curUser;

    public String getCurUser() {
        return curUser;
    }

    public void setCurUser(String curUser) {
        this.curUser = curUser;
    }

    public static void extractDatabase() throws SQLException {
        connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException ex) {
            System.out.println("Driver manager not found");
            System.exit(2);
        }
        Statement statement = connection.createStatement();
        statement.executeUpdate("create sequence if not exists ids start 1");
        statement.executeUpdate(
                "create table if not exists workers (" +
                        "id bigint not null primary key, name varchar(50) not null, " +
                        "coordinates_x real not null check(coordinates_x <= 46) , coordinates_y int not null," +
                        "creation_date varchar(20) not null, salary bigint check(salary > 0)," +
                        "start_date varchar(20) not null, position varchar(20)," +
                        "status varchar(20), person_weight int check(person_weight > 0)," +
                        "person_eyeColor varchar(20), person_hairColor varchar(20)," +
                        "person_nationality varchar(20), author varchar(20) not null" +
                        ")"
        );
        statement.executeUpdate(
                "create table if not exists users (" +
                        "login varchar(20) not null primary key, password bytea not null" +
                        ")"
        );
    }

    public static void delWorker(long id, String user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "delete from workers where (id, author) = (?, ?)"
            );
            statement.setLong(1, id);
            statement.setString(2, user);
            if (statement.executeUpdate() > 0)
                System.out.println("Worker deleted");
            else
                System.out.println("Could not delete worker");
        }
        catch (Exception ex) {
            System.out.println("Could not delete worker2");
        }
    }

    public static boolean checkValidity(long id, String user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from workers" +
                            "where id = ?"
            );
            statement.setLong(1, id);
            ResultSet st = statement.executeQuery();
            st.next();
            return user.equals(st.getString("author"));
        }
        catch (Exception ex) {
            System.out.println("Could not delete worker");
            return false;
        }
    }

    public static void addWorker(Worker w) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into workers(id, name,coordinates_x," +
                            "coordinates_y,creation_date,salary," +
                            "start_date,position,status,person_weight," +
                            "person_eyeColor,person_hairColor," +
                            "person_nationality,author) values(nextVal('ids'),?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );

            //statement.setLong(1, w.getId());
            statement.setString(1, w.getName());
            statement.setFloat(2, w.getCoordinates().getX());
            statement.setInt(3, w.getCoordinates().getY());
            statement.setString(4, w.getCreationDate().toString());
            statement.setLong(5, w.getSalary());
            statement.setString(6, w.getStartDate().toString());
            if (w.getPosition() != null)
                statement.setString(7, w.getPosition().toString());
            else
                statement.setString(7, null);
            if (w.getStatus() != null)
                statement.setString(8, w.getStatus().toString());
            else
                statement.setString(8, null);
            statement.setInt(9, w.getPerson().getWeight());
            if (w.getPerson().getEyeColor() != null)
                statement.setString(10, w.getPerson().getEyeColor().toString());
            else
                statement.setString(10, null);
            if (w.getPerson().getHairColor() != null)
                statement.setString(11, w.getPerson().getHairColor().toString());
            else
                statement.setString(11, null);
            if (w.getPerson().getNationality() != null)
                statement.setString(12, w.getPerson().getNationality().toString());
            else
                statement.setString(12, null);
            statement.setString(13, w.getOwner());
            int rows = statement.executeUpdate();

            if (rows > 0)
                System.out.println("Worker added to db");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Could not add worker to db");
        }
    }

    public static void loadDataToCollection() {
        Coordinates c;
        Worker w;
        Person p;
        try {
            PreparedStatement statement = connection.prepareStatement("select * from workers");
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                c = new Coordinates(res.getFloat(3), res.getInt(4));
                Color eyeColor, hairColor;
                Country country;
                try {
                    eyeColor = Color.valueOf(res.getString("person_eyeColor"));
                }
                catch (Exception ex) {
                    eyeColor = null;
                }
                try {
                    hairColor = Color.valueOf(res.getString("person_hairColor"));
                }
                catch (Exception ex) {
                    hairColor = null;
                }
                try {
                    country = Country.valueOf(res.getString("person_nationality"));
                }
                catch (Exception ex) {
                    country = null;
                }
                p = new Person(res.getInt(10),
                        eyeColor,
                        hairColor,
                        country);
                String name = res.getString("name");
                long salary = res.getLong("salary");
                LocalDate startDate = LocalDate.parse(res.getString("start_date"));
                Position pos;
                try {
                    pos = Position.valueOf(res.getString("position"));
                }
                catch (Exception ex) {
                    pos = null;
                }

                Status st;
                try {
                    st = Status.valueOf(res.getString("status"));
                }
                catch (Exception ex) {
                    st = null;
                }
                String user = res.getString("author");
                w = new Worker(name, c, salary, startDate, pos, st, p, user);
                w.setId(res.getLong("id"));
                CollectionData.collection.add(w);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("smth went wrong :(");
        }

    }

    public static void regUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into users values(?, ?)"
            );
            statement.setString(1, user.getUsername());
            statement.setBytes(2, user.getPassword());
            int rows = statement.executeUpdate();
            if (rows > 0)
                System.out.println("User successfully registered");
        }
        catch (Exception ex) {
            System.out.println("User is already registered");
        }
    }

    public static boolean isUserValid(String username, byte[] pw) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?"
            );
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            res.next();
            if (Arrays.equals(res.getBytes("password"), pw)) {
                System.out.println("User " + username + " logged");
                return true;
            } else
                return false;
        } catch (Exception ex) {
            //ex.printStackTrace();
            return false;
        }
    }

}
