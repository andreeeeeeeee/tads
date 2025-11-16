<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inserir Filme</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        form { max-width: 500px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input, textarea, select { 
            width: 100%; 
            padding: 8px; 
            margin-top: 5px; 
            border: 1px solid #ccc; 
            border-radius: 4px; 
            box-sizing: border-box;
        }
        button { 
            margin-top: 15px; 
            padding: 10px 20px; 
            background-color: #4CAF50; 
            color: white; 
            border: none; 
            border-radius: 4px; 
            cursor: pointer; 
        }
        button:hover { background-color: #45a049; }
        .mensagem { 
            padding: 10px; 
            margin: 15px 0; 
            background-color: #d4edda; 
            border: 1px solid #c3e6cb; 
            color: #155724; 
            border-radius: 4px; 
        }
        a { color: #008CBA; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Inserir Novo Filme</h1>

<?php

if ($_SERVER["REQUEST_METHOD"] == "POST"){

    $nome = mysqli_real_escape_string($conexao = mysqli_connect("localhost","root","mysqluser","filmes"), $_POST["nome"]);
    $sinopse = mysqli_real_escape_string($conexao, $_POST["sinopse"]);
    $duracao = intval($_POST['duracao']);
    $classificacao = mysqli_real_escape_string($conexao, $_POST['classificacao']);
    $diretores = isset($_POST['diretores']) ? $_POST['diretores'] : [];

    $query = "INSERT INTO filmes(nome, sinopse, duracao, classificacao) 
              VALUES ('".$nome."','".$sinopse."',".$duracao.",'".$classificacao."')";

    $resultado = mysqli_query($conexao, $query);
    
    if($resultado) {
        $filme_id = mysqli_insert_id($conexao);
        
        foreach($diretores as $diretor_id) {
            $query_relacao = "INSERT INTO filmes_diretores(filme_id, diretor_id) 
                             VALUES (".$filme_id.", ".intval($diretor_id).")";
            mysqli_query($conexao, $query_relacao);
        }
        
        echo "<div class='mensagem'>Filme inserido com sucesso!</div>";
    } else {
        echo "<div style='background-color: #f8d7da; color: #721c24;' class='mensagem'>Erro ao inserir filme: ".mysqli_error($conexao)."</div>";
    }

    mysqli_close($conexao);
}

$conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));
$query_diretores = "SELECT id, nome FROM diretores ORDER BY nome ASC";
$resultado_diretores = mysqli_query($conexao, $query_diretores);
?>

    <form action="inserir_filmes.php" method="POST">
        <label>Nome do Filme:</label>
        <input type="text" name="nome" required>
        
        <label>Sinopse:</label>
        <textarea name="sinopse" rows="4" required></textarea>
        
        <label>Duração (minutos):</label>
        <input type="number" name="duracao" required>
        
        <label>Classificação Indicativa:</label>
        <input type="text" name="classificacao" placeholder="Ex: L, 10, 12, 14, 16, 18" required>
        
        <label>Diretores (selecione um ou mais):</label>
        <select name="diretores[]" multiple size="5">
            <?php
            while($diretor = mysqli_fetch_array($resultado_diretores)) {
                echo "<option value='".$diretor['id']."'>".$diretor['nome']."</option>";
            }
            mysqli_close($conexao);
            ?>
        </select>
        <small>Segure Ctrl (Windows) ou Cmd (Mac) para selecionar vários diretores</small>

        <button type="submit">Inserir Filme</button>
    </form>

    <p><a href="index.php">← Voltar para a tela principal</a></p>

</body>
</html>