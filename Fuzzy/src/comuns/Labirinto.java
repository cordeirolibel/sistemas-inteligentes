package comuns;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import comuns.*;

/**Labirinto representa um labirinto com paredes. A indexação das posições do
 * labirinto é dada por par ordenado (linha, coluna). A linha inicial é zero e
 * a linha máxima é (maxLin - 1). A coluna inicial é zero e a máxima é 
 * (maxCol - 1).
 *
 * @author Tacla 
 */
public class Labirinto {
	
	private static final int SEED = 42;
	
    /*Array que representa o labirinto sendo as posições = 1 aquelas que 
      contêm paredes */
    public int parede[][]; 
    public Fruta frutas[][]; 

    /*Número máximo de colunas do labirinto */
    private final int maxCol;      
    /*Número máximo de linhas do labirinto */
    private final int maxLin;     
    /*gerador de numeros randomicos*/
    private Random gerador;
    
    private Text_Frutas text_Frutas;
    
    public Labirinto(int maxLinhas, int maxColunas)  {
        this.maxCol = maxColunas;
        this.maxLin = maxLinhas;
        parede = new int[maxLin][maxCol];
        frutas = new Fruta[maxLin][maxCol];

        gerador = new Random(SEED);
        
        try {
        	text_Frutas = new Text_Frutas("frutasLabirinto.txt");
        	
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        
    }
    
    public int getMaxLin() {
        return this.maxLin;
    }
    public int getMaxCol() {
        return this.maxCol;
    }
    
   /** Constroi parede horizontal da coluna inicial até a final na linha indicada.
    * @param ini: coluna inicial entre 0 e número máximo de colunas - 1
    * @param fim: coluna final (deve ser maior que coluna inicial)
    * @param linha: em qual linha colocar a parede (entre 0 e máx. de linhas - 1)
    */
    public void porParedeHorizontal(int ini, int fim, int linha) {
        if (fim >= ini && ini >= 0 && fim < maxCol && linha >= 0 && linha < maxLin) {
            for (int c = ini; c <= fim; c++) {
                parede[linha][c] = 1;
            }
        }
    }
    /** Constroi parede vertical da linha inicial até a final na coluna indicada.
     * até a final 
    * @param ini: linha inicial entre 0 e  máximo de linhas - 1
    * @param fim: linha final (deve ser maior do que linha inicial)
    * @param coluna: em qual coluna colocar a parede (entre 0 e máx. de colunas - 1)
    */
    public void porParedeVertical(int ini, int fim, int coluna) {
        if (fim >= ini && ini >= 0 && fim < maxLin && coluna >= 0 && coluna < maxCol) {
            for (int l = ini; l <= fim; l++) {
                parede[l][coluna] = 1;
            }
        } 
    }
    
    /**
     *  Adiciona pardes para um tipo de labirinto padrao
     */
    public void paredesPadrao() {
    	this.porParedeVertical(0, 1, 0);
    	this.porParedeVertical(0, 0, 1);
    	this.porParedeVertical(6, 8, 1);
    	this.porParedeVertical(5, 5, 2);
    	this.porParedeVertical(8, 8, 2);
    	this.porParedeHorizontal(4, 7, 0);
    	this.porParedeHorizontal(3, 5, 2);
    	this.porParedeHorizontal(3, 6, 3);
    	this.porParedeVertical(6, 7, 4);
    	this.porParedeVertical(5, 6, 5);
    	this.porParedeVertical(5, 7, 7);
    }
    
    /** 
     * retorna uma posicao randomica que nao eh parede
     */
    public int[] randPos() {
    	int col, lin;
    	
    	//procura enquanto so achar parede
    	do {
	    	col = this.gerador.nextInt(this.getMaxCol());
	    	lin = this.gerador.nextInt(this.getMaxLin());
    	}while(this.parede[lin][col] == 1);
    	
    	int []pos = {col,lin};
    	return pos;
    }

    /** 
     * adiciona frutas randomicas
     */
    public void randFruits() {
    	//coloca uma fruta em cada posicao q nao eh parede
    	for (int l = 0; l < maxLin; l++) {
    		for (int c = 0; c < maxCol; c++){
    			if(parede[l][c]==0) {
    				this.frutas[l][c] = this.text_Frutas.get_fruta();
    			}
    		}
    	}
    }
    
}
