package Services;

import DTOs.AdminReimbursementDTO;
import DTOs.ReimbursementDTO;
import Models.Reimbursement;
import Persistence.ReimbursementDAO;

import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ReimbursementServices {

    private Regex reggie = new Regex();
    ReimbursementDAO rdao = new ReimbursementDAO();


    public int createReimbursement(ReimbursementDTO passedValues){
        Reimbursement model = new Reimbursement();
        if(!reggie.dollarCheck(passedValues.getReimbursementAmount())){
            System.out.println("Made it to regex dollar amount check.");
            return 1;
        }
        else if(!reggie.userNameCheck(passedValues.getDescription())){
            System.out.println("Made it to regex description check.");
            return 2;
        }
        else {
            model.setReimbursementAmount(passedValues.getReimbursementAmount());
            model.setDescription(passedValues.getDescription());
            model.setAuthor(passedValues.getAuthor());
            model.setType(passedValues.getType());
            rdao.create(model);
            return 0;
        }
    }

    public Reimbursement read(int id){
        return rdao.read(id);
    }

    public List<Reimbursement> readAll(int id){
        return rdao.getAllForUser(id);
    }


    //Admin section
    public List<Reimbursement> readAllAdmin(){return rdao.getAll();}

    public void adminUpdateReimbursement(AdminReimbursementDTO passedValues){
        System.out.println(passedValues.getReimbursementId() + " " + passedValues.getStatus());
        Reimbursement model = new Reimbursement();
        model.setReimbursementId(passedValues.getReimbursementId());
        model.setStatus(passedValues.getStatus());
        model.setResolver(passedValues.getResolver());
        rdao.adminUpdate(model);
    }
    //end admin section

    public void updateReimbursement(Reimbursement passedValues){
        System.out.println(passedValues.getReimbursementId() + " " + passedValues.getDescription() + " " + passedValues.getReimbursementAmount());
        rdao.update(passedValues);
    }

    public void delete(int id){
        rdao.delete(id);
    }


}
