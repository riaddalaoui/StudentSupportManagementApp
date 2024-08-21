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

/**
 *
 * @author fjourda
 */
public class VerifyIntervenantConnectedAction extends Action {

    public VerifyIntervenantConnectedAction() {
    }
    
    @Override
    public void execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        Integer status = 0;
        Integer connected = 0;
        try {
            if ( (Intervenant)session.getAttribute("utilisateur") != null) {
                connected = 1;
            }
            if ((Soutien)session.getAttribute("soutien") != null){
                status = 1;
                request.setAttribute("soutien",(Soutien)session.getAttribute("soutien"));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        request.setAttribute("status",status);
        request.setAttribute("connected",connected);

    }
}

