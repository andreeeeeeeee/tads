<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Inserir Diretor</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        form { max-width: 500px; }
        label { display: block; margin-top: 10px; font-weight: bold; }
        input { 
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
    <h1>Inserir Novo Diretor</h1>

<?php

if ($_SERVER["REQUEST_METHOD"] == "POST"){

    $conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));
    
    $nome = mysqli_real_escape_string($conexao, $_POST["nome"]);
    $nota_media = floatval($_POST['nota_media']);
    $idade = intval($_POST['idade']);
    $premios = intval($_POST['premios']);

    $query = "INSERT INTO diretores(nome, nota_media, idade, premios) 
              VALUES ('".$nome."', ".$nota_media.", ".$idade.", ".$premios.")";

    $resultado = mysqli_query($conexao, $query);
    
    if($resultado) {
        echo "<div class='mensagem'>Diretor inserido com sucesso!</div>";
    } else {
        echo "<div style='background-color: #f8d7da; color: #721c24;' class='mensagem'>Erro ao inserir diretor: ".mysqli_error($conexao)."</div>";
    }

    mysqli_close($conexao);
}

?>

    <form action="inserir_diretor.php" method="POST">
        <label>Nome do Diretor:</label>
        <input type="text" name="nome" required>
        
        <label>Nota Média (0-10):</label>
        <input type="number" step="0.1" min="0" max="10" name="nota_media" required>
        
        <label>Idade:</label>
        <input type="number" name="idade" required>
        
        <label>Número de Prêmios:</label>
        <input type="number" name="premios" value="0" required>

        <button type="submit">Inserir Diretor</button>
    </form>

    <p><a href="index.php">← Voltar para a tela principal</a></p>

</body>
</html>
