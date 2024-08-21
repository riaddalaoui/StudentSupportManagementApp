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
import static metier.service.Service.mettreBilanSoutien;

/**
 *
 * @author fjourda
 */
public class BilanSoutienAction extends Action{
    public BilanSoutienAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Soutien soutien = (Soutien) session.getAttribute("soutien");
        String bilan = (String)request.getParameter("bilan");
        Soutien updatedSoutien = getSoutienValue(soutien);
        
        System.out.println(soutien);
        
        System.out.println("soutien normalement updated");

        System.out.println(updatedSoutien);
        
        Boolean confirmation = mettreBilanSoutien(updatedSoutien,bilan);
        request.setAttribute("confirmation", confirmation);
    }
    
}