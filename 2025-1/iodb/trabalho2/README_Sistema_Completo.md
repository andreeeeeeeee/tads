# Aplicativo Interativo EduLivre - Sistema Completo de Gestão de Conteúdos

## Descrição

Este aplicativo implementa um sistema interativo completo para a plataforma EduLivre com funcionalidades diferenciadas baseadas no perfil do usuário (Admin, Professor, Aluno). O sistema agora suporta **upload real de arquivos** e **visualização/download de conteúdos**.

## Funcionalidades por Perfil

### 👤 ADMIN

O usuário com perfil **admin** tem acesso completo a todas as funcionalidades:

1. **Cadastrar usuário** - Criar novos usuários (alunos, professores ou admins)
2. **Cadastrar curso** - Criar novos cursos na plataforma
3. **Matricular usuário em curso** - Matricular qualquer usuário em qualquer curso
4. **Buscar conteúdos de um curso** - Visualizar conteúdos com tipo e tamanho do arquivo
5. **Listar cursos com avaliações** - Ver todos os cursos com média de avaliação e número de matriculados
6. **Visualizar conteúdo** - Acessar, visualizar e baixar arquivos dos cursos

### 👨‍🏫 PROFESSOR

O usuário com perfil **professor** pode:

1. **Cadastrar aluno** - Criar novos usuários com perfil de aluno apenas
2. **Cadastrar conteúdo em curso** - Fazer upload real de arquivos (vídeos, PDFs, slides, etc.) nos cursos
3. **Matricular usuário em curso** - Matricular alunos em cursos
4. **Buscar conteúdos de um curso** - Visualizar conteúdos com tipo e tamanho do arquivo
5. **Listar cursos com avaliações** - Ver todos os cursos com média de avaliação e número de matriculados
6. **Visualizar conteúdo** - Acessar, visualizar e baixar arquivos dos cursos

### 👨‍🎓 ALUNO

O usuário com perfil **aluno** pode:

1. **Matricular-se em curso** - Realizar sua própria matrícula em cursos disponíveis
2. **Buscar conteúdos de um curso** - Visualizar conteúdos com tipo e tamanho do arquivo
3. **Listar cursos com avaliações** - Ver todos os cursos com média de avaliação e número de matriculados
4. **Avaliar curso** - Adicionar comentários e notas aos cursos (implementação da funcionalidade JSONB)
5. **Visualizar conteúdo** - Acessar, visualizar e baixar arquivos dos cursos

## 🆕 Novas Funcionalidades - Upload e Visualização de Arquivos

### Upload Real de Arquivos (Professores)

O sistema agora suporta upload real de arquivos com validação de tipo:

**Tipos de arquivo suportados:**

- **Vídeo**: mp4, avi, mkv, mov, wmv, flv, webm
- **PDF**: pdf
- **Imagem**: jpg, jpeg, png, gif, bmp, svg, webp
- **Áudio**: mp3, wav, ogg, flac, aac, m4a
- **Quiz**: txt, json, xml
- **Slide**: ppt, pptx, odp

### Visualização e Download de Conteúdos (Todos os usuários)

- **Listar conteúdos** de um curso com detalhes (tipo, tamanho)
- **Visualizar detalhes** completos do arquivo
- **Baixar arquivos** para o sistema local
- **Visualizar conteúdo** de arquivos pequenos ou texto diretamente no terminal

## Como Usar o Aplicativo

### 1. Login

Ao iniciar o aplicativo, você será solicitado a fornecer:

- **Email**: O email do usuário cadastrado no sistema
- **Senha**: A senha correspondente

### 2. Menu Principal

Após o login bem-sucedido, o menu será exibido com opções específicas do seu perfil.

### 3. Navegação

- Digite o número da opção desejada
- Siga as instruções na tela
- Para sair, digite `0` no menu principal

## Exemplos de Uso

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
=== VISUALIZAR CONTEÚDO ===
Digite o título do curso: Curso de Python

