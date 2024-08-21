/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Intervenant;
import metier.modele.Soutien;
import static metier.service.Service.getSoutien;

/**
 *
 * @author fjourda
 */
public class GetSoutienAction extends Action {
     public GetSoutienAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Intervenant utilisateur = (Intervenant) session.getAttribute("utilisateur");

        System.out.println("l'intervenant :");
        System.out.println(utilisateur);
        Soutien soutien = getSoutien(utilisateur);
        System.out.println("le soutien :");
        System.out.println(soutien);
        if (soutien != null){
            request.setAttribute("confirmation", true);
            request.setAttribute("soutien", soutien);
        }
        else {
            request.setAttribute("confirmation", false);
        }
        
    }
    
}
