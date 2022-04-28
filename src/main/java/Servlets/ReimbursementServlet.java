package Servlets;

import DTOs.AuthDTO;
import DTOs.ReimbursementDTO;
import Models.Reimbursement;
import Models.User;
import Persistence.ReimbursementDAO;
import Persistence.UserDAO;
import Services.ReimbursementServices;
import com.fasterxml.jackson.databind.ObjectMapper;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class ReimbursementServlet extends HttpServlet {
    private ReimbursementServices rServices;
    private ReimbursementDTO rdto;
    private UserDAO udao;
    @Override
    public void init() throws ServletException{
        this.rServices = new ReimbursementServices();
        this.rdto = new ReimbursementDTO();
        this.udao = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Made it to get method of reimbursement servlet");
        switch(req.getHeader("mode")) {
            case "getAll":
                List<Reimbursement> models = rServices.readAll(Integer.parseInt(req.getHeader("reimbId")));
                String json = new ObjectMapper().writeValueAsString(models);
                System.out.println(models);
                resp.setContentType("application/json");
                resp.getWriter().print(json);
                resp.setStatus(200);
                break;
            case "getOne":
                Reimbursement model = rServices.read(Integer.parseInt(req.getHeader("reimbId")));
                json = new ObjectMapper().writeValueAsString(model);
                System.out.println(model);
                resp.setContentType("application/json");
                resp.getWriter().print(json);
                resp.setStatus(200);
                break;
            default:
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Made into post section of ReimbursementServlet");

        rdto = new ObjectMapper().readValue(req.getInputStream(), ReimbursementDTO.class);

        System.out.println("DEBUG - rdto: " + rdto);

        int switchOnReturnService = rServices.createReimbursement(rdto);
        switch (switchOnReturnService){
            case 0:
                resp.setStatus(201);
                resp.getWriter().print(new ObjectMapper().writeValueAsString(rdto));
                break;
            case 1:
                resp.setStatus(451);
                break;
            case 2:
                resp.setStatus(452);
                break;
        }
        System.out.println(resp.getStatus());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Made it to put section of reimbursement");
        Reimbursement reimb = new ObjectMapper().readValue(req.getInputStream(), Reimbursement.class);
        rServices.updateReimbursement(reimb);
        System.out.println(reimb);
        resp.setStatus(200);
        resp.getWriter().print(new ObjectMapper().writeValueAsString(reimb));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Made it to the delete section of reimbursement");
        System.out.println(req.getHeader("reimbId"));
        rServices.delete(Integer.parseInt(req.getHeader("reimbId")));
        resp.setStatus(200);
    }
}
