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
public class ConfirmationSerialisation extends Serialisation{
        public void appliquer(HttpServletRequest req, HttpServletResponse res) throws IOException {
        
        Boolean conf = (Boolean)req.getAttribute("confirmation");
        
        System.out.println(conf);

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonObject obj = new JsonObject();
        obj.addProperty("confirmation", conf);
        System.out.println("confirmation :");
        System.out.println(obj);

        res.setContentType("application/json;charset=UTF-8");
        PrintWriter out = res.getWriter();
        out.println(gson.toJson(obj));
        out.close(); 
        }
}
