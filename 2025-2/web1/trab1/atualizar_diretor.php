<?php

$nome = $_POST['nome'];
$nota_media = $_POST['nota_media'];
$idade = $_POST['idade'];
$premios = $_POST['premios'];
$id_para_atualizacao = $_POST['id_para_atualizacao_form'];

$conexao = mysqli_connect("localhost","root","mysqluser","filmes") or die(mysqli_error($conexao));

$nome = mysqli_real_escape_string($conexao, $nome);

$query = "UPDATE diretores
          SET nome = '".$nome."', 
              nota_media = ".$nota_media.", 
              idade = ".$idade.",
              premios = ".$premios." 
          WHERE id = ".$id_para_atualizacao;

mysqli_query($conexao, $query);
mysqli_close($conexao);

header('Location: listar_diretores.php');

?>
