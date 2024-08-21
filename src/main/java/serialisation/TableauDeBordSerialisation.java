/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serialisation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Etablissement;

/**
 *
 * @author fjourda
 */
public class TableauDeBordSerialisation extends Serialisation{
        public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Map<LatLng, Etablissement> map = (Map<LatLng, Etablissement>)req.getAttribute("map");
       
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject listEtablissements = new JsonObject();

        
        for (Map.Entry<LatLng, Etablissement> entry : map.entrySet()){
            LatLng coord = entry.getKey();

            // Formater les coordonnées en une chaîne lisible, par exemple "lat,lng"
            String temp = coord.lat + "," + coord.lng;
            
            
            JsonObject school = new JsonObject();            
            Etablissement etablissement = entry.getValue();

            school.addProperty("id", etablissement.getId());
            school.addProperty("code", etablissement.getCode());
            school.addProperty("IPS", etablissement.getIPS());

            listEtablissements.add(temp, school);

        }
        
        
        
        
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(listEtablissements));
        out.close(); 
        }
}
   