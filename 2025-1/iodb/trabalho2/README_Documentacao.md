# Sistema EduLivre - Plataforma de Gestão de Conteúd### Visualização e Download de Conteúdos

O sistema oferece opções completas de interação com os arquivos:

**Visualização Automática:**

- **Imagens** - Exibidas em janela Swing com zoom e scroll
- **PDFs** - Abertos automaticamente no visualizador padrão do sistema
- **Vídeos** - Reproduzidos no player padrão do sistema
- **Áudios** - Executados no player padrão do sistema
- **Quiz (texto)** - Exibidos em janela de texto formatada
- **Slides** - Abertos no aplicativo padrão (PowerPoint, LibreOffice, etc.)

**Download de Arquivos:**

- **Escolha do local** de salvamento pelo usuário
- **Criação automática** de diretórios se não existirem
- **Sanitização de nomes** de arquivo para compatibilidade
- **Verificação de arquivos** existentes com opção de sobrescrever
- **Feedback completo** do processo de downloadnal

## Descrição

O EduLivre é um sistema interativo completo para gestão de conteúdo educacional desenvolvido em Java com banco de dados PostgreSQL. O sistema oferece funcionalidades diferenciadas baseadas no perfil do usuário (Admin, Professor, Aluno) e implementa upload real de arquivos com visualização automática baseada no tipo.

## Funcionalidades por Perfil

### 👤 ADMIN

O usuário com perfil **admin** tem acesso completo ao sistema:

1. **Cadastrar usuário** - Criar novos usuários (alunos, professores ou outros admins)
2. **Cadastrar curso** - Criar novos cursos na plataforma
3. **Matricular aluno em um curso** - Matricular qualquer aluno em qualquer curso
4. **Buscar conteúdos de um curso** - Visualizar conteúdos com tipo e tamanho
5. **Listar cursos com avaliações** - Ver cursos com média de avaliação e número de matriculados

### 👨‍🏫 PROFESSOR

O usuário com perfil **professor** pode:

1. **Cadastrar aluno** - Cadastrar novos alunos
2. **Cadastrar conteúdo em um curso** - Upload real de arquivos com validação de tipo
3. **Matricular aluno em um curso** - Matricular alunos em cursos
4. **Buscar conteúdos de um curso** - Visualizar conteúdos com tipo e tamanho
5. **Listar cursos com avaliações** - Ver cursos com média de avaliação e número de matriculados

### 👨‍🎓 ALUNO

O usuário com perfil **aluno** pode:

1. **Matricular-se em um curso** - Realizar matrícula em cursos disponíveis
2. **Buscar conteúdos de um curso** - Visualizar conteúdos dos cursos matriculados
3. **Listar cursos com avaliações** - Ver cursos com média de avaliação e número de matriculados
4. **Avaliar curso** - Adicionar comentários e notas aos cursos (funcionalidade JSONB)

## 🆕 Sistema de Upload e Visualização de Arquivos

### Upload Real de Arquivos (Professores)

O sistema implementa upload completo de arquivos com validação rigorosa de tipo:

**Tipos de arquivo suportados:**

- **📹 Vídeo**: mp4, avi, mkv, mov, wmv, flv, webm
- **📄 PDF**: pdf
- **🖼️ Imagem**: jpg, jpeg, png, gif, bmp, svg, webp
- **🔊 Áudio**: mp3, wav, ogg, flac, aac, m4a
- **❓ Quiz**: txt, json, xml
- **📊 Slide**: ppt, pptx, odp

### Visualização Automática de Conteúdos

O sistema oferece visualização automática baseada no tipo de arquivo:

- **Imagens** - Exibidas em janela Swing com zoom e scroll
- **PDFs** - Abertos automaticamente no visualizador padrão do sistema
- **Vídeos** - Reproduzidos no player padrão do sistema
- **Áudios** - Executados no player padrão do sistema
- **Quiz (texto)** - Exibidos em janela de texto formatada
- **Slides** - Abertos no aplicativo padrão do sistema

### Armazenamento e Gerenciamento

