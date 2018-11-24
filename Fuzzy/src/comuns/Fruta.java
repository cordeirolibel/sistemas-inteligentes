package comuns;

public class Fruta {
	private char cores[];
	private char energia;

	public Fruta(char[] cores, char energia) {
		this.cores = cores;
		this.energia = energia;
	}
	
	
	public char[] getCores() {
		return this.cores;
	}
	
	public char getEnergia() {
		return this.energia;
	}
	
	public void print() {
		System.out.print(cores);
		System.out.println(energia);
	}
	
	
}
