/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Intervenant;
import metier.modele.Soutien;
import static metier.service.Service.obtenirHistorique;


/**
 *
 * @author fjourda
 */
public class GetHistoriqueIntervenantAction extends Action{

    public GetHistoriqueIntervenantAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Intervenant utilisateur = (Intervenant) session.getAttribute("utilisateur");
        List<Soutien> hist = obtenirHistorique(utilisateur);
        request.setAttribute("historique", hist);
        
    }
}
