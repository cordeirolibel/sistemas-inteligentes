package sistema;

import ambiente.*;
import arvore.TreeNode;
import arvore.fnComparator;
import problema.*;
import comuns.*;
import static comuns.PontosCardeais.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner; //ler teclado


import java.util.Comparator;
import java.util.*;


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
        	//this.planoFixo();
                Scanner ler = new Scanner(System.in); //ler teclado
                int leitura;
                System.out.printf("Escolha um algoritmo:\n0 - Custo Uniforme\n1 - A* com heurística 1\n2 - A* com heurística 2\n");
                leitura = ler.nextInt();
                if (leitura==0 || leitura==1 || leitura==2)
                    this.planoDinamico(leitura); //0=custoUniforme; 1=A*heuristica1; 2=A*heuristica2
        	else 
                {
                    System.out.println("Comando não reconhecido; usando custo uniforme como padrão\n");
                    this.planoDinamico(0);
                }
                        
                    
                //return -1;
        	
        	//mostrando plano
        	System.out.print("Plano: ");
        	for(int a : plan)
        		System.out.printf("%s ", acao[a]);
        	
        	System.out.println("\n\n*** Plano Arquitetado - Inicio da Execucao! ***\n");
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
    
    // Gerador de um plano fixo - um vetor
    private boolean planoFixo() {
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
    	
    	return true;
    }
    
    // Gerador de plano 
    private boolean planoDinamico(int tipo) {
    	
        if (tipo == 0)
            System.out.println("Usando Custo Uniforme:\n");
        if (tipo == 1)
            System.out.println("Usando A* com heurísitca 1:\n");
        if (tipo == 2)
            System.out.println("Usando A* com heurísitca 2:\n");
        
        //Controle e análise
        int nosgerados = 0;
        int ct_ja_explorados = 0;
        int ct_descartados_front = 0;
        int nosgeradostemp = 0;
        int ct_ja_explorados_temp = 0;
        int ct_descartados_front_temp = 0;
        int nosgeradosmax = 0;
        int ct_ja_explorados_max = 0;
        int ct_descartados_front_max = 0;
        
        
    	//criando primeiro no ou raiz
		TreeNode tree = new TreeNode(null);
		tree.setState(this.sensorPosicao());
		tree.setGn(0);//zero para o primeiro
                if (tipo == 0)
                    tree.setHn(0);
                if (tipo == 1)
                    tree.setHn(H1(this.sensorPosicao()));
                if (tipo == 2)
                    tree.setHn(H2(this.sensorPosicao()));
		
		
                nosgerados++; //raiz
                                
		//criando fila ordenada - fronteira
		Comparator<TreeNode> comparator = new fnComparator();
		PriorityQueue<TreeNode> queue = new PriorityQueue<TreeNode>(comparator);
		queue.add(tree);
		
		//criando visitados ou explorados
    	//por padrao a matriz eh zerada
    	int visitados[][];
		visitados = new int [this.prob.crencaLabir.getMaxLin()][this.prob.crencaLabir.getMaxCol()];
		
		//procurando caminho no universo de estados
		TreeNode no;
		Estado est_no, est_no_filho;
		while(queue.size()!=0) {
			
                    if(nosgeradostemp>nosgeradosmax)
                        nosgeradosmax = nosgeradostemp;
                    if(ct_ja_explorados_temp>ct_ja_explorados_max)
                        ct_ja_explorados_max = ct_ja_explorados_temp;
                    if(ct_descartados_front_temp>ct_descartados_front_max)
                        ct_descartados_front_max = ct_descartados_front_temp;
                    
                    nosgeradostemp = 0;
                    ct_ja_explorados_temp = 0;
                    ct_descartados_front_temp = 0;
                    
                    
			//pegando o menor da fronteira
			no = queue.remove();
			est_no = no.getState();
			
			//testando se eh o noh objetivo
			if (this.prob.testeObjetivo(est_no)){
				//retornar a solucao
				
				int depth = no.getDepth();
				System.out.println("Achei solucao!");
				System.out.print("Custo: ");
				System.out.println(no.getFn());
				System.out.print("Passos: ");
				System.out.println(depth);
				
                                System.out.printf("Nós gerados: %d\n", nosgerados);
                                System.out.printf("Nós não inseridos: %d\n", ct_ja_explorados);
                                System.out.printf("Nós removidos da fronteira: %d\n", ct_descartados_front);
                                System.out.printf("Nós gerados (max): %d\n", nosgeradosmax);
                                System.out.printf("Nós não inseridos (max): %d\n", ct_ja_explorados_max);
                                System.out.printf("Nós removidos da fronteira (max): %d\n", ct_descartados_front_max);
                                
                                
				//criando o plano
				this.plan = new int[depth];
				int i=0;
				while(no.getParent()!=null) {
					this.plan[depth-i-1] = no.getAction();
					no = no.getParent();
					i+=1;
				}
				return true;
			}
			
			//colocando o noh como explorado
			visitados[est_no.getLin()][est_no.getCol()] = 1;
			
			//acoes possiveis ou nos filhos
	        int possiveis[];
	        possiveis = this.prob.acoesPossiveis(est_no);
	        
	        //para cada acao possivel
	        for (int acao = 0; acao < possiveis.length; acao++) {
	        	
	        	//se a acao nao for possivel
	        	if (possiveis[acao] == -1)
					continue;
	        	
	        	//criando o filho
	        	est_no_filho = this.prob.suc(est_no, acao);
	        	TreeNode no_filho  = no.addChild();
	        	no_filho.setState(est_no_filho);
                        if (tipo == 0)
                            no_filho.setHn(0);
                        if (tipo == 1)
                            no_filho.setHn(H1(est_no_filho)); 
                        if (tipo == 2)
                            no_filho.setHn(H2(est_no_filho)); 
	        	no_filho.setGn(this.prob.obterCustoAcao(est_no,acao,est_no_filho)+no.getGn());
	        	no_filho.setAction(acao);
	        	
                        nosgerados++;
                        nosgeradostemp++;
                        
	        	
	        	//procura se noh ja esta na fonteira
	        	TreeNode no_filho_em_queue = this.contains(queue,no_filho);
                        
                        if((visitados[est_no_filho.getLin()][est_no_filho.getCol()]!=0))
                        {
                            ct_ja_explorados++;
                            ct_ja_explorados_temp++;
                        }
                        
	        	//se seu filho nao foi explorado e nao esta na fronteira
	        	if((visitados[est_no_filho.getLin()][est_no_filho.getCol()]==0) &&
	        	   (no_filho_em_queue==null)) {
	        		//coloca na fronteira
	        		queue.add(no_filho);
	        	}
	        	//se estado esta na fronteira com custo maior
	        	else if(no_filho_em_queue!=null) {
	        		if(no_filho_em_queue.getFn()>no_filho.getFn()) {
	        			//substitui pelo novo
	        			queue.remove(no_filho_em_queue);
	        			queue.add(no_filho);
                                        
                                        ct_descartados_front++;
                                        ct_descartados_front_temp++;
	        		}
                                else {
                                     ct_descartados_front++;
                                     ct_descartados_front_temp++;
                                }
                                    
	        	}
	        }
		}
		
		//fracasso! Nao achou caminho
		System.out.println("Nao achei solucao!");
		return false;
    }
    
    /**Verifica se queue contem o noh
     * @param queue fila de procura
     * @param no noh a ser procurado
     * @return null se nao contem e o noh achado se contem
     */
    private TreeNode contains(PriorityQueue<TreeNode> queue, TreeNode no) {
    
    	TreeNode[] elementos = new TreeNode[queue.size()];

        // use toArrsy() method
    	TreeNode[] elementos2 = queue.toArray(elementos); 
        
        //verifica todos os nos de queue
        for(TreeNode no2: elementos) {
    		
    		if (no.equals(no2))
    			//achei o no igual
    			return no2;
    	}
        
        //nao achou noh
    	return null;
    }
    
    //Fixo: para testes
    private float H0(Estado estAtual) {
    	return 0;
    }
    
    //Distancia euclidiana simples
    //sqrt(2)<1.5  entao deve funcionar
    private float H1(Estado estAtual) {
    	Estado estObj = this.prob.getEstObj();
    	
    	int d_col = estObj.getCol()-estAtual.getCol();
    	int d_lin = estObj.getCol()-estAtual.getCol();
    	
        return (float) Math.sqrt(d_lin*d_lin+d_col*d_col);
    	
    }
    
    // Melhor caminho: vai o que der na diagonal e em linha reta o resto
    private float H2(Estado estAtual) {
    	Estado estObj = this.prob.getEstObj();
    	
    	int d_col = Math.abs(estObj.getCol()-estAtual.getCol());
    	int d_lin = Math.abs(estObj.getLin()-estAtual.getLin());
    	
    	float custo = 0;
    	custo += Math.max(d_col,d_lin)-Math.min(d_col,d_lin); //linha reta
    	custo += Math.min(d_col,d_lin)*1.5; //diagonal
    	
        return custo;
    }
    
    
}    

