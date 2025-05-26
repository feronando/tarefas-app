# Gerenciador de Tarefas

Este é um projeto simples de gerenciamento de tarefas desenvolvido em Java Web, utilizando Jakarta EE (JSF, CDI, JPA/Hibernate) para a camada de backend e PrimeFaces para a interface de usuário. O objetivo é demonstrar um CRUD (Criar, Ler, Atualizar, Excluir) completo de tarefas.

---

## Itens Implementados e Diferenciais

Com base nos critérios fornecidos e nas funcionalidades desenvolvidas, os seguintes itens foram implementados:


### a) Criar uma aplicação Java Web utilizando JavaServer Faces (JSF)
* Interface de usuário desenvolvida integralmente com **JSF (Jakarta Faces)**.
* Utilização de **PrimeFaces** para componentes.
* Gerenciamento de beans e injeção de dependências através de **CDI (Contexts and Dependency Injection)**.

### b) Utilizar persistência em um banco de dados PostgreSQL.
* Configuração do projeto para utilizar **PostgreSQL** como sistema de gerenciamento de banco de dados.
* Acesso e manipulação dos dados da aplicação através do PostgreSQL.

### c) Utilizar JPA (Java Persistence API)
* A camada de persistência é implementada com **JPA**.

### f) Outros diferenciais que julgar conveniente.
* **Mensagens de Feedback:** Implementação de `p:growl` para exibir mensagens de sucesso e erro ao usuário.
* **Conclusão de Tarefas:** Funcionalidade para marcar uma tarefa como 'Concluída' diretamente da lista.
* **Validação de Formulários:** Campos obrigatórios são validados para garantir a integridade dos dados antes da persistência.

---

## Como Executar o Projeto Localmente

Para rodar este projeto em sua máquina local, siga os passos abaixo:

### Pré-requisitos

Certifique-se de ter o seguinte software instalado em sua máquina:

1.  **JDK (Java Development Kit) 11 ou superior:**
    * Verifique sua versão: `java -version`
    * Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://openjdk.java.net/install/index.html)