- **Validação rigorosa** de tipos baseada em extensão de arquivo
- **Armazenamento seguro** em campo BYTEA do PostgreSQL
- **Formatação automática** de tamanhos (B, KB, MB, GB, TB)
- **Verificação de integridade** antes do upload

## Como Usar o Sistema

### 1. Inicialização e Login

Ao executar o sistema:

1. **Compilar o projeto** (Maven ou IDE)
2. **Executar a classe Main**
3. **Informar credenciais** de login:
   - **Email**: Email do usuário cadastrado
   - **Senha**: Senha correspondente

**Usuário padrão disponível:**

- Email: `admin@email.com`
- Senha: `admin123`
- Perfil: Administrador

### 2. Navegação no Sistema

- O **menu principal** é exibido conforme o perfil do usuário
- Digite o **número da opção** desejada
- Siga as **instruções na tela**
- Digite **0** para sair do sistema

### 3. Interface por Perfil

Cada perfil tem acesso a funcionalidades específicas:

- **Admin**: Acesso completo ao sistema
- **Professor**: Foco em criação de conteúdo e gestão de alunos  
- **Aluno**: Foco em consumo de conteúdo e avaliações

## Exemplos de Uso Prático

### Upload de Arquivo (Professor)

```text
=== CADASTRAR CONTEÚDO ===
Cursos disponíveis:
1. Curso de Python
2. Curso de Java
Escolha o curso: 1

Título do conteúdo: Aula 1 - Introdução ao Python
Descrição: Primeira aula do curso de Python

Tipo de conteúdo:
1. Vídeo
2. PDF
3. Imagem
4. Áudio
5. Quiz
6. Slide
Escolha o tipo: 1

Caminho do arquivo: C:\Videos\python_aula1.mp4
Arquivo carregado com sucesso! Tamanho: 25.67 MB
Conteúdo cadastrado com sucesso!
```

### Visualização de Conteúdo (Todos os usuários)

```text
=== BUSCAR CONTEÚDOS DE CURSO ===
Cursos disponíveis:
1. Curso de Python
Escolha o curso: 1

=== CONTEÚDOS DISPONÍVEIS ===
1. Aula 1 - Introdução        video      25.67 MB
2. Exercícios Python          pdf        2.34 MB
3. Slides - Sintaxe           slide      5.89 MB

Escolha o conteúdo para visualizar (0 para voltar): 1

============================================================
DETALHES DO CONTEÚDO
============================================================
Título: Aula 1 - Introdução
Descrição: Primeira aula do curso de Python
Tipo: video
Tamanho: 25.67 MB

Opções:
1. Visualizar conteúdo
2. Baixar arquivo
0. Voltar
Escolha uma opção: 2

=== BAIXAR ARQUIVO ===
Digite o caminho onde deseja salvar o arquivo: C:\Downloads
Arquivo baixado com sucesso!
Local: C:\Downloads\Aula_1___Introducao.mp4
Tamanho: 25.67 MB
```

### Download de Arquivo

```text
============================================================
DETALHES DO CONTEÚDO
============================================================
Título: Manual do Curso
Descrição: Guia completo do curso em PDF
Tipo: pdf
Tamanho: 2.34 MB

Opções:
1. Visualizar conteúdo
2. Baixar arquivo
0. Voltar
Escolha uma opção: 2

=== BAIXAR ARQUIVO ===
Digite o caminho onde deseja salvar o arquivo: C:\Downloads\Cursos
Diretório não existe! Deseja criá-lo? (s/n): s

Arquivo baixado com sucesso!
Local: C:\Downloads\Cursos\Manual_do_Curso.pdf
Tamanho: 2.34 MB
```

### Validação de Tipos de Arquivo

```text
Escolha o tipo: 1 (Vídeo)
Caminho do arquivo: C:\Documentos\manual.pdf

O tipo do arquivo não corresponde ao tipo selecionado!
Tipos aceitos para video: mp4, avi, mkv, mov, wmv, flv, webm
```

### Avaliação de Curso (Aluno)

