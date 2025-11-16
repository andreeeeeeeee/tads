<?php
$id = $_POST['id_para_remover'];
$tipo = isset($_POST['tipo']) ? $_POST['tipo'] : 'filme';

$conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

if($tipo == 'filme') {
    // A exclusão dos relacionamentos em filmes_diretores será feita automaticamente pelo ON DELETE CASCADE
    $query = "DELETE FROM filmes WHERE id = ".$id;
    mysqli_query($conexao, $query);
    mysqli_close($conexao); 
    header('Location: index.php');
} else if($tipo == 'diretor') {
    // A exclusão dos relacionamentos em filmes_diretores será feita automaticamente pelo ON DELETE CASCADE
    $query = "DELETE FROM diretores WHERE id = ".$id;
    mysqli_query($conexao, $query);
    mysqli_close($conexao); 
    header('Location: listar_diretores.php');
}
?>
