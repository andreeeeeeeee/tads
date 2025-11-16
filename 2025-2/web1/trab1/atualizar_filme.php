<?php

$nome = $_POST['nome'];
$sinopse = $_POST['sinopse'];
$duracao = $_POST['duracao'];
$classificacao = $_POST['classificacao'];
$id_para_atualizacao = $_POST['id_para_atualizacao_form'];
$diretores = isset($_POST['diretores']) ? $_POST['diretores'] : [];

$conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

$nome = mysqli_real_escape_string($conexao, $nome);
$sinopse = mysqli_real_escape_string($conexao, $sinopse);
$classificacao = mysqli_real_escape_string($conexao, $classificacao);

$query = "UPDATE filmes
          SET nome = '".$nome."', 
              sinopse = '".$sinopse."', 
              duracao = ".$duracao.",
              classificacao = '".$classificacao."' 
          WHERE id = ".$id_para_atualizacao;

mysqli_query($conexao, $query);

$query_delete = "DELETE FROM filmes_diretores WHERE filme_id = ".$id_para_atualizacao;
mysqli_query($conexao, $query_delete);

foreach($diretores as $diretor_id) {
    $query_relacao = "INSERT INTO filmes_diretores(filme_id, diretor_id) 
                     VALUES (".$id_para_atualizacao.", ".intval($diretor_id).")";
    mysqli_query($conexao, $query_relacao);
}

mysqli_close($conexao);

header('Location: index.php');

?>