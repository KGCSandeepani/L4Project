package ClassLevel;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Integration {

	static String visualFilePath ="C:\\Users\\Chamodi Sandeepani\\Desktop\\visual.json";
	static String rlFilePath ="C:\\Users\\Chamodi Sandeepani\\Desktop\\visualJson.json";
	static String finalFilePath ="C:\\Users\\Chamodi Sandeepani\\Desktop\\final.json";
	
	public Integration() {
		try {
			integration();
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void integration() throws IOException, JSONException {
        
        String ns="namespace", n="name", p="package", c="class", rl="reusabilityLevel";
        
		InputStream is = /*JSONString.class.getResourceAsStream*/new FileInputStream(visualFilePath);
		if (is == null) {
		    throw new NullPointerException("Cannot find resource file " );
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		JSONTokener tokener = new JSONTokener(in);
		JSONArray ja = new JSONArray(tokener);
		
		InputStream isl = new FileInputStream(rlFilePath);
		if (isl == null) {
		    throw new NullPointerException("Cannot find resource file " );
		}
		BufferedReader inl = new BufferedReader(new InputStreamReader(isl));
		JSONTokener tokenerl = new JSONTokener(inl);
		JSONArray jal = new JSONArray(tokenerl);
		
		new FileWriter(finalFilePath).close();
		
		
		for(int i=0;i<ja.length();i++) {
			JSONObject ro = (JSONObject) ja.get(i);
			
			for(int j=0;j<jal.length();j++) {
				
				JSONObject rol = (JSONObject) jal.get(j);
				//System.out.println("vvvv ---->" + ro.getString(ns).replace("\\","."));
				
				if((ro.getString(ns).replace("\\",".")).equals(rol.getString(p)) && (ro.getString(n)+".java").equals(rol.getString(c))) {
		        	System.out.println("Vinno_Read---->" + ro.getString(n) +" : " + rol.getString(p));
		        	ro.put("rl",rol.getString(rl));

		            //Write into the file	
		        	try(FileWriter file = new FileWriter(finalFilePath, true)){
		                file.write(ro.toString());
		                System.out.println("Successfully updated json object to file...!!");
		        	}
		        	break;
		        }
		    }
		}

    }
}

