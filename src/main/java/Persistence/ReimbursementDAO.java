package Persistence;

import Connection.DBConnector;
import Models.Reimbursement;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ReimbursementDAO implements CRUDInterface<Reimbursement> {

    @Override
    public Reimbursement create(Reimbursement model) {
        System.out.println(model.getAuthor());
        try{
            String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_description, /*reimb_receipt,*/ reimb_author, reimb_status_id, reimb_type_id)"
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setDouble(1, model.getReimbursementAmount());
            pstmt.setString(2, String.valueOf(LocalDate.now()));
            pstmt.setString(3, model.getDescription());
            //pstmt.setBlob(4, model.getImage());
            //This int below should really get the current user that is logged in
            pstmt.setInt(4, model.getAuthor());
            pstmt.setInt(5, 1);
            pstmt.setInt(6, model.getType());
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if(keys.next()){
                int key = keys.getInt(1);
                model.setReimbursementId(key);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        return model;
    }

    @Override
    //double reimbursementAmount, String description, Blob image, int author, int type
    public Reimbursement read(int id) {

        Reimbursement reimb = new Reimbursement();
        try{
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                reimb.setReimbursementId(rs.getInt("reimb_id"));
                reimb.setReimbursementAmount(rs.getDouble("reimb_amount"));
                reimb.setSubmitted(rs.getString("reimb_submitted"));
                reimb.setResolved(rs.getString("reimb_resolved"));
                reimb.setDescription(rs.getString("reimb_description"));
                reimb.setAuthor(rs.getInt("reimb_author"));
                reimb.setResolver(rs.getInt("reimb_resolver"));
                reimb.setStatus(rs.getInt("reimb_status_id"));
                reimb.setType(rs.getInt("reimb_type_id"));

            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }

        return reimb;
    }

    //This section is for the admin
    //
    //
    //
    //


    @Override
    public List<Reimbursement> getAll() {
        List<Reimbursement> list = new LinkedList<>();
        try{
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = 1";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Reimbursement reimb = new Reimbursement();
                reimb.setReimbursementId(rs.getInt("reimb_id"));
                reimb.setReimbursementAmount(rs.getDouble("reimb_amount"));
                reimb.setSubmitted(rs.getString("reimb_submitted"));
                reimb.setDescription(rs.getString("reimb_description"));
                reimb.setAuthor(rs.getInt("reimb_author"));
                reimb.setStatus(rs.getInt("reimb_status_id"));
                reimb.setType(rs.getInt("reimb_type_id"));
                list.add(reimb);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }

        return list;
    }

    public void adminUpdate(Reimbursement model) {
        try {
            String sql = "UPDATE ers_reimbursement SET reimb_status_id = ?, reimb_resolved = ?, reimb_resolver = ? WHERE reimb_id = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setInt(1, model.getStatus());
            pstmt.setString(2, String.valueOf(LocalDate.now()));
            pstmt.setInt(3, model.getResolver());
            pstmt.setInt(4, model.getReimbursementId());
            pstmt.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }


    //Admin stuff is done
    //
    //
    //

    public List<Reimbursement> getAllForUser(int id) {
        List<Reimbursement> list = new LinkedList<>();
        try{
            String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                Reimbursement reimb = new Reimbursement();
                reimb.setReimbursementId(rs.getInt("reimb_id"));
                reimb.setReimbursementAmount(rs.getDouble("reimb_amount"));
                reimb.setSubmitted(rs.getString("reimb_submitted"));
                reimb.setDescription(rs.getString("reimb_description"));
                reimb.setAuthor(rs.getInt("reimb_author"));
                reimb.setStatus(rs.getInt("reimb_status_id"));
                reimb.setType(rs.getInt("reimb_type_id"));
                list.add(reimb);
            }
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        System.out.println(list);
        return list;
    }

    @Override
    public void update(Reimbursement model) {
        try {
            String sql = "UPDATE ers_reimbursement SET reimb_amount = ?,  reimb_description = ?, /*reimb_receipt,*/ reimb_type_id = ? WHERE reimb_id = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setDouble(1, model.getReimbursementAmount());
            pstmt.setString(2, model.getDescription());
            //pstmt.setBlob(4, model.getImage());
            //This int below should really get the current user that is logged in
            pstmt.setInt(3, model.getType());
            pstmt.setInt(4, model.getReimbursementId());
            pstmt.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String sql = "DELETE FROM ers_reimbursement WHERE reimb_id = ?";
            PreparedStatement pstmt = DBConnector.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
        catch(SQLException se){
            se.printStackTrace();
        }

    }

    @Override
    public void delete(Reimbursement model) {

    }
}
