package ClassLevel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;

public class CreateJSONFileClassLevel {
	public static final String jsonFilePathClassLevel = "C:\\Users\\Chamodi Sandeepani\\Desktop\\json_cl.json";
	private static double[] output;
	private static String[] rl;
	private List<String> packageName;
	private List<String> className;
	
	public CreateJSONFileClassLevel() {
		output = new PredictClassLevel().results;
		rl = new String[output.length];
		Analysis();
		System.out.println(Arrays.toString(rl));
		DataRead();
		Create();
	}
	
	private static void Analysis() {
		for (int i = 0; i < output.length; i++) {
			if(output[i]==1) {
				rl[i]="Medium";
			}else if(output[i]==2) {
				rl[i]="Low";
			}else if(output[i]==0) {
				rl[i]="High";
			}else {
				rl[i]="Null";
			}
		}
		
	}
	
	private void Create() {
		
			
			try (FileWriter file = new FileWriter(jsonFilePathClassLevel)) {
				for (int i = 0; i < output.length; i++) {
					JSONObject obj = new JSONObject();
					obj.put("package", packageName.get(i));
					obj.put("class", className.get(i));
					obj.put("reusabilityLevel", rl[i]);
					
					file.write(obj.toJSONString());
					file.flush();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		
			
	}
	
	private void DataRead() {
		
        String csvFile = "testDataClassLevel.csv";
        BufferedReader br = null;
        
        String line = "";
        String cvsSplitBy = ",";
        
        packageName = new ArrayList<String>();  
        className = new ArrayList<String>(); 
        try {
            br = new BufferedReader(new FileReader(csvFile));
            line = br.readLine();
            while ((line = br.readLine()) != null) {              
                Object[] data = line.split(cvsSplitBy);
                System.out.println("1 :" + data[0] + " , 2 :" + data[1] );  
                packageName.add((String)data[0]);
                className.add((String)data[1]);                          
            }
                        	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
