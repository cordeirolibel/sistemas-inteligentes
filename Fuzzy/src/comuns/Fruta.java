package comuns;
import java.util.Random;
import comuns.*;
import static comuns.Cor.*;

public class Fruta {
	private Cor cores[];
	private int energia;

	public Fruta(Cor[] cores, int energia) {
		this.cores = cores;
		this.energia = energia;
	}
	
	
	public Cor[] getCores() {
		return this.cores;
	}
	
	public int getEnergia() {
		return this.energia;
	}
}
