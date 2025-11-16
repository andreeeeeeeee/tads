
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Filme</title>
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
        a { color: #008CBA; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <h1>Edição de Filme</h1>

<?php
    $idBuscaDados = $_POST['id_para_editar'];
    $conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

    $query = "SELECT id, nome, sinopse, duracao, classificacao FROM filmes WHERE id=".$idBuscaDados;
    $resultado = mysqli_query($conexao,$query);
    $linha = mysqli_fetch_array($resultado);
    
    $query_diretores_filme = "SELECT diretor_id FROM filmes_diretores WHERE filme_id = ".$idBuscaDados;
    $resultado_diretores_filme = mysqli_query($conexao, $query_diretores_filme);
    $diretores_selecionados = [];
    while($dir = mysqli_fetch_array($resultado_diretores_filme)) {
        $diretores_selecionados[] = $dir['diretor_id'];
    }
    
    $query_todos_diretores = "SELECT id, nome FROM diretores ORDER BY nome ASC";
    $resultado_todos_diretores = mysqli_query($conexao, $query_todos_diretores);
?>

    <form action="atualizar_filme.php" method="POST">
        <label>Nome do Filme:</label>
        <input type="text" name="nome" value="<?=$linha['nome']?>" required>
        
        <label>Sinopse:</label>
        <textarea name="sinopse" rows="4" required><?=$linha['sinopse']?></textarea>
        
        <label>Duração (minutos):</label>
        <input type="number" name="duracao" value="<?=$linha['duracao']?>" required>
        
        <label>Classificação Indicativa:</label>
        <input type="text" name="classificacao" value="<?=$linha['classificacao']?>" required>
        
        <label>Diretores (selecione um ou mais):</label>
        <select name="diretores[]" multiple size="5">
            <?php
            while($diretor = mysqli_fetch_array($resultado_todos_diretores)) {
                $selected = in_array($diretor['id'], $diretores_selecionados) ? 'selected' : '';
                echo "<option value='".$diretor['id']."' ".$selected.">".$diretor['nome']."</option>";
            }
            mysqli_close($conexao);
            ?>
        </select>
        <small>Segure Ctrl (Windows) ou Cmd (Mac) para selecionar vários diretores</small>

        <input type="hidden" name="id_para_atualizacao_form" value="<?=$linha['id']?>">

        <button type="submit">Atualizar Filme</button>
    </form>

    <p><a href="index.php">← Voltar para a tela principal</a></p>

</body>
</html>