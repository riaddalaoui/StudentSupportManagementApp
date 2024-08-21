/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.security.Provider;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import static metier.service.Service.authentifierEleve;
import static metier.service.Service.authentifierIntervenant;

/**
 *
 * @author fjourda
 */
public class AuthentifierUtilisateurAction extends Action {

    public AuthentifierUtilisateurAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
//        TestUtilisateur user = new TestUtilisateur();
//        user.setId(1024);
//        user.setNom("Lovelace");
//        user.setPrenom("Ada");
//        user.setMail("ada.lovelace@insa-lyon.fr");
        
        
        Intervenant user = authentifierIntervenant(login, password);
        Eleve user2 = authentifierEleve(login, password);
            
        
        if (user != null){
            request.setAttribute("utilisateur", user);
            request.setAttribute("type", "Intervenant");
        }
        else if (user2 != null){
            request.setAttribute("utilisateur", user2);
            request.setAttribute("type", "Eleve");
        }
        else {
            request.setAttribute("utilisateur", null);
            request.setAttribute("type", null);
        }
    }
}
