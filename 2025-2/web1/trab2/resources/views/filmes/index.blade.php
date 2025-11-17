@extends('layouts.app')

@section('content')
<div class="container">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h1>Lista de Filmes</h1>
    <a href="{{ route('filmes.create') }}" class="btn btn-primary">
      <i class="bi bi-plus-circle"></i> Adicionar Filme
    </a>
  </div>

  @if($filmes->count() > 0)
  <div class="table-responsive">
    <table class="table table-striped table-hover">
      <thead class="table-dark">
        <tr>
          <th>ID</th>
          <th>Nome</th>
          <th>Sinopse</th>
          <th>Duração</th>
          <th>Classificação</th>
          <th>Diretores</th>
          <th class="text-center">Ações</th>
        </tr>
      </thead>
      <tbody>
        @foreach($filmes as $filme)
        <tr>
          <td>{{ $filme->id }}</td>
          <td>{{ $filme->nome }}</td>
          <td>{{ Str::limit($filme->sinopse, 50) }}</td>
          <td>{{ $filme->duracao ? $filme->duracao . ' min' : '-' }}</td>
          <td>{{ $filme->classificacao ?? '-' }}</td>
          <td>
            @if($filme->diretores && $filme->diretores->count() > 0)
            {{ $filme->diretores->pluck('nome')->join(', ') }}
            @else
            <span class="text-muted">Nenhum</span>
            @endif
          </td>
          <td>
            <div class="btn-group d-flex space-between" role="group">
              <a href="{{ route('filmes.show', $filme->id) }}" class="btn btn-info btn-sm" title="Ver detalhes">
                Ver
              </a>
              <a href="{{ route('filmes.edit', $filme->id) }}" class="btn btn-warning btn-sm" title="Editar">
                Editar
              </a>
              <form action="{{ route('filmes.destroy', $filme->id) }}" method="POST" style="display:inline;" onsubmit="return confirm('Tem certeza que deseja excluir este filme?');">
                @csrf
                @method('DELETE')
                <button type="submit" class="btn btn-danger btn-sm" title="">Excluir</button>
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
    <p class="mb-0">Nenhum filme cadastrado. <a href="{{ route('filmes.create') }}">Clique aqui</a> para adicionar o primeiro filme.</p>
  </div>
  @endif
</div>
@endsection