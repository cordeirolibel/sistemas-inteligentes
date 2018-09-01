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
    Problema prob; // formulacao do problema
    Estado estAtu; // guarda o estado atual (posição atual do agente)
    /* @todo T2: fazer uma sequencia de acoes a ser executada em deliberar
       e armazena-la no atributo plan[]
    */
    int plan[];
    double custo;
    static int ct = -1;
           
    public Agente(Model m) {
        this.model = m;
        prob = new Problema();
        
        //@todo T2: Aqui vc deve preencher a formulacao do problema 
        
        //@todo T2: crencas do agente a respeito do labirinto OK
        prob.criarLabirinto(9, 9);
        prob.crencaLabir.porParedeVertical(0, 1, 0);
        prob.crencaLabir.porParedeVertical(0, 0, 1);
        prob.crencaLabir.porParedeVertical(5, 8, 1);
        prob.crencaLabir.porParedeVertical(5, 5, 2);
        prob.crencaLabir.porParedeVertical(8, 8, 2);
        prob.crencaLabir.porParedeHorizontal(4, 7, 0);
        prob.crencaLabir.porParedeHorizontal(7, 7, 1);
        prob.crencaLabir.porParedeHorizontal(3, 5, 2);
        prob.crencaLabir.porParedeHorizontal(3, 5, 3);
        prob.crencaLabir.porParedeHorizontal(7, 7, 3);
        prob.crencaLabir.porParedeVertical(6, 7, 4);
        prob.crencaLabir.porParedeVertical(5, 6, 5);
        prob.crencaLabir.porParedeVertical(5, 7, 7);
        
        
        //@todo T2: crencas do agente: Estado inicial, objetivo e atual
        // utilizar atributos da classe Problema OK
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 8);
    }
    
    /**Escolhe qual ação (UMA E SOMENTE UMA) será executada em um ciclo de raciocínio
     * @return 1 enquanto o plano não acabar; -1 quando acabar
     */
    public int deliberar() {
    	
        ct++;
        //@todo T2: executar o plano de acoes: SOMENTE UMA ACAO POR CHAMADA DESTE METODO
        // Ao final do plano, verifique se o agente atingiu o estado objetivo verificando
        // com o teste de objetivo
        
        //criando plano de acoes
        if (ct==0) {
        	this.plan = new int[11];
        	this.plan[0] = N;
        	this.plan[1] = N;
        	this.plan[2] = N;
        	this.plan[3] = NE;
        	this.plan[4] = L;
        	this.plan[5] = L;
        	this.plan[6] = L;
        	this.plan[7] = L;
        	this.plan[8] = NE;
        	this.plan[9] = NE;
        	this.plan[10] = L;
        }
        
        
        int acaoPlan = this.plan[ct];
        Estado posAtual = this.sensorPosicao();
        
        //proxima posicao
        Estado posSuc = this.prob.suc(posAtual, acaoPlan);
        
        //soma custo
        this.custo += this.prob.obterCustoAcao(posAtual,acaoPlan,posSuc);
        
        //acoes possiveis
        int possiveis[];
        possiveis = this.prob.acoesPossiveis(posAtual);
                
        //@todo T2: imprimir o que foi pedido
        System.out.printf("estado atual: %d,%d\n",posAtual.getLin(),posAtual.getCol());
        System.out.printf("açoes possiveis: { ");
        for(int n = 0; n<8; n++)
        {
            if(possiveis[n]!= -1)
                System.out.printf("%s ", acao[n]);
        }
        System.out.printf("}\n");
        System.out.printf("ct = %d de %d ação escolhida=%s\n",ct,plan.length-1,acao[acaoPlan]);
        System.out.printf("custo ate o momento: %.1f\n\n",this.custo);
        
        executarIr(plan[ct]); 
       
        //Testar objetivo
        if (this.prob.testeObjetivo(this.sensorPosicao()) == true)
           return -1;
        
        return 1;
    }
    
    /**Funciona como um driver ou um atuador: envia o comando para
     * agente físico ou simulado (no nosso caso, simulado)
     * @param direcao N NE S SE ...
     * @return 1 se ok ou -1 se falha
     */
    public int executarIr(int direcao) {
        model.ir(direcao);
        return 1; 
    }   
    
    // Sensor
    public Estado sensorPosicao() {
        int pos[];
        pos = model.lerPos();
        return new Estado(pos[0], pos[1]);
    }
}    

