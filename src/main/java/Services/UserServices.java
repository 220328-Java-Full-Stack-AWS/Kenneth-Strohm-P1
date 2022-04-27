package Services;

import Models.User;
import Persistence.UserDAO;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServices {

    private UserDAO udao = new UserDAO();
    private Regex reggie = new Regex();


    public int createNewUser(User model){
        if(!reggie.userNameCheck(model.getUserName())){
            System.out.println("Made it to regex username check.");
            return 1;
        }
        else if(!reggie.passwordCheck(model.getPassword())){
            System.out.println("Made it to regex password check.");
            return 2;
        }
        else if(!reggie.userNameCheck(model.getfName())){
            System.out.println("Made it to regex fName check.");
            return 3;
        }
        else if(!reggie.userNameCheck(model.getlName())){
            System.out.println("Made it to regex lName check.");
            return 4;
        }
        else if(!reggie.emailCheck(model.getEmail())){
            System.out.println("Made it to regex email check.");
            return 5;
        }
        else {
            udao.create(model);
            return 0;
        }
    }


    public User loginCheck(String username, String password){
        User user = udao.getUserByUsername(username);
        if(username != null && user.getPassword().equals(password)){
            return user;
        }
        else {
            return null;
        }
    }

}
