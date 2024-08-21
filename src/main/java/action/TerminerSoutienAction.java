/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Soutien;
import static metier.service.Service.finirSoutien;
import static metier.service.Service.getSoutienValue;

/**
 *
 * @author fjourda
 */
public class TerminerSoutienAction extends Action{
    public TerminerSoutienAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Soutien soutien = (Soutien) session.getAttribute("soutien");
        System.out.println(soutien);
        Soutien updatedSoutien = getSoutienValue(soutien);

        Boolean confirmation = finirSoutien(updatedSoutien);
        request.setAttribute("confirmation", confirmation);
    }
    
}