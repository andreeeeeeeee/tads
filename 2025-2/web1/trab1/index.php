<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciamento de Filmes e Diretores</title>
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
    <h1>Sistema de Gerenciamento de Filmes e Diretores</h1>
    
    <div class="menu">
        <a href="inserir_filmes.php">Inserir Filme</a>
        <a href="inserir_diretor.php">Inserir Diretor</a>
        <a href="listar_diretores.php">Listar Diretores</a>
    </div>

    <div class="search-box">
        <form method="GET" action="index.php">
            <input type="text" name="pesquisa" placeholder="Pesquisar filmes ou diretores..." 
                   value="<?php echo isset($_GET['pesquisa']) ? htmlspecialchars($_GET['pesquisa']) : ''; ?>">
            <button type="submit">Pesquisar</button>
            <?php if(isset($_GET['pesquisa'])): ?>
                <a href="index.php" style="margin-left: 10px;">Limpar</a>
            <?php endif; ?>
        </form>
    </div>

    <h2>Filmes</h2>

<?php
$conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

$pesquisa = isset($_GET['pesquisa']) ? $_GET['pesquisa'] : '';

if (!empty($pesquisa)) {
    $query = "SELECT DISTINCT f.* FROM filmes f
              LEFT JOIN filmes_diretores fd ON f.id = fd.filme_id
              LEFT JOIN diretores d ON fd.diretor_id = d.id
              WHERE f.nome LIKE '%".$pesquisa."%'
              OR d.nome LIKE '%".$pesquisa."%'
              ORDER BY f.nome ASC";
} else {
    $query = "SELECT * FROM filmes ORDER BY nome ASC";
}

$resultado = mysqli_query($conexao,$query);
?>

<table>
    <tr>
        <th>Nome</th>
        <th>Sinopse</th>
        <th>Duração (min)</th>
        <th>Classificação</th>
        <th>Diretores</th>
        <th>Remover</th>
        <th>Editar</th>
    </tr>

<?php
while($linha = mysqli_fetch_array($resultado)){
    $query_diretores = "SELECT d.nome FROM diretores d
                        INNER JOIN filmes_diretores fd ON d.id = fd.diretor_id
                        WHERE fd.filme_id = ".$linha['id'];
    $resultado_diretores = mysqli_query($conexao, $query_diretores);
    
    $diretores = [];
    while($diretor = mysqli_fetch_array($resultado_diretores)) {
        $diretores[] = $diretor['nome'];
    }
    $diretores_texto = !empty($diretores) ? implode(", ", $diretores) : "Sem diretor";
    
    echo "<tr>
        <td>".$linha['nome']."</td>
        <td>".$linha['sinopse']."</td>
        <td>".$linha['duracao']."</td>
        <td>".$linha['classificacao']."</td>
        <td>".$diretores_texto."</td>
        <td>
            <form action='remover.php' method='POST'>
                <input type='hidden' name='id_para_remover' value=".$linha['id'].">
                <input type='hidden' name='tipo' value='filme'>
                <button type='submit' class='btn-remover'>Remover</button>
            </form>
        </td>
        <td>
            <form action='editar_filme.php' method='POST'>
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