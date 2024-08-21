/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialisation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.StatistiqueAccueil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author fjourda
 */
public class StatistiqueAccueilSerialisation extends Serialisation{
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        StatistiqueAccueil stats = (StatistiqueAccueil)req.getAttribute("statistiques");
        
        System.out.println(stats);

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject statistique = new JsonObject();
        statistique.addProperty("nbCours", stats.getNombreCours());
        statistique.addProperty("nbEleves", stats.getNombreEleve());
        statistique.addProperty("nbIntervenants", stats.getNombreIntervenants());

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(statistique));
        out.close();  
    }
}
