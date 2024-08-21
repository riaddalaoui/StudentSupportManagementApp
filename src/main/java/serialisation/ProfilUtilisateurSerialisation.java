/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Eleve;
import metier.modele.Intervenant;


/**
 *
 * @author fjourda
 */
public class ProfilUtilisateurSerialisation extends Serialisation{
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
          
        
        if ("Intervenant".equals((String)req.getAttribute("type"))){
            Intervenant utilisateur = (Intervenant)req.getAttribute("utilisateur");
            
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonObject user = new JsonObject();
            JsonObject container = new JsonObject();
    //      {"connexion":true, "utilisateur": {"id": 1024,"nom": "Lovelace", "prenom": "Ada", "mail" : ada.lovelace@insa-lyon.fr}}
            user.addProperty("id", utilisateur.getId());
            user.addProperty("nom", utilisateur.getNom());
            user.addProperty("prenom", utilisateur.getPrenom());
            user.addProperty("mail", utilisateur.getEmail());
            user.addProperty("numTel", utilisateur.getNumTel());
            user.addProperty("nombreInterventions", utilisateur.getNombreInterventions());
            user.addProperty("niveauMin", utilisateur.getNiveauMin());
            user.addProperty("niveauMax", utilisateur.getNiveauMax());
            user.addProperty("enVisio", utilisateur.isEnVisio());
            
            container.addProperty("connexion", true);
            container.addProperty("type", "Intervenant");
            container.add("utilisateur",user);

            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println(gson.toJson(container));
            out.close();  
        }
        else if ("Eleve".equals((String)req.getAttribute("type"))){
            Eleve utilisateur = (Eleve)req.getAttribute("utilisateur");
            
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonObject user = new JsonObject();
            JsonObject container = new JsonObject();
            user.addProperty("id", utilisateur.getId());
            user.addProperty("nom", utilisateur.getNom());
            user.addProperty("prenom", utilisateur.getPrenom());
            user.addProperty("mail", utilisateur.getEmail());
            user.addProperty("classe", utilisateur.getClasse());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = utilisateur.getDateNaissance();
            System.out.println(date);
            System.out.println(utilisateur.getEmail());
            String strDate = sdf.format(date);
            System.out.println("test");
            user.addProperty("dateNaissance", strDate);

            user.addProperty("code", utilisateur.getEtablissement().getCode());            
            container.addProperty("connexion", true);
            container.addProperty("type", "Eleve");
            container.add("utilisateur",user);

            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println(gson.toJson(container));
            out.close();              
        }
        else {
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonObject container = new JsonObject();
            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println(gson.toJson(container));
            out.close();  
        }
   
    }
}
