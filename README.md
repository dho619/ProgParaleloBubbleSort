# Implementação do algoritmo Bubble Sort para rodar em Paralelo

Usando uma lista com cem mil elementos, o tempo gasto pelo meu notebook para criar e organizar os valores de forma crescente foi de 42843 milisegundos ou 42,843 segundos. 

Para o uso de threads, foi feito da seguinte forma, o vetor foi dividido em N partes e cada uma dessas partes foi organizada separadamente, após isso, foi feita a organização geral de todas as partes. Segue abaixo o resultado obitido para diferentes números de threads.

Conclusão 
	Saindo de um resultado de 42843 milisegundos com uma thread, para 7478 milisegundos com 64 threads, que foi melhor desempenho dos testes, é notável que a velocidade de ordenação ficou cinco vezes mais rápida, demorando apenas 17,45% do tempo gasto pela ordenação por uma thread.
	Em contrapartida, o aumento de threads de forma inconsequente, não é a melhor forma de se usar a programação paralela, primeiramente que é nítido a queda de desempenho após certo tempo aumentando (cem threads nesse teste) e além disto tem a questão de um programas não deve usar todo o processador para ele, a não ser que o mesmo rodará em um computador próprio para ele.


