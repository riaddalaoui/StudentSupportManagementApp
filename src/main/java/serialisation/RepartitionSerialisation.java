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

/**
 *
 * @author fjourda
 */
public class RepartitionSerialisation extends Serialisation{
    public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject container = new JsonObject();


        Double[] repartition = (Double[])req.getAttribute("repartition");  

        for (int i=0; i < repartition.length; i++){
            String temp = String.valueOf(i);
            container.addProperty(temp,repartition[i]);
        }
        
        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(container));
        out.close(); 
    }
}