2.  **Apache Maven 3.6.3 ou superior:**
    * Verifique sua versão: `mvn -v`
    * Download: [Apache Maven](https://maven.apache.org/download.cgi)
3.  **Payara Server (Servidor de Aplicação Jakarta EE):**
    * Baixe a versão `Full` ou `Web` do Payara Server Community: [Payara Server Downloads](https://www.payara.fish/downloads/payara-platform-community-downloads/)
    * Descompacte o arquivo ZIP em um diretório de sua preferência.
4.  **Banco de Dados PostgreSQL:**
    * Instale o PostgreSQL em sua máquina.
    * Crie um banco de dados para a aplicação.
    * Crie um usuário e senha com permissões para acessar este banco de dados.

### Passos para Configuração do PostgreSQL com Payara

Para que sua aplicação possa se conectar ao PostgreSQL, você precisa configurar um `DataSource` no Payara Server.

1.  **Baixe o Driver JDBC do PostgreSQL:**
    * Acesse o site oficial do PostgreSQL e baixe o driver JDBC (Jar Driver). Procure por "PostgreSQL JDBC Driver" ou `postgresql-X.X.jar`.
    * Exemplo: [PostgreSQL JDBC Driver Download](https://jdbc.postgresql.org/download/)
    * Salve este arquivo `.jar` em um local acessível, como `C:\payara5\glassfish\domains\domain1\lib` ou `/opt/payara5/glassfish/domains/domain1/lib`. (A pasta `lib` dentro do seu domínio Payara é um bom lugar para drivers de banco de dados).

2.  **Inicie o Payara Server:**
    * Abra o terminal (linha de comando) ou o prompt de comando.
    * Navegue até o diretório `bin` da sua instalação Payara (ex: `C:\payara5\bin` ou `/opt/payara5/bin`).
    * Execute o comando para iniciar o servidor:
        ```bash
        asadmin start-domain domain1
        ```
    * Aguarde até que o servidor inicie completamente.

3.  **Acesse o Console Administrativo do Payara:**
    * Abra seu navegador e acesse a URL do console administrativo: `http://localhost:4848` (porta padrão).
    * Faça login se solicitado (usuário padrão `admin`, sem senha na primeira vez).

4.  **Crie um JDBC Connection Pool:**
    * No menu à esquerda, navegue até `JDBC` -> `JDBC Connection Pools`.
    * Clique no botão `New...`.
    * Preencha os campos conforme abaixo (ajustando para seus dados do PostgreSQL):
        * **Pool Name:** `tarefasPool` (Você pode usar qualquer nome, mas mantenha este para referência)
        * **Resource Type:** `javax.sql.DataSource`
        * **Database Driver Vendor:** `PostgreSQL`
        * Clique em `Next`.
        * Na próxima tela, preencha as **Additional Properties**:
            * `User`: `tarefas_user` (ou o usuário que você criou para o PostgreSQL)
            * `Password`: `tarefas_pass` (ou a senha que você criou)
            * `URL`: `jdbc:postgresql://localhost:5432/tarefas_db` (Ajuste `localhost`, `5432` e `tarefas_db` conforme sua instalação do PostgreSQL)
            * `ServerName`: `localhost`
            * `PortNumber`: `5432`
            * `DatabaseName`: `tarefas_db`
        * Clique em `Finish`.
    * **Teste o Connection Pool (Opcional, mas recomendado):** Após criar o pool, selecione-o na lista e clique no botão `Ping`. Se tudo estiver configurado corretamente, você deverá ver uma mensagem de sucesso.

5.  **Crie um JDBC Resource (DataSource):**
    * No menu à esquerda, navegue até `JDBC` -> `JDBC Resources`.
    * Clique no botão `New...`.
    * Preencha os campos:
        * **JNDI Name:** `jdbc/tarefasDS` (Este nome é CRUCIAL! Ele deve ser **exatamente** o mesmo que você usa no seu `persistence.xml` dentro da tag `<jta-data-source>`)
        * **Pool Name:** Selecione `tarefasPool` na lista suspensa (o pool que você acabou de criar).
        * Clique em `OK`.

6.  **Configure o `persistence.xml` do seu projeto:**
    * Abra o arquivo `src/main/resources/META-INF/persistence.xml` no seu projeto.
    * Certifique-se de que a unidade de persistência (provavelmente `tarefas-pu`) está configurada para usar o `DataSource` que você criou no Payara:
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <persistence version="2.2"
                     xmlns="[http://xmlns.jcp.org/xml/ns/persistence](http://xmlns.jcp.org/xml/ns/persistence)"
                     xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
                     xsi:schemaLocation="[http://xmlns.jcp.org/xml/ns/persistence](http://xmlns.jcp.org/xml/ns/persistence) [http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd](http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd)">
            <persistence-unit name="tarefas-pu" transaction-type="JTA">
                <jta-data-source>jdbc/tarefasDS</jta-data-source> <properties>
                    <property name="hibernate.hbm2ddl.auto" value="update" /> <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect" />
                    <property name="hibernate.show_sql" value="true" />
                    <property name="hibernate.format_sql" value="true" />
                </properties>
            </persistence-unit>
        </persistence>
        ```
    * Salve o arquivo.

### Passos Finais para Execução do Projeto

1.  **Clone o Repositório (ou baixe os arquivos):**
    * Se você estiver em um repositório Git:
        ```bash
        git clone <URL_DO_SEU_REPOSITORIO>
        cd <nome_do_diretorio_do_projeto>
        ```
    * Se você baixou os arquivos, navegue até a pasta raiz do projeto.

2.  **Compile e Empacote o Projeto com Maven:**
    * Abra o terminal (linha de comando) na pasta raiz do projeto (onde o `pom.xml` está localizado).
    * Execute o comando Maven para limpar, compilar e empacotar o projeto em um arquivo `.war`:
        ```bash
        mvn clean install
        ```
    * Este comando irá gerar um arquivo `.war` (`tarefas-app.war`) dentro da pasta `target/`.


3.  **Faça o Deploy da Aplicação no Payara:**
    * **Via Console Administrativo:**
        * Acesse o console administrativo do Payara no seu navegador (`http://localhost:4848`).
        * Vá para `Applications` (Aplicações) -> `Deploy` (Fazer Deploy).
        * Clique em `Choose File` (Escolher Arquivo) e selecione o arquivo `.war` gerado em `target/` do seu projeto.
        * Siga as instruções para fazer o deploy.
    * **Via Linha de Comando (asadmin):**
        * Na pasta `bin` da sua instalação Payara, execute:
            ```bash
            asadmin deploy <caminho_completo_para_seu_projeto>/target/<nome_do_arquivo>.war
            ```
            Ex: `asadmin deploy C:\Users\SeuUsuario\Projetos\tarefas-app\target\tarefas-app.war`

    * **Via Autodeploy (Copiando o .war para o Diretório de Deploy):**

        * Navegue até o diretório de autodeploy do seu domínio Payara. O caminho padrão é:
            $PAYARA_HOME\glassfish\domains\domain1\autodeploy
        Copie o arquivo .war (que está na pasta target/ do seu projeto) para este diretório.
        O Payara Server monitora essa pasta e fará o deploy da aplicação automaticamente em poucos segundos. Você pode verificar o console do servidor para ver a mensagem de sucesso do deploy.

4.  **Acesse a Aplicação:**
    * Após o deploy bem-sucedido, você pode acessar a aplicação no seu navegador.
    * A URL padrão será algo como: `http://localhost:8080/<nome_do_contexto_da_aplicacao>/`
    * O `<nome_do_contexto_da_aplicacao>` geralmente é o nome do seu arquivo `.war` sem a extensão `.war` (ex: `tarefas-app`).
    * Portanto, a URL inicial provavelmente será: `http://localhost:8080/tarefas-app/index.xhtml` ou `http://localhost:8080/tarefas-app/`.

---

## Contato

Para dúvidas ou sugestões, entre em contato.