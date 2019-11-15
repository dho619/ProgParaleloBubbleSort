package progParalelo_v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * /**
 *
 * @author Dho
 */
public class paralelizacao extends Thread {

    int[] vetor;
    int M, nCpus;
    boolean bestResult;

    /*Se nao passa o numero de cpus, ele procura pelo melhor resultado, e isso ira demorar, muito*/
    public paralelizacao(int[] vetor) {
        this.vetor = vetor;
        this.M = vetor.length;
        this.nCpus = 0;
        this.bestResult = true;
    }

    /* Se passa o numero de nCpus, ele ira rodar com esse numero de threads */
    public paralelizacao(int[] vetor, int nCpus) {
        this.vetor = vetor;
        this.M = vetor.length;
        this.nCpus = nCpus;
        this.bestResult = false;
    }

    @Override
    public void run() {

        if (!bestResult) {
            rodarThreads(nCpus);
        } else {
            long result = 0, resultAnt = 0, resultAux = 0;
            int nCpusAnt = 1,nCpusAux = 0, limiteInf= 0, limiteSup = M, aumenta = 1;
            boolean continuar = true, novoLimiteDefinido = false;
            while (continuar) {
                resultAnt = result;
                nCpus = limiteInf + aumenta;

                long tempoInicial = System.currentTimeMillis();
                rodarThreads(nCpus);
                long tempoFinal = System.currentTimeMillis();
                result = tempoFinal - tempoInicial;

                System.out.println("Rodando com " + nCpus+ " threads, tendo como resultado: " + result);
                
                if (resultAux != 0) {
                    if (resultAux < result){
                        System.out.println("--------------------------------------------------------------------------------------------------------------");
                        System.out.println("Com " + nCpusAux + " threads foi o melhor resultado obitido! Result: " + resultAux);
                        System.out.println("--------------------------------------------------------------------------------------------------------------");
                        System.out.println("");
                        continuar = false;
                    } else{
                        resultAux = 0;
                        nCpusAux = 0; 
                    }
                }else
                    if ((nCpus > 1) && (result > resultAnt)) {
                        limiteInf = nCpusAnt;
                        limiteSup = nCpus;
                        aumenta = 1;                    
                        novoLimiteDefinido = true;
                        //System.out.println("O melhor resultado esta entre: " + nCpusAnt + " - " + nCpus);
                    }
                if((limiteSup-limiteInf) == 1){
                    System.out.println("--------------------------------------------------------------------------------------------------------------");
                    System.out.println("Com " + limiteInf + " thread foi o melhor resultado obitido! Result: " + resultAnt);
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
                
                
            } //while (continuar)
        } // if (!bestResult){...} else {
    } // run

    private void rodarThreads(int numThreads) {
        int valorInicial = 0;
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
        for (int i = 0; i < numThreads; i++) {
            valorInicial = valorFinal;
            valorFinal = M / numThreads * (i + 1);
            bubbleSort b = new bubbleSort(vetor, valorInicial, valorFinal); //chamando o construtor do bubbleSort com seus paramentros
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
        {
            b.start();
        }
        for (bubbleSort b : threads) //sincronizando as threads
        {
            try {
                b.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(paralelizacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
         Depois de terminar todas as threads anteriores, teremos n partes organizadas ente si, mas o
         vetor nao esta ainda organizado, desta forma, ira rodar todo o vetor novamente organizando,
         mas por estar parcialmente organizado, a velocidade e bem mais rapida.
         */
        bubbleSort organizar = new bubbleSort(vetor, 0, M);
        organizar.start();
        try {
            organizar.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(paralelizacao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
