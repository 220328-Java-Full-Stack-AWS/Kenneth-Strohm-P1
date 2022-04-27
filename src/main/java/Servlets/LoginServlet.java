package Servlets;

import DTOs.AuthDTO;
import Models.User;
import Persistence.UserDAO;
import Services.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private UserServices uservices;
    private ObjectMapper mapper;
    private UserDAO udao;
    @Override
    public void init() throws ServletException{
        this.uservices = new UserServices();
        this.mapper = new ObjectMapper();
        this.udao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = udao.getUserByUsername(req.getHeader("userName"));
        resp.setStatus(200);
        resp.getWriter().print(mapper.writeValueAsString(user));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Made into post section of LoginServlet");
        AuthDTO adto = mapper.readValue(req.getInputStream(), AuthDTO.class);
        User currentUser = uservices.loginCheck(adto.getUserName(), adto.getPassword());
        if(currentUser.equals(null)){
            resp.setStatus(401);
            System.out.println(401);
        }
        else{
            System.out.println(200);
            resp.setStatus(200);
            resp.getWriter().print(mapper.writeValueAsString(currentUser));
            resp.setHeader("access-control-expose-headers", "authToken");
            resp.setHeader("authToken", currentUser.getUserName());
        }

    }
}