```text
=== AVALIAR CURSO ===
Cursos disponíveis:
1. Curso de Python

Escolha o curso: 1
Nota (0-5): 4.5
Comentário: Excelente curso, muito didático!

Avaliação adicionada com sucesso!
Média atual do curso: 4.5
```

## Arquitetura do Sistema

### Estrutura do Banco de Dados

O sistema utiliza PostgreSQL com as seguintes tabelas:

```sql
-- Tabela de usuários
CREATE TABLE usuario (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('aluno', 'professor', 'admin'))
);

-- Tabela de cursos
CREATE TABLE curso (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    titulo VARCHAR(200) UNIQUE NOT NULL,
    descricao TEXT NOT NULL,
    data_criacao DATE DEFAULT CURRENT_DATE,
    avaliacao JSONB  -- Funcionalidade JSONB para avaliações
);

-- Tabela de matrículas
CREATE TABLE matricula (
    id SERIAL PRIMARY KEY,
    usuario_id UUID NOT NULL,
    curso_id UUID NOT NULL,
    data_matricula DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuario (id) ON DELETE CASCADE,
    FOREIGN KEY (curso_id) REFERENCES curso (id) ON DELETE CASCADE
);

-- Tabela de conteúdos
CREATE TABLE conteudo (
    id SERIAL PRIMARY KEY,
    curso_id UUID NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('video', 'pdf', 'imagem', 'audio', 'quiz', 'slide')),
    arquivo BYTEA,  -- Armazenamento de arquivos em binário
    FOREIGN KEY (curso_id) REFERENCES curso (id) ON DELETE CASCADE
);
```

### Arquitetura Java

**Padrão MVC implementado:**

