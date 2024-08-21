/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;
import metier.modele.Etablissement;
import metier.modele.StatistiqueTableauDeBord;
import static metier.service.Service.calculerStatistiquesTableauDeBord;

/**
 *
 * @author fjourda
 */
public class CalculStatAction extends Action{

    public CalculStatAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        
        Etablissement etablissement = new Etablissement();
        System.out.println(request.getParameter("id"));
        Long id = Long.valueOf(request.getParameter("id"));
        etablissement.setId(id);
        String code = (String)request.getParameter("code");
        etablissement.setCode(code);
        Double ips = Double.valueOf(request.getParameter("ips"));
        etablissement.setIPS(ips);
        
        StatistiqueTableauDeBord stat = calculerStatistiquesTableauDeBord(etablissement);
        request.setAttribute("statistiques", stat);
        
    }
}