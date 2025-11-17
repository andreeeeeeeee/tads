@extends('layouts.app')

@section('content')
<div class="container">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1>Lista de Diretores</h1>
    <a href="{{ route('diretores.create') }}" class="btn btn-primary">
      <i class="bi bi-plus-circle"></i> Adicionar Diretor
    </a>
  </div>

  @if($diretores->count() > 0)
  <div class="table-responsive">
    <table class="table table-striped table-hover">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Nota Média</th>
          <th>Idade</th>
          <th>Prêmios</th>
          <th>Filmes</th>
          <th class="text-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        @foreach($diretores as $diretor)
        <tr>
          <td>{{ $diretor->id }}</td>
          <td>{{ $diretor->nome }}</td>
          <td>{{ $diretor->nota_media ?? '-' }}</td>
          <td>{{ $diretor->idade ?? '-' }}</td>
          <td>{{ $diretor->premios ?? '0' }}</td>
          <td>
            @if($diretor->filmes && $diretor->filmes->count() > 0)
            {{ $diretor->filmes->count() }} filme(s)
            @else
            <span class="text-muted">Nenhum</span>
            @endif
          </td>
          <td>
            <div class="btn-group d-flex space-between" role="group">
              <a href="{{ route('diretores.show', $diretor->id) }}" class="btn btn-info btn-sm" title="Ver detalhes">
                Ver
              </a>
              <a href="{{ route('diretores.edit', $diretor->id) }}" class="btn btn-warning btn-sm" title="Editar">
                Editar
              </a>
              <form action="{{ route('diretores.destroy', $diretor->id) }}" method="POST" style="display:inline;" onsubmit="return confirm('Tem certeza que deseja excluir este diretor?');">
                @csrf
                @method('DELETE')
                <button type="submit" class="btn btn-danger btn-sm" title="Excluir">Excluir</button>
              </form>
            </div>
          </td>
        </tr>
        @endforeach
      </tbody>
    </table>
  </div>
  @else
  <div class="alert alert-info">
    <p class="mb-0">Nenhum diretor cadastrado. <a href="{{ route('diretores.create') }}">Clique aqui</a> para adicionar o primeiro diretor.</p>
  </div>
  @endif
</div>
@endsection