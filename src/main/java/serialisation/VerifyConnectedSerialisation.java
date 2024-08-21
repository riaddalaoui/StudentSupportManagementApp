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
import metier.modele.Soutien;

/**
 *
 * @author fjourda
 */
public class VerifyConnectedSerialisation {
        public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        Integer status = (Integer)req.getAttribute("status");
        Integer connected = (Integer)req.getAttribute("connected");
       
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject obj = new JsonObject();
        obj.addProperty("status", status);
        obj.addProperty("connected", connected);
        
  
        JsonObject histObject = new JsonObject();

        if(status==1){
            Soutien soutien = (Soutien)req.getAttribute("soutien");
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
            histObject.addProperty("intervenantId", soutien.getIntervenant().getId());
            histObject.addProperty("eleveNom", soutien.getEleve().getNom());
            histObject.addProperty("elevePrenom", soutien.getEleve().getPrenom());
            histObject.addProperty("niveau", soutien.getEleve().getClasse());
            histObject.addProperty("etablissement", soutien.getEleve().getEtablissement().getCode());
        }
        obj.add("soutien", histObject);
        
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(obj));
        out.close(); 
        }
}
