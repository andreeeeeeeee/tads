@extends('layouts.app')

@section('content')
<div class="container">
  <div class="card">
    <div class="card-header">
      <h1>Detalhes do Filme</h1>
    </div>
    <div class="card-body">
      <dl class="row">
        <dt class="col-sm-3">ID:</dt>
        <dd class="col-sm-9">{{ $filme->id }}</dd>

        <dt class="col-sm-3">Nome:</dt>
        <dd class="col-sm-9">{{ $filme->nome }}</dd>

        <dt class="col-sm-3">Sinopse:</dt>
        <dd class="col-sm-9">{{ $filme->sinopse ?? '-' }}</dd>

        <dt class="col-sm-3">Duração:</dt>
        <dd class="col-sm-9">{{ $filme->duracao ? $filme->duracao . ' minutos' : '-' }}</dd>

        <dt class="col-sm-3">Classificação:</dt>
        <dd class="col-sm-9">{{ $filme->classificacao ?? '-' }}</dd>

        <dt class="col-sm-3">Diretores:</dt>
        <dd class="col-sm-9">
          @if($filme->diretores && $filme->diretores->count() > 0)
          <ul class="list-unstyled mb-0">
            @foreach($filme->diretores as $diretor)
            <li>
              <a href="{{ route('diretores.show', $diretor->id) }}">{{ $diretor->nome }}</a>
            </li>
            @endforeach
          </ul>
          @else
          <span class="text-muted">Nenhum diretor associado</span>
          @endif
        </dd>

        <dt class="col-sm-3">Criado em:</dt>
        <dd class="col-sm-9">{{ $filme->created_at->format('d/m/Y H:i') }}</dd>

        <dt class="col-sm-3">Atualizado em:</dt>
        <dd class="col-sm-9">{{ $filme->updated_at->format('d/m/Y H:i') }}</dd>
      </dl>
    </div>
    <div class="card-footer">
      <a href="{{ route('filmes.index') }}" class="btn btn-secondary">Voltar</a>
      <a href="{{ route('filmes.edit', $filme->id) }}" class="btn btn-warning">Editar</a>
      <form action="{{ route('filmes.destroy', $filme->id) }}" method="POST" style="display:inline;" onsubmit="return confirm('Tem certeza que deseja excluir este filme?');">
        @csrf
        @method('DELETE')
        <button type="submit" class="btn btn-danger">Excluir</button>
      </form>
    </div>
  </div>
</div>
@endsection