package sistema;

import ambiente.*;
import arvore.TreeNode;
import arvore.fnComparator;
import problema.*;
import comuns.*;
import static comuns.PontosCardeais.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author tacla
 */
public class Agente implements PontosCardeais {
    /* referência ao ambiente para poder atuar no mesmo*/
    Model model;
    Problema prob;
    Estado estAtu; // guarda o estado atual (posição atual do agente)
    int plan[];
    double custo;
    static int ct = -1;
           
    public Agente(Model m) {
        this.model = m; 
        
        // Posiciona o agente fisicamente no ambiente na posicao inicial
        model.setPos(8,0);
    }
    
     /**
     * Agente escolhe qual ação será executada em um ciclo de raciocínio. 
     */
    public int deliberar() {
                
        //  contador de acoes
        ct++;
        System.out.println("Turno:"+ct);
        if (ct == 20)
        {
        	return -1;
        }
        
        // @todo a cada acao escolher uma acao {N, NE, L, SE, S, SO, O, NO} 
        Estado posicaoAtual = sensorPosicao();
        // termina se chegou no alvo
        
        int decideacao = -1;
        if(posicaoAtual.getLin()+1 <= model.labir.getMaxLin()-1 && posicaoAtual.getCol()+1 <= model.labir.getMaxCol()-1)
        {
           if(model.labir.parede[posicaoAtual.getLin()+1][posicaoAtual.getCol()+1] != 1)
            {
                decideacao = SE;
            } 
        }
        if(posicaoAtual.getLin()+1 <= model.labir.getMaxLin()-1)
        {
            if (model.labir.parede[posicaoAtual.getLin()+1][posicaoAtual.getCol()] != 1)
            {
                decideacao = S;
            }
        }
        if(posicaoAtual.getLin()-1 >= 0 && posicaoAtual.getCol()-1 >=0)
        {
            if (model.labir.parede[posicaoAtual.getLin()-1][posicaoAtual.getCol()-1] != 1)
            {
                decideacao = NO;
            }
        }
        if(posicaoAtual.getLin()+1 <= model.labir.getMaxLin()-1 && posicaoAtual.getCol()-1 >=0)
        {
           if (model.labir.parede[posicaoAtual.getLin()+1][posicaoAtual.getCol()-1] != 1)
            {
                decideacao = SO;
            } 
        }
        if(posicaoAtual.getCol()-1 >= 0)
        {
            if (model.labir.parede[posicaoAtual.getLin()][posicaoAtual.getCol()-1] != 1)
            {
                decideacao = O;
            }
        }
        if(posicaoAtual.getLin()-1 >= 0 && posicaoAtual.getCol()+1 <= model.labir.getMaxCol()-1) 
        {
            if (model.labir.parede[posicaoAtual.getLin()-1][posicaoAtual.getCol()+1] != 1)
            {
                decideacao = NE;
            }
        }
        if(posicaoAtual.getLin()-1 >= 0)
        {
            if (model.labir.parede[posicaoAtual.getLin()-1][posicaoAtual.getCol()] != 1)
            {
                decideacao = N;
            }
        }
        if(posicaoAtual.getCol()+1 <= model.labir.getMaxCol()-1)
        {
            if (model.labir.parede[posicaoAtual.getLin()][posicaoAtual.getCol()+1] != 1)
            {
                decideacao = L;
            }
        }
        
        //nao consegue se mover
        if (decideacao == -1) 
        {
        	return -1;
        }
        
        System.out.println(decideacao);
        executarIr(decideacao); 
        
        return 1; // Se retornar -1, encerra o agente
    }
    
    /**
    * Atuador: solicita ao agente 'fisico' executar a acao. 
    */
    public int executarIr(int direcao) {
        model.ir(direcao);
        return 1; // deu certo
    }

    /**
     * Simula um sensor que realiza a leitura da posição atual no ambiente e
     * traduz para um par de coordenadas armazenadas em uma instância da classe
     * Estado.
     * @return Estado um objeto que representa a posição atual do agente no labirinto 
     */
    public Estado sensorPosicao() {
        int pos[];
        pos = model.lerPos();
        return new Estado(pos[0], pos[1]);
    }
    
}