# Implementação do algoritmo Bubble Sort para rodar em Paralelo

Usando uma lista com cem mil elementos, o tempo gasto pelo meu notebook para criar e organizar os valores de forma crescente foi de 42843 milisegundos ou 42,843 segundos.

Introdução:
	Nessa segunda fase, foi uma continuação do que já havia sendo feito no primeiro trabalho, tendo o acréscimo de uma divisão do vetor antes de enviá-lo para rodar em paralelo. Essa divisão foi feita da seguinte forma:


	[55,6,2,99,51,12,14,...,89,2] = [6,2,12,14,...2] [55,99,51,...89]


Como se dá para notar, foram divididos os menores valores de um lado e os maiores para outro lado. Seguindo a mesma lógica mostrada acima, também foi feita a divisão do vetor em três e quatro partes.
Os testes realizados, buscaram encontrar os melhores resultados, diversas vezes foi rodado o código, para uma melhor exatidão dos resultados. Nessa análise, temos a amostra dos resultados obtidos, para duas, três e quaro divisões, sendo que para cada tipo foi registrado, quatro resultados, dois mostrando o melhor resultado geral e outro de cada divisão. Mais à frente, ficará mais fácil de se entender, com as imagens.

Estrutura do código:
	O código utiliza se divide em três arquivos, bubbleSort.java, paralelizacao.java e ordenar.java, para ficar mais fácil de entender, vou explicá-lo do mais simples ao mais complexo:
* bubbleSort.java: Nesse arquivo, simplesmente foi implementado o código padrão do bubbleSort, sendo o mesmo adicionado dentro de uma classe que estende a classe Thread, para que o mesmo possa rodar em threads.

* paralelizacao.java: Nesse arquivo, praticamente está usando a lógica da class main da fase 01, mais algumas mudanças. A primeira mudança foi na transformação do código em uma classe que também estende threads. A ideia é ter um thread que se divide em mais threads e assim testar qual a performance alcançada, com tal meio. Um outro ponto adicionado, foi o polimorfismo de sobrecarga, para aceitar duas formas de chamar o construtor da classe, uma passando o vetor e o número de threads que será dividido a ordenação (na fase um, esse valor foi adicionado à variável nCpus) e um segundo caso, que você passa apenas o vetor. Nesse segundo caso, como se passa apenas o vetor, uma variável chamada bestResult recebe true, desta forma o algoritmo é desviado para uma rota que irá procurar o melhor tempo de execução encontrado (Como foi feito, será explicado mais a frente).


* ordenar.java: Nesse arquivo, ficou um pouco mais “cheio”, devido adição de funções para teste. Mas de forma simplificado, existe duas funções principais, sendo elas, a de rodarOrdenacao e buscaMelhorResult. As outras são mais simples, a separarVetor para dividir o vetor e a imprimeVetor, para imprimir o vetor.
Voltando as principais, a rodarOrdenacao simplesmente recebe o vetor, o número de threads que será dividido e o número de subvetores que serão criados usando a lógica já explicada acima, podendo ser 2, 3 ou 4 subvetores. Já na buscaMelhorResult, se passa o vetor e a mesma vai dividir o vetor nos três tipos de divisões disponíveis e buscará o melhor resultado de cada, usando a mesma lógica de busca do paralelizacao.java.

* Busca de melhor Resultado: A busca foi feita da seguinte forma, usando uma ideia de que o número de threads é igual a 0 + N, onde N começa valendo 1 e vai dobrando a cada rodada, comparando se ainda há aumento de desempenho, quando não houver, ele substituiu o 0 pelo o número de threads anterior (o que teve melhor resultado) e N volta para 1 e continua a somar. Desta forma, ele consegue encontrar o melhor resultado.

Resultados:
Usando uma lista com cem mil elementos, o tempo gasto pelo meu notebook para criar e organizar os valores, sendo que foram separados em dois vetores menores, foi de 32,78 segundos. Como mostra a imagem a seguir:

Os testes seguintes, foram feitos duas vezes em duas formas distintas, para cada tipo de divisão do vetor. Assim temos, 4 resultados encontrados para a divisão do vetor em duas partes e 4 resultados para a divisões em três e quatro partes. Para ficar mais visível, falarei apenas os resultados e as imagens serão colocados, ao fim do trabalho.


   * Primeira forma:
