/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Matiere;
import static metier.service.Service.getListMatieres;

/**
 *
 * @author fjourda
 */
public class GetMatieresAction extends Action{
    public GetMatieresAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request){
        List<Matiere> matieres = getListMatieres();
        request.setAttribute("matieres", matieres);        
    }

}
