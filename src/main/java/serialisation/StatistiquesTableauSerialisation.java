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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.StatistiqueTableauDeBord;

/**
 *
 * @author fjourda
 */
public class StatistiquesTableauSerialisation extends Serialisation{
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        StatistiqueTableauDeBord stats = (StatistiqueTableauDeBord)req.getAttribute("statistiques");
        
        System.out.println(stats);

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject statistique = new JsonObject();
        statistique.addProperty("nombreDeCours", stats.getNombreDeCours());
        statistique.addProperty("noteMoyenne", stats.getNoteMoyenne());
        if (stats.getMeilleurIntervenant() != null){
            statistique.addProperty("meilleurIntervenantNom", stats.getMeilleurIntervenant().getNom());
            statistique.addProperty("meilleurIntervenantPrenom", stats.getMeilleurIntervenant().getPrenom());
        }
        else {
            statistique.addProperty("meilleurIntervenantNom", "aucun");
            statistique.addProperty("meilleurIntervenantPrenom", "");
        }
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(statistique));
        out.close();  
    }
}
