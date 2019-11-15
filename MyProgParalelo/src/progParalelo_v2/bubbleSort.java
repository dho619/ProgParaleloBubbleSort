package progParalelo_v2;

public class bubbleSort extends Thread{ //extender a clase thread, para poder usar ela como threads
   private int valorInicial, valorFinal;
   int[] vetor;
    //Construtor
    public bubbleSort(int[] vetor, int valorInicial, int valorFinal) {
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.vetor = vetor;
    }
    
   @Override//sobrescrever o metodo run da class thread
    public void run() {
        //ordenacao de vetor pelo algoritmo bubbleSort
        int aux = 0;
        for(int i = valorInicial; i < valorFinal; i++)
            for (int j = valorInicial; j < valorFinal-1; j++){
                if (vetor[j] > vetor[j + 1]){
                    aux = vetor[j];
                    vetor[j] = vetor[j+1];
                    vetor[j+1] = aux;
                }
            }                   
    }
    
}
