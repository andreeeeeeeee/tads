<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Diretor</title>
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
        a { color: #008CBA; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Edição de Diretor</h1>

<?php
    $idBuscaDados = $_POST['id_para_editar'];
    $conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

    $query = "SELECT id, nome, nota_media, idade, premios FROM diretores WHERE id=".$idBuscaDados;
    $resultado = mysqli_query($conexao,$query);
    $linha = mysqli_fetch_array($resultado);
    mysqli_close($conexao);
?>

    <form action="atualizar_diretor.php" method="POST">
        <label>Nome do Diretor:</label>
        <input type="text" name="nome" value="<?=$linha['nome']?>" required>
        
        <label>Nota Média (0-10):</label>
        <input type="number" step="0.1" min="0" max="10" name="nota_media" value="<?=$linha['nota_media']?>" required>
        
        <label>Idade:</label>
        <input type="number" name="idade" value="<?=$linha['idade']?>" required>
        
        <label>Número de Prêmios:</label>
        <input type="number" name="premios" value="<?=$linha['premios']?>" required>

        <input type="hidden" name="id_para_atualizacao_form" value="<?=$linha['id']?>">

        <button type="submit">Atualizar Diretor</button>
    </form>

    <p><a href="listar_diretores.php">← Voltar para lista de diretores</a></p>

</body>
</html>
