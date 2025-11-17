@extends('layouts.app')

@section('content')
<div class="container">
  <div class="card">
    <div class="card-header">
      <h1>Detalhes do Diretor</h1>
    </div>
    <div class="card-body">
      <dl class="row">
        <dt class="col-sm-3">ID:</dt>
        <dd class="col-sm-9">{{ $diretor->id }}</dd>

        <dt class="col-sm-3">Nome:</dt>
        <dd class="col-sm-9">{{ $diretor->nome }}</dd>

        <dt class="col-sm-3">Nota Média:</dt>
        <dd class="col-sm-9">{{ $diretor->nota_media ?? '-' }}</dd>

        <dt class="col-sm-3">Idade:</dt>
        <dd class="col-sm-9">{{ $diretor->idade ?? '-' }}</dd>

        <dt class="col-sm-3">Prêmios:</dt>
        <dd class="col-sm-9">{{ $diretor->premios ?? '0' }}</dd>

        <dt class="col-sm-3">Filmes:</dt>
        <dd class="col-sm-9">
          @if($diretor->filmes && $diretor->filmes->count() > 0)
          <ul class="list-unstyled mb-0">
            @foreach($diretor->filmes as $filme)
            <li>
              <a href="{{ route('filmes.show', $filme->id) }}">{{ $filme->nome }}</a>
            </li>
            @endforeach
          </ul>
          @else
          <span class="text-muted">Nenhum filme associado</span>
          @endif
        </dd>

        <dt class="col-sm-3">Criado em:</dt>
        <dd class="col-sm-9">{{ $diretor->created_at->format('d/m/Y H:i') }}</dd>

        <dt class="col-sm-3">Atualizado em:</dt>
        <dd class="col-sm-9">{{ $diretor->updated_at->format('d/m/Y H:i') }}</dd>
      </dl>
    </div>
    <div class="card-footer">
      <a href="{{ route('diretores.index') }}" class="btn btn-secondary">Voltar</a>
      <a href="{{ route('diretores.edit', $diretor->id) }}" class="btn btn-warning">Editar</a>
      <form action="{{ route('diretores.destroy', $diretor->id) }}" method="POST" style="display:inline;" onsubmit="return confirm('Tem certeza que deseja excluir este diretor?');">
        @csrf
        @method('DELETE')
        <button type="submit" class="btn btn-danger">Excluir</button>
      </form>
    </div>
  </div>
</div>
@endsection