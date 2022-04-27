package Persistence;

import Connection.DBConnector;
import Models.Role;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;


public class UserDAO implements CRUDInterface<User>{

    public boolean checkIfEmailIsUnique(String email){
        try{
            String sql = "SELECT user_email FROM ers_users WHERE user_email = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()){
                return true;
            }
        }
        catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }


    public boolean checkIfUserNameIsUnique(String username){
        try{
            String sql = "SELECT ers_username FROM ers_users WHERE ers_username = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()){
                return true;
            }
        }
        catch (SQLException se){
            se.printStackTrace();
        }catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return false;
    }

    public User getUserByUsername(String username){
        User user = new User();
        String sql = "SELECT * FROM ers_users WHERE ers_username = ?";
        try {
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                user.setUserId(rs.getInt("ers_user_id"));
                user.setUserName(rs.getString("ers_username"));
                user.setPassword(rs.getString("ers_password"));
                user.setfName(rs.getString("user_first_name"));
                user.setlName(rs.getString("user_last_name"));
                user.setEmail(rs.getString("user_email"));
                user.setRoleId(rs.getInt("user_role_id"));
                if(user.getRoleId() == 2){
                    user.setRole(Role.FINANCE_MANAGER);
                }
                else{
                    user.setRole(Role.EMPLOYEE);
                }
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }catch (NullPointerException ne) {
            ne.printStackTrace();
        }
        return user;
    }

    @Override
    public User create(User model) {

        try{
            String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id) " +
                    "VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, model.getUserName());
            pstmt.setString(2, model.getPassword());
            pstmt.setString(3, model.getfName());
            pstmt.setString(4,model.getlName());
            pstmt.setString(5, model.getEmail());
            pstmt.setInt(6, 1);
            pstmt.executeUpdate();
            model.setRole(Role.EMPLOYEE);
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()){
                int key = keys.getInt(1);
                model.setUserId(key);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return model;
    }

    @Override
    public User read(int id) {
        User user = new User();
        try {
            String sql = "SELECT ers_user_id, ers_username, user_first_name, user_last_name, user_email, user_role_id FROM ers_users WHERE ers_user_id = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                user.setUserId(rs.getInt("ers_user_id"));
                user.setUserName(rs.getString("ers_username"));
                user.setfName(rs.getString("user_first_name"));
                user.setlName(rs.getString("user_last_name"));
                user.setEmail(rs.getString("user_email"));
                user.setRoleId(rs.getInt("user_role_id"));
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        System.out.println(user.getUserId() + " " + user.getUserName() + " " +
                user.getfName() + " " + user.getlName() + " " + user.getEmail() +
                " " + user.getRoleId());
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new LinkedList<>();
        try {
            String sql = "SELECT ers_username, user_first_name, user_last_name, user_role_id FROM ers_users";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                User user = new User();
                user.setUserName(rs.getString("ers_username"));
                user.setfName(rs.getString("user_first_name"));
                user.setlName(rs.getString("user_last_name"));
                user.setRoleId((rs.getInt("user_role_id")));
                list.add(user);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(User model) {
        /*

        If I get time, let users update name and email

        try {
            String sql = "UPDATE ers_users SET user_";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
        }
        catch(SQLException se){
            se.printStackTrace();
        }

         */

    }

    @Override
    public void delete(int id) {
        //possible give admin ability to delete users
    }

    @Override
    public void delete(User model) {
        //possible give admin ability to delete users
    }
}
