/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Soutien;
import static metier.service.Service.getSoutienValue;
import static metier.service.Service.noterSoutien;

/**
 *
 * @author fjourda
 */
public class NoterSoutienAction extends Action{
    public NoterSoutienAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Soutien soutien = (Soutien) session.getAttribute("soutien");
        String noteStr = (String)request.getParameter("note");
        Integer note = Integer.valueOf(noteStr);
        Soutien updatedSoutien = getSoutienValue(soutien);
        System.out.println(soutien);
        
        System.out.println("soutien normalement updated");

        System.out.println(updatedSoutien);
        Boolean confirmation = noterSoutien(updatedSoutien,note);
        request.setAttribute("confirmation", confirmation);
    }
    
}