package comuns;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class Text_Frutas {
	private LineNumberReader lnr;
	
	public Text_Frutas(String dir ) throws FileNotFoundException{
		
		File file = new File(dir); 
		
        BufferedReader br = new BufferedReader(new FileReader(file));
        this.lnr = new LineNumberReader(br);
  
	}
	
	public Fruta get_fruta() {
		String line; 
		
		//tenta ler uma nova linha do arquivo
		try {
			line = this.lnr.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	  	if(line == null) 
	    	return null;
	  	char [] cores = {line.charAt(0),line.charAt(2),line.charAt(4),line.charAt(6),line.charAt(8)};
	  	return new Fruta(cores,line.charAt(10));
		  
	}
}
