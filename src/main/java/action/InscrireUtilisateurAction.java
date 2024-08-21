/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.security.Provider;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import static metier.service.Service.authentifierEleve;
import static metier.service.Service.authentifierIntervenant;
import static metier.service.Service.inscrireEleve;

/**
 *
 * @author fjourda
 */
public class InscrireUtilisateurAction extends Action {

    public InscrireUtilisateurAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {

        Integer classe = Integer.valueOf(request.getParameter("classe"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = request.getParameter("datenaiss");
        Date date = null;
        try {
            date = sdf.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(InscrireUtilisateurAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Eleve user = new Eleve();
        user.setClasse(classe);
        user.setDateNaissance(date);
        user.setEmail(request.getParameter("login"));
        user.setMotDePasse(request.getParameter("password"));
        user.setNom(request.getParameter("nom"));
        user.setPrenom(request.getParameter("prenom"));
        
        System.out.println(user);
        
        Boolean inscription = inscrireEleve(user,request.getParameter("code"));
            
        
        request.setAttribute("confirmation", inscription);
        
    }
}
