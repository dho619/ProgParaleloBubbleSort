/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progParalelo_v2;

import java.util.ArrayList;                                                                                                     
import java.util.Collection;

/**
 *
 * @author Dho
 */
public class ordernar {
    
    public static void main(String[] args) throws InterruptedException{
        
        int nCpus = 256;//Runtime.getRuntime().availableProcessors();
        int numDivisao = 2;
        
        int M = 100000;
        int[] vetor = new int[M];
        
        for (int i = 0; i < M; i++){
            vetor[i] = (int) (Math.random() * M);
        }
        
        //buscaMelhorResult(vetor);
        long tempoInicial = System.currentTimeMillis();
        rodarOrdenacao(vetor, numDivisao, nCpus);
        long tempoFinal = System.currentTimeMillis();
        System.out.println("Tempo de execução: " + (tempoFinal - tempoInicial));
        
    }
    
    public static void buscaMelhorResult(int [] vetor) throws InterruptedException{
        int nCpus = 1;//Runtime.getRuntime().availableProcessors();
        int M = 100000;
        for (int i=2; i<=4; i++){
            long result = 0, resultAnt = 0, resultAux = 0;
            int nCpusAnt = 1, nCpusAux=0, limiteInf= 0, limiteSup = M, aumenta = 1;
            boolean continuar = true, novoLimiteDefinido = false;
            System.out.println("Vetor dividido em " + i + " partes:");
            System.out.println("");
            
            while (continuar) {
                resultAnt = result;
                nCpus = limiteInf + aumenta;

                long tempoInicial = System.currentTimeMillis();
                rodarOrdenacao(vetor, i, nCpus);
                long tempoFinal = System.currentTimeMillis();
                result = tempoFinal - tempoInicial;
                
                System.out.println("Rondando com " + nCpus+ " threads para cada parte, tendo como resultado: " + result);
                if (resultAux != 0) {
                    if (resultAux < result){
                        System.out.println("--------------------------------------------------------------------------------------------------------------");
                        System.out.println("Com " + nCpusAux + " threads, foi o melhor resultado obitido com o vetor dividido em " + i +" partes, tendo o resultado: " + resultAux);
                        System.out.println("--------------------------------------------------------------------------------------------------------------");
                        System.out.println("");
                        continuar = false;
                    } else{
                        resultAux = 0;
                        nCpusAux = 0; 
                    }
                } else        
                    if ((nCpus > 1) && (result > resultAnt)) {
                        limiteInf = nCpusAnt;
                        limiteSup = nCpus;
                        aumenta = 1;                    
                        novoLimiteDefinido = true;
                        //System.out.println("O melhor resultado esta entre: " + nCpusAnt + " - " + nCpus + ", divido em " + i);
                    }
                
                if((limiteSup-limiteInf) == 1){
                    System.out.println("--------------------------------------------------------------------------------------------------------------");
                    System.out.println("Com " + limiteInf + " threads, foi o melhor resultado obitido com o vetor dividido em " + i +" partes, tendo o resultado: " + resultAnt);
                    System.out.println("--------------------------------------------------------------------------------------------------------------");
                    System.out.println("");
                    continuar = false;
                }
                
                if (novoLimiteDefinido) {
                    resultAux = resultAnt;
                    nCpusAux = nCpusAnt;
                    novoLimiteDefinido = false;
                } else {
                   nCpusAnt = nCpus;
                   aumenta = aumenta * 2;
                }
            }
        }
    }
    
    public static void rodarOrdenacao(int[] vetor, int numDivisao, int numThreads ) throws InterruptedException{
        
        int M = vetor.length;
        
        int [][] vetorDividido = new int[4][M];
        
        separarVetor(vetor, vetorDividido, M, numDivisao);
        

        Collection<paralelizacao> threads = new ArrayList<paralelizacao>();
        
        if (numThreads == 0 ){
            for (int i = 0; i < numDivisao; i++) {
                paralelizacao p = new paralelizacao(vetorDividido[i]); 
                threads.add(p); 
            }
        } else {
            for (int i = 0; i < numDivisao; i++) {
                paralelizacao p = new paralelizacao(vetorDividido[i], numThreads); 
                threads.add(p); 
            }
        }


        for (paralelizacao p : threads)
        {
            p.start();
        }
        for (paralelizacao p : threads) 
        {
            p.join();
        }
        
        /*for(int i=0; i<numDivisao; i++){
           System.out.println("Valores do " + (i+1) + "º vetor");
           imprimeVetor(vetorDividido[i]); 
        }*/
    }
    
    public static void separarVetor(int[] vetor,int[][] vetorDividido, int M, int numDivisao){
        int cont1 = 0,cont2 = 0, cont3 = 0, cont4 = 0;
        if (numDivisao == 4) {
           for (int i = 0; i < M;i++){
                if (vetor[i] < M/4){
                    vetorDividido[0][cont1] = vetor[i];
                    cont1++;
                }else if((vetor[i] >= M/4) && (vetor[i] < M/4*2)){
                   vetorDividido[1][cont2] = vetor[i];
                    cont2++; 
                }else if((vetor[i] >= M/4*2) && (vetor[i] < M/4*3)){
                   vetorDividido[2][cont3] = vetor[i];
                    cont3++; 
                }else{
                    vetorDividido[3][cont4] = vetor[i];
                    cont4++;  
                }
            } 
        }
        else if (numDivisao == 3) {
            for (int i = 0; i < M;i++){
                if (vetor[i] < M/3){
                    vetorDividido[0][cont1] = vetor[i];
                    cont1++;
                }else if((vetor[i] >= M/3) && (vetor[i] < M/3*2)){
                   vetorDividido[1][cont2] = vetor[i];
                    cont2++; 
                } else{
                    vetorDividido[2][cont3] = vetor[i];
                    cont3++;  
                }
            }
        } 
        else {
          for (int i = 0; i < M;i++)
            if (vetor[i] > M/2){
                vetorDividido[0][cont1] = vetor[i];
                cont1++;
            }
            else{
                vetorDividido[1][cont2] = vetor[i];
                cont2++;
            }  
        }            
    }
    
    public static void imprimeVetor(int[] vetor){
        for (int i = 0; i < vetor.length;i++)
            if (vetor[i] != 0)
                System.out.print(vetor[i] + " - ");
        System.out.println();
    }
    
}
