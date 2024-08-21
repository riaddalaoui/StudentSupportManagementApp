/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import metier.modele.StatistiqueAccueil;
import static metier.service.Service.calculerStatistiquesAccueil;

/**
 *
 * @author fjourda
 */
public class GetStatistiquesAccueilAction extends Action{

    public GetStatistiquesAccueilAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        StatistiqueAccueil stat = calculerStatistiquesAccueil();
        request.setAttribute("statistiques", stat);
        
    }
}
