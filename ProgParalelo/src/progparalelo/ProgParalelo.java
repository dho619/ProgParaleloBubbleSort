/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progparalelo;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Dho
 */
public class ProgParalelo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
        int nCpus = Runtime.getRuntime().availableProcessors(); // Pega o numero de nucleos do computador
        System.out.println("Numero de Threads: " + nCpus);      // Esse numero sera o numero de theads criadas
                                                                // Pode colocar um valor fixo, se quiser
                                                                
        long tempoInicial = System.currentTimeMillis();//Pega os segundos atuais, para descobrir o tempo gasto
        int M = 100000; // o tamanho do vetor
        int[] vetor = new int[M]; //criando o vetor
        
        for (int i = 0; i < M; i++){              //Adicionando valores aleatorios para cada
            vetor[i] = (int) (Math.random() * M); // posicao do vetor
        }
        
        imprimeVetor(vetor); //Chama a funcao para imprimir o vetor;

        
        int valorIncial = 0;
        int valorFinal = 0;
        /*
        Collection serve para agrupar elementos, seja do tipo primitivo ou objetos, nesse caso sera
        os objetos da classe bubbleSort, ele passara para ele como ArrayList (pois o mesmo nao precisa 
        especificar a quantidade de objetos que ira ter na colecao)
        */
        Collection<bubbleSort> threads = new ArrayList<bubbleSort>();
                
         /*
        Aqui sera feito a separacao das threads de acordo com o numero de threads escolhido ou pego
        automaticamente pela funcao "Runtime.getRuntime().availableProcessors()" (nCpus). 
        O valor e dividido de acordo com o numero passsado, por exemplo se for 4 threads, criara 4 objetos
        bubbleSort no threads que é um Collection, sendo um do 0 ao 25000, outro do 25000 ao 50000 
        e assim por diante.
        */
        for (int i = 0; i < nCpus;i++){
            valorIncial = valorFinal;
            valorFinal = M/nCpus * (i + 1);
            bubbleSort b = new bubbleSort(vetor, valorIncial, valorFinal); //chamando o construtor do bubbleSort com seus paramentros
            threads.add(b); //Adicionando as threads na Collection
        }
        /*
           **** IMPORTANTE **** Vale lembrar que o vetor e um objeto e objetos sao sempre passados como
           referencia e nao por valor, desta forma, ao enviar o "vetor" de parametro, estou enviando na
           verdade as posicao de memoria dele, no que resulta que alteracoes feitas dentro da classe bubbleSort
           estao alterando ele. Ja o valorInicial e valorFinal sao tipo primitivos, assim sao passados como valor
           e caso fossem alterados dentro do metodo run da classe bubbleSort, nao seriam alterados aqui
        */
        
        
        for (bubbleSort b : threads) //iniciando todas as threads
            b.start();
        for (bubbleSort b : threads) //sincronizando as threads
            b.join();
        
        /*
          Depois de terminar todas as threads anteriores, teremos n partes organizadas ente si, mas o
          vetor nao esta ainda organizado, desta forma, ira rodar todo o vetor novamente organizando,
          mas por estar parcialmente organizado, a velocidade e bem mais rapida.
        */
        bubbleSort organizar = new bubbleSort(vetor, 0, M);
        organizar.start();
        organizar.join();
        
        
        imprimeVetor(vetor);//chamando a funcao para imprimir o vetor ja organizado
        long tempoFinal = System.currentTimeMillis(); //pegando novamente os segundos
        System.out.println("Tempo de execução: " + (tempoFinal - tempoInicial)); //imprime em milisegundos o tempo demorado
    }
    
    public static void imprimeVetor(int[] vetor){ // funcoa para imprimir o vetor
        for (int i = 0; i < vetor.length;i++)
            System.out.print(vetor[i] + " - ");
        System.out.println();
    }
    
}
