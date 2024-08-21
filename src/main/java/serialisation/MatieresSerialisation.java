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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import metier.modele.Matiere;

/**
 *
 * @author fjourda
 */
public class MatieresSerialisation {
        public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        List<Matiere> matieres = (List<Matiere>)req.getAttribute("matieres");
        
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();
        for (Matiere matiere : matieres){
            String id = Long.toString(matiere.getId());
            container.addProperty(id, matiere.getMatiere());
        }

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(container));
        out.close();  
    }
}
