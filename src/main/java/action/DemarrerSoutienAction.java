/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Soutien;
import static metier.service.Service.demarrerSoutien;

/**
 *
 * @author fjourda
 */
public class DemarrerSoutienAction extends Action{
    public DemarrerSoutienAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Soutien soutien = (Soutien) session.getAttribute("soutien");
        System.out.println(soutien);
        Boolean confirmation = demarrerSoutien(soutien);
        request.setAttribute("confirmation", confirmation);
    }
    
}