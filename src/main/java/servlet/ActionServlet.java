package servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import action.AuthentifierUtilisateurAction;
import action.BilanSoutienAction;
import action.CalculStatAction;
import action.CreateSoutienAction;
import action.DemarrerSoutienAction;
import action.GetCoordeonneesAction;
import action.GetHistoriqueEleveAction;
import action.GetHistoriqueIntervenantAction;
import action.GetMatieresAction;
import action.GetRepartitionAction;
import action.GetStatistiquesAccueilAction;
import action.InscrireUtilisateurAction;
import action.NoterSoutienAction;
import action.TerminerSoutienAction;
import action.VerifyIntervenantConnectedAction;
import action.VerifyStudentConnectedAction;
import action.GetSoutienAction;
import dao.JpaUtil;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Soutien;
import serialisation.ConfirmationSerialisation;
import serialisation.HistoriqueSerialisation;
import serialisation.MatieresSerialisation;
import serialisation.ProfilUtilisateurSerialisation;
import serialisation.RepartitionSerialisation;
import serialisation.SoutienSerialisation;
import serialisation.StatistiqueAccueilSerialisation;
import serialisation.StatistiquesTableauSerialisation;
import serialisation.TableauDeBordSerialisation;
import serialisation.VerifyConnectedSerialisation;


/**
 *
 * @author fjourda
 */
@WebServlet(urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {
    
    
    @Override
    public void init() throws ServletException {
        super.init();
        JpaUtil.creerFabriquePersistance();
    }

    @Override
    public void destroy() {
        JpaUtil.fermerFabriquePersistance();
        super.destroy();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HttpSession session = request.getSession(true);
            
            
            /* TODO output your page here. You may use following sample code. */
            String todo = request.getParameter("todo");
            System.out.println("Trace : todo = " + todo);
            
            System.out.println("[TEST] Appel de l’ActionServlet ");
            switch(todo) {
                case "connecter" : {
                    new AuthentifierUtilisateurAction().execute(request);
                    
                    if("Eleve".equals((String)request.getAttribute("type"))){
                        Eleve utilisateur = (Eleve) request.getAttribute("utilisateur");
                        session.setAttribute("utilisateur", utilisateur);
                    }
                    if("Intervenant".equals((String)request.getAttribute("type"))){
                        Intervenant utilisateur = (Intervenant) request.getAttribute("utilisateur");
                        session.setAttribute("utilisateur", utilisateur);
                    }                    
                    
                    new ProfilUtilisateurSerialisation().appliquer(request, response);
                    break;
                }
                case "disconnect" : {
                    session.setAttribute("utilisateur", null);
                    session.setAttribute("soutien", null);
                    break ;
                }
                case "getStatisticsAccueil" : {
                    new GetStatistiquesAccueilAction().execute(request);
                    new StatistiqueAccueilSerialisation().appliquer(request,response);
                    break;
                }
                case "inscrire" : {
                    new InscrireUtilisateurAction().execute(request);
                    new ConfirmationSerialisation().appliquer(request, response);
                    break;
                }
                case "getHistoryStudents" : {
               
                    new GetHistoriqueEleveAction().execute(request);
                    new HistoriqueSerialisation().appliquer(request,response);
                    break;
                }                
                case "getHistoryIntervenant" : {
      
                    new GetHistoriqueIntervenantAction().execute(request);
                    new HistoriqueSerialisation().appliquer(request,response);
                    break;
                }
                case "getMatieres" : {
                    new GetMatieresAction().execute(request);
                    new MatieresSerialisation().appliquer(request,response);
                    break;
                }
                case "creerSoutien" : {
                    new CreateSoutienAction().execute(request);

                    if (Boolean.TRUE.equals((Boolean) request.getAttribute("confirmation"))){
                        Soutien soutien = (Soutien) request.getAttribute("soutien");
                        session.setAttribute("soutien", soutien);
                    }
                    new ConfirmationSerialisation().appliquer(request,response);
                    break;
                }
                case "terminerVisio" : {
                    new TerminerSoutienAction().execute(request);
                    new ConfirmationSerialisation().appliquer(request,response);
                    break;
                }
                case "notation" : {
                    new NoterSoutienAction().execute(request);
                    if ((Boolean) request.getAttribute("confirmation")==true){
                        session.setAttribute("soutien", null);
                    }
                    new ConfirmationSerialisation().appliquer(request,response);
                    break;
                }
                case "verifyStudentConnected" : {
                    new VerifyStudentConnectedAction().execute(request);
                    new VerifyConnectedSerialisation().appliquer(request,response);
                    break;
                }                
                case "verifyIntervenantConnected" : {
                    new VerifyIntervenantConnectedAction().execute(request);
                    new VerifyConnectedSerialisation().appliquer(request,response);
                    break;
                }
                case "getSoutien" : {
                    new GetSoutienAction().execute(request);
                    if (Boolean.TRUE.equals((Boolean) request.getAttribute("confirmation"))){
                        Soutien soutien = (Soutien) request.getAttribute("soutien");
                        session.setAttribute("soutien", soutien);
                    }
                    new SoutienSerialisation().appliquer(request,response);
                    break;
                }     
                case "demarrerSoutien":{
                    new DemarrerSoutienAction().execute(request);
                    new ConfirmationSerialisation().appliquer(request,response);
                    break;
                }
                case "bilan" : {
                    new BilanSoutienAction().execute(request);
                    if ((Boolean) request.getAttribute("confirmation")==true){
                        session.setAttribute("soutien", null);
                    }
                    new ConfirmationSerialisation().appliquer(request,response);
                    break;
                }
                case "getStatistics" : {
                    new GetCoordeonneesAction().execute(request);
                    new TableauDeBordSerialisation().appliquer(request,response);
                    break;
                }
                case "displayRepartition" : {
                    new GetRepartitionAction().execute(request);
                    new RepartitionSerialisation().appliquer(request,response);
                    break;
                }
                case "calculStat" : {
                    new CalculStatAction().execute(request);
                    new StatistiquesTableauSerialisation().appliquer(request,response);
                    break;
                }
                default :
                {
                    break;
                }
            }
        }
    }

}
