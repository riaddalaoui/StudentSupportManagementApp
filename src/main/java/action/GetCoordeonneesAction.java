/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.google.maps.model.LatLng;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Etablissement;
import static metier.service.Service.obtenirMapEtablissementLatLng;

/**
 *
 * @author fjourda
 */
public class GetCoordeonneesAction extends Action {
    public GetCoordeonneesAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        Map<LatLng, Etablissement> map = obtenirMapEtablissementLatLng();
        request.setAttribute("map", map);
    }
    
}
