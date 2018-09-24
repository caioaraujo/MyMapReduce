# MyMapReduce para inserção de compromissos

## Requisitos

- Instalar o mongodb seguindo o link:
https://docs.mongodb.com/manual/installation/

- Instalar o hadoop seguindo o link:
https://hadoop.apache.org/docs/r3.1.1/hadoop-project-dist/hadoop-common/ClusterSetup.html#Installation

## Execução

- Gerar o jar com a task install do maven
- Criar diretório: ```mkdir -p ~/compromises/input```
- Criar arquivo no formato csv delimitado por ";" no seguinte padrão:
```<codigo_banco>;<nome_favorecido>;<data_vencimento (dd/mm/aaaa)>;<valor_compromisso>;<nome_pagador>;<descrição_pagamento>;```
Exemplo:
```001;Clayton;01/01/2019;10.03;Caio;Cerveja churrasco;```
- Executar o comandos:
  ```cd $HADOOP_HOME```
  ```bin/hadoop jar .../hdfs-0.0.1-SNAPSHOT.jar mapreduce.Main ~/compromises/input/ ~/compromises/output```
