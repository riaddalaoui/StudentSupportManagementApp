/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import static metier.service.Service.obtenirRepartitionEleve;

/**
 *
 * @author fjourda
 */
public class GetRepartitionAction extends Action{
    public GetRepartitionAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {

        
        HttpSession session = request.getSession();
        Double[] repartition = obtenirRepartitionEleve();
        System.out.println(Arrays.toString(repartition));        
        request.setAttribute("repartition", repartition);
    }
}