Na primeira forma, foi feito usando o arquivo ordenar.java, onde o mesmo usando a busca de melhor resultado, da forma já explicada, passando a cada momento um número de divisões do vetor (2, 3 ou 4) e para cada uma delas, ia aumentando o número de threads para cara parte, lembrando, que essas partes também estavam rodando em paralelo. Outro ponto importante, é que ele envia o mesmo número de threads para cada parte, assim, se envia 20 threads, cada parte do vetor, rodará com 20 threads.

        * Com 2 Partes: Com duas partes, os melhores resultados obtidos foram, no primeiro teste 11,35 segundos com 32 threads em cada parte e no segundo teste, foi 10,89 segundos com 256 threads em cada parte.

        * Com 3 Partes: Com três partes, os melhores resultados obtidos foram, no primeiro teste 13,22 segundos com 128 threads em cada parte e no segundo teste, foi 13,29 segundos com 256 threads em cada parte.

        * Com 4 Partes: Com quatro partes, os melhores resultados obtidos foram, no primeiro teste 16,75 segundos com 256 threads em cada parte e no segundo teste, foi 16,93 segundos com 256 threads em cada parte.

Obs: Foi apenas coincidência, sair todos resultados múltiplos de dois.

* Segunda forma:
Na segunda forma, foi feito usando o arquivo paralelizacao.java, onde o mesmo também usou a busca de melhor resultado, com a diferença, que ele volta o retorno do melhor resultado de cada divisão, assim sendo, se foi dividido em dois, foi dois resultados, se foram três, três resultados. Vale ressaltar, que o que será considerado, será o maior tempo retornado, pois os demais esperaram, antes de prosseguir.

	* Com 2 Partes: Com duas partes, os melhores resultados obtidos foram, no primeiro teste, uma parte com 12,41 segundos e 33 threads e outra parte com 10,04 com 36 threads. No segundo teste, uma parte com 11,80 segundos e 128 threads e outra parte com 10,31 com 1024 threads.

	* Com 3 Partes: Com três partes, os melhores resultados obtidos foram, no primeiro teste, uma parte com 14,74 segundos e 64 threads, outra com 12,53 com 36 threads e a última com 10,16 com 514 threads. No segundo teste, uma parte com 14,80 segundos e 32 threads, outra com 15,35 com 17 threads e a última com 10,01 com 33 threads.

	* Com 4 Partes: Com quatro partes, os melhores resultados obtidos foram, no primeiro teste, uma parte com 18,42 segundos e 64 threads, outra com 14,65 com 129 threads, outra com 12,42 com 72 threads e a última com 10,36 com 138 threads. No segundo teste, uma parte com 18,98 segundos e 32 threads, outra com 15,01 com 66 threads, outra com 15,47 com 512 threads e a última com 15,72 com 66 threads.


Conclusão:
	Saindo de um resultado de 32,78 segundos, com um thread e o vetor dividido em duas partes, rodando uma por vez, para um resultado com 10,89 segundos com 256 threads rodando em cada uma das duas partes, é possível se ver a diferença. Novamente, lembrando que na segunda forma, partes individuais tiveram desempenho melhores, mas o que conta é o maior tempo demorado entre as partes, devido ao fato que terá que sincronizar os dados antes de continuar.
	Depois dos testes realizados, um ponto interessante e bastante perceptível, é a variância dos resultados, que ocorreu não apenas nos testes registrados, como também nos demais. Desta forma, quanto maior o número de threads, maior a instabilidade, devido às questões de fila de processos a serem executados. No fim pude concluir, que mesmo com a instabilidade, os pontos com melhores resultados e instabilidade menor, foi entre 36 e 64 threads. Contudo, novamente, vale ressaltar que esse foi o resultado obtido apenas em meu notebook e ainda a questões, de se o computador final que rodaria tal programa, será um computador específico para aquilo ou um genérico, pois isso deve ser considerado em implementação de verdade.

  Obs: Apenas por curiosidade, o código no computador no laboratório, teve um tempo de 33,14 segundos executados da forma do Exemplo 6 e com a divisão em 2 partes, onde cada parte rodou em paralelo e criou mais 256 threads cada, como no meu melhor resultado, ele rodou em apenas 4,77 segundos.
