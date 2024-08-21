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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Soutien;

/**
 *
 * @author fjourda
 */
public class HistoriqueSerialisation {
        public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
            
             List<Soutien> hist = ( List<Soutien> )req.getAttribute("historique");
            
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
            JsonObject container = new JsonObject();
    //      {"connexion":true, "utilisateur": {"id": 1024,"nom": "Lovelace", "prenom": "Ada", "mail" : ada.lovelace@insa-lyon.fr}}
            
            Integer temp = 0;
            for (Soutien soutien : hist){
                
                JsonObject histObject = new JsonObject();
                histObject.addProperty("id", soutien.getId());
                histObject.addProperty("autoEval", soutien.getAutoEval());
                histObject.addProperty("Bilan", soutien.getBilan());
                histObject.addProperty("description", soutien.getDescription());
                histObject.addProperty("matière", soutien.getMatiere());
                histObject.addProperty("statut", soutien.getStatus());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = soutien.getDateSoutien();
                String strDate = sdf.format(date);
                histObject.addProperty("date", strDate);
                histObject.addProperty("intervenantNom", soutien.getIntervenant().getNom());
                histObject.addProperty("intervenantPrenom", soutien.getIntervenant().getPrenom());
                histObject.addProperty("eleveNom", soutien.getEleve().getPrenom());
                histObject.addProperty("elevePrenom", soutien.getEleve().getNom());
             
                String id = "soutien"+temp;
                container.add(id,histObject);
                temp = temp+1;
            }

            res.setContentType("application/json;charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println(gson.toJson(container));
            out.close();  
            
        }

}
