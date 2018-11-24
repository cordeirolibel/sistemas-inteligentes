/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import ambiente.*;

/**
 *
 * @author tacla
 */
public class Main {
    public static void main(String args[]) {
    	
    	int estrategia = 1;//0 randomico, 1 fuzzy e 2 non-lethal
     	int  plano = 1; // 0=custo uniforme, 1=A*-H1, 2=A*-H2
    	
    	
        // Cria o ambiente (modelo) = labirinto com suas paredes
        Model model = new Model(9, 9);
        model.labir.paredesPadrao();
        
        // Cria um agente
        Agente ag;
        
        
        // agente escolhe proxima açao e a executa no ambiente (modificando
        // o estado do labirinto porque ocupa passa a ocupar nova posicao)
        float pontuacao, mediaPontuacao = 0;
        int num_sucesso = 0;
        
        System.out.println("\n*** Inicio do ciclo de raciocinio dos agentes ***\n");
        for(int i=0;i<100;i++) {
    
        	model.randPosObjFruits();
        	ag = new Agente(model);
                //model.desenhar(true);
        	
	        while (ag.deliberar(0,estrategia) != -1) {  
                    //model.desenhar(true);
                }
	        
	        pontuacao = ag.getPontuacao(); 
	        mediaPontuacao += pontuacao;
	        if (pontuacao<50){
	        	num_sucesso += 1;
	        }
	        
	        System.out.printf("ID: %d",i+1);
	        System.out.printf("\tPontuacao: %.1f ",pontuacao);
	        System.out.printf("\tBaseline: %d ",estrategia);
	        System.out.println();
        }
        
        System.out.println("-----------------------------------");
        System.out.printf("Numero de Sucessos:\t%d/%d\n",num_sucesso,100);
        System.out.printf("Pontuacao Media:\t%.2f\n",mediaPontuacao/100);
     
    }
    
    
    
}