=== CONTEÚDOS DISPONÍVEIS ===
1. Aula 1 - Introdução ao Python    video      25.67 MB
2. Exercícios Python               pdf        2.34 MB
3. Slides - Sintaxe Básica         slide      5.89 MB

Escolha o conteúdo para visualizar (0 para voltar): 1

============================================================
DETALHES DO CONTEÚDO
============================================================
Título: Aula 1 - Introdução ao Python
Descrição: Primeira aula do curso de Python
Tipo: video
Tamanho: 25.67 MB

Opções:
1. Baixar arquivo
2. Visualizar conteúdo (texto/dados)
0. Voltar
Escolha uma opção: 1
Digite o caminho onde salvar o arquivo: C:\Downloads
Arquivo salvo com sucesso em: C:\Downloads\Aula_1___Introducao_ao_Python.mp4
```

### Validação de Tipos de Arquivo

O sistema automaticamente valida se o arquivo enviado corresponde ao tipo selecionado:

```text
Escolha o tipo: 1 (Vídeo)
Caminho do arquivo: C:\Documentos\manual.pdf
O tipo do arquivo não corresponde ao tipo selecionado!
Tipos aceitos para video: mp4, avi, mkv, mov, wmv, flv, webm
```

## Estrutura do Banco de Dados

O aplicativo utiliza as seguintes tabelas:

- **usuario** - Dados dos usuários (id, nome, email, senha, perfil)
- **curso** - Informações dos cursos (id, titulo, descricao, data_criacao, avaliacao)
- **matricula** - Relacionamento usuário-curso (id, usuario_id, curso_id, data_matricula)
- **conteudo** - Materiais dos cursos (id, curso_id, titulo, descricao, tipo, arquivo BYTEA)

## Funcionalidades Técnicas Implementadas

### 1. Sistema de Login

- Autenticação por email e senha
- Controle de acesso baseado em perfis

### 2. Upload Real de Arquivos

- Validação de tipos de arquivo baseada em extensão
- Armazenamento em campo BYTEA do PostgreSQL
- Verificação de existência e validade do arquivo

### 3. Visualização e Download

- Listagem de conteúdos com informações detalhadas
- Download de arquivos com nomes sanitizados
- Visualização de arquivos pequenos/texto
- Exibição de informações hexadecimais para arquivos binários

### 4. Validação de Dados

- Verificação rigorosa de tipos de arquivo
- Formatação automática de tamanhos (B, KB, MB, GB)
- Sanitização de nomes de arquivo para download

### 5. Manipulação JSONB

- Adição de comentários no campo `avaliacao` dos cursos
- Cálculo automático da média de avaliações
- Estrutura JSON para armazenar múltiplas avaliações

## Requisitos

- Java 8+
- PostgreSQL
- Bibliotecas:
  - JDBC PostgreSQL Driver
  - org.json (para manipulação JSON)

## Limitações e Considerações

### Segurança

- Senhas armazenadas em texto plano (em produção, usar hash)
- Validação de tipos baseada apenas em extensão
- Não há limitação de tamanho de arquivo

### Performance

- Arquivos são carregados completamente na memória
- Para arquivos muito grandes, considerar streaming
- Visualização limitada a arquivos pequenos (<1KB) para texto

### Funcionalidades Futuras

- Autenticação mais robusta
- Controle de permissões mais granular
- Preview de imagens e vídeos
- Sistema de pastas/categorização
- Histórico de downloads

## Execução

1. Certifique-se de que o banco PostgreSQL está rodando
2. Execute a classe `Main.java`
3. Faça login com um usuário válido
4. Navegue pelas opções do menu
5. Para testar upload, tenha arquivos disponíveis no sistema

## Observações Importantes

- **Caminhos de arquivo**: Use caminhos absolutos para upload
- **Tipos de arquivo**: O sistema valida a extensão antes do upload
- **Downloads**: Arquivos são salvos com nomes sanitizados
- **Visualização**: Arquivos pequenos podem ser visualizados diretamente
- **Permissões**: Professores só podem cadastrar alunos, não outros perfis