- **📁 models/**: Classes de domínio (Usuario, Curso, Conteudo, etc.)
- **📁 DAOs/**: Camada de acesso a dados (UsuarioDAO, CursoDAO, etc.)
- **📁 views/**: Interface de usuário (classe Main)

**Classes principais:**

- `Usuario`: Gerencia dados e permissões de usuários
- `Curso`: Gerencia informações e avaliações de cursos
- `Conteudo`: Gerencia upload, validação e visualização de arquivos
- `ConexaoPostgreSQL`: Gerencia conexões com banco de dados

## Funcionalidades Técnicas Implementadas

### 🔐 Sistema de Autenticação

- **Login seguro** por email e senha
- **Controle de acesso** baseado em perfis (ADMIN, PROFESSOR, ALUNO)
- **Validação de credenciais** com feedback ao usuário

### 📤 Upload Real de Arquivos

- **Validação rigorosa** de tipos baseada em extensão
- **Armazenamento seguro** em campo BYTEA do PostgreSQL  
- **Verificação de integridade** antes do upload
- **Formatação automática** de tamanhos de arquivo

### 👀 Visualização e Download de Conteúdo

- **Imagens**: Janelas Swing com zoom e scroll automático
- **PDFs**: Abertura no visualizador padrão do sistema
- **Vídeos/Áudios**: Reprodução no player padrão
- **Arquivos de texto**: Exibição em janelas formatadas
- **Slides**: Abertura em aplicativos compatíveis
- **Download personalizado**: Salvamento em local escolhido pelo usuário
- **Sanitização automática**: Nomes de arquivo compatíveis com sistema operacional

### 📊 Funcionalidade JSONB (PostgreSQL)

- **Avaliações de cursos** armazenadas em formato JSON
- **Cálculo automático** de médias de avaliações
- **Estrutura flexível** para comentários e notas
- **Agregação de dados** para relatórios

### ✅ Validações e Controles

- **Verificação de tipos** de arquivo antes do upload
- **Controle de permissões** por perfil de usuário
- **Validação de dados** de entrada
- **Tratamento de erros** com mensagens claras

### 🏗️ Padrões de Design

- **MVC (Model-View-Controller)** para organização do código
- **DAO (Data Access Object)** para acesso a dados
- **Factory Pattern** para criação de conexões
- **Separation of Concerns** entre camadas

## Requisitos do Sistema

### 💻 Tecnologias Utilizadas

- **Java 8+** - Linguagem de programação principal
- **PostgreSQL** - Sistema de gerenciamento de banco de dados
- **Maven** - Gerenciamento de dependências e build
- **JDBC** - Conectividade com banco de dados
- **Swing** - Interface gráfica para visualização de conteúdo

### 📚 Bibliotecas e Dependências

```xml
<dependencies>
    <!-- Driver PostgreSQL -->
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <version>42.6.0</version>
    </dependency>
    
    <!-- Manipulação JSON -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20230227</version>
    </dependency>
</dependencies>
```

### ⚙️ Configuração do Ambiente

1. **PostgreSQL instalado e configurado**
2. **Java 8+ instalado**
3. **Maven configurado** (opcional, pode usar IDE)
4. **Banco de dados EduLivre criado** com o script SQL fornecido

### 🚀 Como Executar

```bash
# 1. Clonar/baixar o projeto
# 2. Configurar banco de dados PostgreSQL
psql -U postgres -f eduLivre.sql

# 3. Compilar o projeto (Maven)
mvn clean compile

# 4. Executar o sistema
mvn exec:java -Dexec.mainClass="com.edulivre.views.Main"

# OU através de IDE (IntelliJ, Eclipse, VSCode)
# Executar a classe Main.java
```

## Limitações e Considerações

### ⚠️ Segurança

- **Senhas em texto plano**: Em produção, implementar hash (BCrypt, SHA-256)
- **Validação por extensão**: Validação de tipos baseada apenas em extensão
- **Autenticação simples**: Não há controle de sessão ou tokens

### 🎯 Performance

- **Carregamento em memória**: Arquivos são carregados completamente na RAM
- **Limite de tamanho**: Sem controle de tamanho máximo de upload
- **Conexões de banco**: Pool de conexões não implementado

### 🔧 Funcionalidades Limitadas

- **Sem sistema de pastas**: Organização plana de conteúdos
- **Visualização básica**: Preview limitado para alguns tipos de arquivo
- **Sem histórico**: Não há log de ações ou auditoria
- **Relatórios limitados**: Apenas listagens básicas

### 🎨 Interface

- **Interface console**: Sistema baseado em linha de comando
- **Navegação linear**: Sem interface gráfica principal
- **Feedback limitado**: Mensagens de status básicas

## 🚀 Melhorias Futuras

### 🔒 Segurança Avançada

- Implementar hash de senhas com salt
- Sistema de tokens JWT para sessões
- Controle de permissões mais granular
- Auditoria e logs de segurança

### 📱 Interface

- Interface web responsiva (Spring Boot + Thymeleaf)
- API REST para integração com frontend moderno
- Interface mobile-friendly
- Dashboard com estatísticas

### 🎯 Funcionalidades

- Sistema de pastas/categorias para organização
- Preview avançado para imagens e vídeos
- Sistema de comentários em conteúdos
- Notificações por email
- Relatórios gerenciais avançados
- Sistema de backup automático

### 📊 Performance

- Implementar cache de consultas
- Pool de conexões com HikariCP
- Streaming de arquivos grandes
- Compressão de conteúdo
- CDN para arquivos estáticos

## 🎯 Execução e Testes

### Pré-requisitos

1. **PostgreSQL rodando** na porta padrão (5432)
2. **Banco edulivre criado** com o script SQL
3. **Java configurado** no PATH do sistema

### Passos para Execução

```bash
# 1. Verificar PostgreSQL
pg_isready -h localhost -p 5432

# 2. Criar banco de dados
psql -U postgres -c "CREATE DATABASE edulivre;"
psql -U postgres -d edulivre -f eduLivre.sql

# 3. Compilar projeto
cd edulivre
mvn clean compile

# 4. Executar sistema
mvn exec:java -Dexec.mainClass="com.edulivre.views.Main"
```

### Usuários de Teste

**Administrador padrão:**

- Email: `admin@email.com`
- Senha: `admin123`
- Funcionalidades: Acesso completo ao sistema

### Fluxo de Teste Sugerido

1. **Login como admin** com credenciais padrão
2. **Criar curso** de teste
3. **Cadastrar usuário professor** e aluno
4. **Login como professor** e fazer upload de conteúdo
5. **Login como aluno** e avaliar curso
6. **Testar visualização** de diferentes tipos de arquivo

### Estrutura de Arquivos

```text
edulivre/
├── src/main/java/com/edulivre/
│   ├── models/          # Classes de domínio
│   ├── DAOs/           # Acesso a dados
│   └── views/          # Interface de usuário
├── target/             # Arquivos compilados
├── pom.xml            # Configuração Maven
└── README.md          # Documentação

eduLivre.sql           # Script de criação do banco
README_Documentacao.md # Esta documentação
trabalho2.md          # Especificação do trabalho
```

## 📝 Observações Importantes

### ⚠️ Cuidados na Execução

- **Caminhos de arquivo**: Use caminhos absolutos para upload e download
- **Tipos de arquivo**: Sistema valida extensão antes do upload  
- **Tamanho de arquivo**: Sem limite definido, cuidado com arquivos muito grandes
- **Permissões**: Professores só podem cadastrar alunos, não outros perfis
- **Espaço em disco**: Verificar espaço disponível antes de downloads grandes

### 💡 Dicas de Uso

- **Teste com arquivos pequenos** primeiro (< 10MB)
- **Mantenha arquivos organizados** em pastas específicas para download
- **Use nomes descritivos** para conteúdos
- **Teste diferentes tipos** de arquivo para validar visualização
- **Organize downloads** em pastas separadas por curso ou tipo de conteúdo

### 🐛 Resolução de Problemas

**Erro de conexão com banco:**

- Verificar se PostgreSQL está rodando
- Confirmar credenciais de conexão
- Verificar se banco edulivre existe

**Erro no upload de arquivo:**

- Verificar se arquivo existe no caminho especificado
- Confirmar se extensão corresponde ao tipo selecionado
- Verificar permissões de leitura do arquivo

**Erro no download de arquivo:**

- Verificar se o diretório de destino é válido
- Confirmar permissões de escrita no local de destino
- Verificar espaço disponível em disco
- Validar se o caminho não contém caracteres inválidos

**Erro na visualização:**

- Confirmar se aplicativo padrão está instalado
- Verificar se Desktop.isDesktopSupported() retorna true
- Testar com diferentes tipos de arquivo

---

## 📋 Resumo do Projeto

O **Sistema EduLivre** é uma aplicação completa de gestão educacional desenvolvida como projeto acadêmico, demonstrando:

### 🎯 Objetivos Alcançados

- ✅ **Sistema de autenticação** com controle de perfis
- ✅ **Upload real de arquivos** com validação de tipos
- ✅ **Visualização automática** baseada no tipo de conteúdo
- ✅ **Download personalizado** de arquivos com controle total
- ✅ **Funcionalidade JSONB** para avaliações de cursos
- ✅ **Padrão MVC** bem estruturado
- ✅ **Integração PostgreSQL** com relacionamentos complexos

### 🏆 Diferenciais Técnicos

- **Armazenamento seguro** de arquivos em BYTEA
- **Visualização inteligente** com abertura automática de aplicativos
- **Sistema de download robusto** com criação automática de diretórios
- **Sanitização inteligente** de nomes de arquivo
- **Validação rigorosa** de tipos de arquivo
- **Interface adaptativa** por perfil de usuário
- **Estrutura JSON flexível** para avaliações

### 💡 Aprendizados Aplicados

- **Banco de Dados**: PostgreSQL, JSONB, relacionamentos, integridade referencial
- **Java**: POO, padrões de design, manipulação de arquivos, Swing
- **Arquitetura**: MVC, DAO, separação de responsabilidades
- **Validação**: Tipos de arquivo, entrada de dados, controle de acesso

---

**🎓 Projeto Acadêmico - Tecnologia em Análise e Desenvolvimento de Sistemas**  
**📚 Disciplina: Implementação e Operações de Banco de Dados**  
**📅 2025/1**
