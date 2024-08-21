/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Eleve;
import metier.modele.Soutien;
import static metier.service.Service.creeSoutien;

/**
 *
 * @author fjourda
 */
public class CreateSoutienAction extends Action {
     public CreateSoutienAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Eleve utilisateur = (Eleve) session.getAttribute("utilisateur");
        String categorie = request.getParameter("categorie");

        String description = request.getParameter("description");
        
        System.out.println("le description :");
        System.out.println(description);
        System.out.println("le matiere :");
        System.out.println(categorie);
        System.out.println("le eleve :");
        System.out.println(utilisateur);
        Soutien soutien = creeSoutien(utilisateur, categorie, description);
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
