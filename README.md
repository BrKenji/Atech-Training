# ATECH-School

# Roteiro de estudos das tecnologias utilizadas no site do pós venda

## Histórico

Este roteiro de estudos foi originalmente criado para o entendimento da stack de tecnologia utilizadas no [site do pós venda](https://view.atech.com.br). A ideia era facilitar o desenvolvimento de uma nova funcionalidade no site, ligada a alguns gráficos de desempenho.

## Modo de uso

O repositório tem um branch principal com essa descrição. A principio nenhum código ficará nesse branch. Cada desenvolvedor que for seguir esse roteiro de estudos deve baixar esse repositório do git, criar uma branch nomeando-a com seu usuário de rede (por exemplo _alopes_) e colocar todo o seu código nessa sua branch.

Para cada commit é necessário utilizar um ID do Jira. Existe uma task no Jira Bugtracker específica para isso e todos os commits devem utilizá-la, a saber é a [CELDEV-11](https://jira.atech.com.br/browse/CELDEV-11). Um exemplo de mensagem de commit é a seguinte: _"CELDEV-11 Finalização do marco 1"_

---

# Marco 1: Api do site (java)

Fazer um programa (jar) em java 11 com Spring boot e gradle, cujo nome seja *data\_performance\_analysis*  que tenha um endpoint que exibe uma string *"Hello World"*.

Deve ser possível executar no terminal o comando:

```
java -jar data_performance_analysis.jar
```

e então eu devo poder acessar através do meu browser a seguinte página:

```
http://localhost:8081/hello_world
```

e visualizar na tela a string *"Hello World"*
 
Além disso, devo poder acessar a URL:

```
http://localhost:8081/hello/{nome}
```

onde *{nome}* pode ser substituído por qualquer nome (como http://localhost/hello/adriano) e deve ser exibido um json da seguinte forma:

```
{"greeting": "Hello", "name": "adriano"}
```

---

# Marco 2: Site em containers (docker)

Uma vez o site simples pronto, é o momento dele passar a ser usado através do docker. Pra isso deveremos usar o docker-compose. Aqui, preciso conseguir entrar na pasta com o projeto etc. e dar o comando:

```
docker-compose up
```

E conseguir acessar pelo browser o site como no marco 1, mas como uma diferença: devo conseguir acessar o site através da porta 8083. Por exemplo:

```
http://localhost:8083/hello/{nome}
```

---

# Marco 3: Site com front-end (Angular)

Agora que já temos o back-end, é hora do front-end. Nesse marco a ideia é a seguinte:

Montar um site em angular que tenha um cabeçalho nomeado "Base do site de gráficos de estatística de desempenho".

Logo abaixo deve haver um input text em material com o placeholder "digite seu nome aqui" seguido de um botão com nome "iniciar".

Quando o usuário clicar em iniciar, caso não tenha digitado nenhum nome, deve aparecer um aviso de erro no próprio input-text informando que o campo é obrigatório (basta ele ficar vermelho que já é o suficiente).

Quando o usuário clicar em iniciar e tiver um nome no input-text, deverá ser requisitado ao back-end (já pronto) a mensagem (como definida logo abaixo deverá ser exibido o texto definido no "greeting" seguido do texto em "name" (o nome deverá ter sido enviado ao back-end como definido no marco 1). Caso o back-end esteja fora do ar, deverá ser exibida uma mensagem de erro.

Em um primeiro momento o site poderá ser executado na própria máquina (através do comando *ng serve*).
 
Quando estiver pronto, o front-end em angular deverá ser colocado também em container do docker (usando nginx) e deverá continuar funcionando como antes.

Observação: Como o front-end estará em uma porta diferente do back-end, as requisições darão erro de CORS. Para evitar esse problema é preciso usar a annotation *CrossOrigin* para liberar o acesso pelo endereço do front-end e para os métodos GET e POST (esse último será usada posteriormente)

---

# Marco 4: Site com banco de dados (Mongo DB)

Aqui será o último marco de aprendizado de nova tecnologia/área. Os próximos já passarão a ser ligados ao funcionamento que precisaremos efetivamente no novo site. Utilizaremos o Mongo DB como banco de dados.

O banco deverá ter uma coleção que guarde as informações de todas as boas vindas já dadas pelo site. Deverão ser armazenadas duas informações: o nome que recebeu as boas vindas; e o horário que essas boas vindas foram dada. Então, toda vez que uma boa vinda for dada, ela deverá ser persistida no banco de dados.

No back-end deverá haver um end-point */history* que quando acessado irá retornar uma lista com todo o histórico, onde cada item terá as seguintes informações: a saudação recebida, o nome da pessoa e a data/hora que isso ocorreu.

No front-end deverá haver uma nova página que exiba esse histórico. Na página principal deverá haver um botão/link/(ou algum outro meio) que redirecione a pessoa para essa página de histórico.

---

# Marco 5: Enviando informações com arquivos zipados

Hoje os programas que geram o site com os gráficos trabalhar a partir de arquivos compactados que são armazenados no svn. Não vamos mexer com o svn, mas vamos já lidar com os arquivos, então nesse marco vamos fazer:

1. Enviar um arquivo compactados através de uma interface no front-end para o back-end
1. Descompactar arquivos no back-end
1. Lidar com arquivos de texto e processá-los (parse)
1. Armazenar informações no banco de dados e verificar possíveis duplicações

Para facilitar o processo, não usaremos aqui ainda dados reais. A ideia é que tenhamos arquivos txt com várias linhas com os dados iguais o do histórico que temos no banco dados. Um exemplo de arquivo será o seguinte:

```
gretting;name;date
Hello;Adriano;2021-06-16 14:08:03
Olá;Lucas;2021-06-15 18:08:03
Hola;Vinicius;2021-06-15 14:08:03
Priviet;Matheus;2021-06-16 18:08:00
```

Aqui a ideia é que teremos vários arquivos dentro de um mesmo zip. Montaremos exemplos quando chegarmos nesse marco.

No front-end em angular deverá haver uma página onde possamos inserir um arquivo e enviar ao back-end.

No back-end deverá haver um endpoint para receber esse arquivo. Lá deve-se verificar se o arquivo está correto. Caso não seja um "zip", deverá ser retornado um "Bad Request". Também deverá ser retornado algum erro caso um dos arquivos esteja inconsistente (o que será decoberto no memento do processamento). Caso todo o processo esteja certo, os dados devem ser persistidos no banco de dados. Não devem ser inseridos dados repetidos. Quando finalizado, deverá ser retornado o número de dados (linhas) inseridas no banco de dados e o número de linhas repetidas.

As respostas do back-end deverão ser exibidas no front-end.

---

# Marco 6: Exibição de gráfico

Agora que já temos um site onde podemos salvar e recuperar informações, precisamos que os dados sejam exibidos da forma mais adequada ao ser humano. Em outras palavras, queremos que os dados sejam exibidos em forma de gráficos.

Faremos uma página separada para exibição dos gráficos. Nela o usuário deverá poder montar gráficos diferentes usando alguns filtros diferentes.

O usuário deverá poder montar um gráfico da quantidade de boas vindas que cada nome recebeu em granularidade de dia e hora, sendo possível escolher o intervalo que será exibido para o gráfico.

O usuário deverá poder ver a quantidade de boas vindas dadas em certo intervalo, com granularidade de dias, horas e minutos.

O usuário deverá poder ver a quantidade de nomes diferentes que receberam boas vindas em certo intervalo, com granularidade de dias, horas e minutos.

O usuário deverá poder ver a quantidade de cada diferente "boas vindas" ("hello","olá",etc.) dadas em certo intervalo, com granularidade de dias, horas e minutos.

O usuário deverá poder ver a quantidade dada em certo intervalo do par ("boas vindas","nome"), com granularidade de dias, horas e minutos.

---

# Marco 7: Boas práticas de programação (ESLint / SonarLint), refatoração e documentação

Hoje há muitas ferramentas de "linting" para nossos códigos.

A ideia aqui é aprender com essas ferramentas.

Além disso, vamos ver como refatorar os códigos já feitos e deixá-los mais fáceis de entender e de outras pessoas passarem a dar manutenção nele.

Por fim, vamos discutir o que é importante documentar e o que é desnecessário ou menos importante.

---

# Marco 8: Versionamento (gitlab), controle de tarefas (Jira) e automatização de processo (Jenkins)

Este marco será, brevemente, para a transição à "vida real". A ideia dele é ver quais e como usamos ferramentas auxiliares no desenvolvimento de nossos sistemas.

Primeiramente veremos o que é e como funciona o versionamento de código. No caso presente veremos sobre o git e gitlab. Além disso, veremos onde o site do pós venda está versionado e como poderemos fazer o versionamento desse novo módulo.

Veremos a parte de controle de tarefas. Na Atech usamos o Jira para este fim. Veremos o repositória do site do pós venda no Jira e como o utilizamos.

Por fim, mas não menos importante, veremos como tratamos a automatização de processo, a qual, em geral, colocamos no Jenkins.

Esse marco não está muito bem definido e a ideia é que não esteja mesmo. Os pontos aqui serão discutido conforme as necessidades para a transição para começar o trabalho junto ao site do pós venda propriamente dito.
