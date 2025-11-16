<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listar Diretores</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        h1 { color: #333; }
        .menu { margin: 20px 0; }
        .menu a { 
            display: inline-block; 
            padding: 10px 15px; 
            margin: 5px; 
            background-color: #4CAF50; 
            color: white; 
            text-decoration: none; 
            border-radius: 5px; 
        }
        .menu a:hover { background-color: #45a049; }
        .search-box { margin: 20px 0; }
        .search-box input[type="text"] { 
            padding: 8px; 
            width: 300px; 
            border: 1px solid #ccc; 
            border-radius: 4px; 
        }
        .search-box button { 
            padding: 8px 15px; 
            background-color: #008CBA; 
            color: white; 
            border: none; 
            border-radius: 4px; 
            cursor: pointer; 
        }
        .search-box button:hover { background-color: #007399; }
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border: 1px solid #ddd; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f2f2f2; }
        button { cursor: pointer; padding: 5px 10px; }
        .btn-remover { background-color: #f44336; color: white; border: none; border-radius: 3px; }
        .btn-editar { background-color: #008CBA; color: white; border: none; border-radius: 3px; }
    </style>
</head>
<body>
    <h1>Diretores</h1>
    
    <div class="menu">
        <a href="index.php">← Voltar para Filmes</a>
        <a href="inserir_diretor.php">Inserir Diretor</a>
    </div>

    <div class="search-box">
        <form method="GET" action="listar_diretores.php">
            <input type="text" name="pesquisa" placeholder="Pesquisar diretores..." 
                   value="<?php echo isset($_GET['pesquisa']) ? htmlspecialchars($_GET['pesquisa']) : ''; ?>">
            <button type="submit">Pesquisar</button>
            <?php if(isset($_GET['pesquisa'])): ?>
                <a href="listar_diretores.php" style="margin-left: 10px;">Limpar</a>
            <?php endif; ?>
        </form>
    </div>

<?php
$conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

$pesquisa = isset($_GET['pesquisa']) ? $_GET['pesquisa'] : '';

if (!empty($pesquisa)) {
    $query = "SELECT * FROM diretores 
              WHERE nome LIKE '%".$pesquisa."%' 
              ORDER BY nome ASC";
} else {
    $query = "SELECT * FROM diretores ORDER BY nome ASC";
}

$resultado = mysqli_query($conexao,$query);
?>

<table>
    <tr>
        <th>Nome</th>
        <th>Nota Média</th>
        <th>Idade</th>
        <th>Prêmios</th>
        <th>Filmes</th>
        <th>Remover</th>
        <th>Editar</th>
    </tr>

<?php
while($linha = mysqli_fetch_array($resultado)){
    $query_filmes = "SELECT f.nome FROM filmes f
                     INNER JOIN filmes_diretores fd ON f.id = fd.filme_id
                     WHERE fd.diretor_id = ".$linha['id'];
    $resultado_filmes = mysqli_query($conexao, $query_filmes);
    
    $filmes = [];
    while($filme = mysqli_fetch_array($resultado_filmes)) {
        $filmes[] = $filme['nome'];
    }
    $filmes_texto = !empty($filmes) ? implode(", ", $filmes) : "Nenhum filme";
    
    echo "<tr>
        <td>".$linha['nome']."</td>
        <td>".$linha['nota_media']."</td>
        <td>".$linha['idade']."</td>
        <td>".$linha['premios']."</td>
        <td>".$filmes_texto."</td>
        <td>
            <form action='remover.php' method='POST'>
                <input type='hidden' name='id_para_remover' value=".$linha['id'].">
                <input type='hidden' name='tipo' value='diretor'>
                <button type='submit' class='btn-remover'>Remover</button>
            </form>
        </td>
        <td>
            <form action='editar_diretor.php' method='POST'>
                <input type='hidden' name='id_para_editar' value=".$linha['id'].">
                <button type='submit' class='btn-editar'>Editar</button>
            </form>
        </td>
    </tr>";
}

mysqli_close($conexao);
?>
</table>

</body>
</html>
