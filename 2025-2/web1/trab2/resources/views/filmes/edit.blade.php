@extends('layouts.app')

@section('content')
<div class="container">
  <h1>Editar Filme</h1>
  <form action="{{ route('filmes.update', $filme->id) }}" method="POST">
    @csrf
    @method('PUT')

    <div class="mb-3">
      <label for="nome" class="form-label">Nome do Filme *</label>
      <input type="text" name="nome" id="nome" class="form-control" value="{{ old('nome', $filme->nome) }}" required maxlength="100">
    </div>

    <div class="mb-3">
      <label for="sinopse" class="form-label">Sinopse</label>
      <textarea name="sinopse" id="sinopse" class="form-control" rows="3" maxlength="100">{{ old('sinopse', $filme->sinopse) }}</textarea>
      <div class="form-text">Máximo 100 caracteres</div>
    </div>

    <div class="mb-3">
      <label for="duracao" class="form-label">Duração (minutos)</label>
      <input type="number" name="duracao" id="duracao" class="form-control" value="{{ old('duracao', $filme->duracao) }}" min="1">
    </div>

    <div class="mb-3">
      <label for="classificacao" class="form-label">Classificação</label>
      <input type="text" name="classificacao" id="classificacao" class="form-control" value="{{ old('classificacao', $filme->classificacao) }}" maxlength="2" placeholder="Ex: L, 10, 12, 14, 16, 18">
      <div class="form-text">Máximo 2 caracteres (Ex: L, 10, 12, 14, 16, 18)</div>
    </div>

    @if(isset($diretores) && $diretores->count() > 0)
    <div class="mb-3">
      <label class="form-label">Diretores</label>
      @foreach($diretores as $diretor)
      <div class="form-check">
        <input class="form-check-input" type="checkbox" name="diretores[]" value="{{ $diretor->id }}" id="diretor{{ $diretor->id }}"
          {{ $filme->diretores->contains($diretor->id) ? 'checked' : '' }}>
        <label class="form-check-label" for="diretor{{ $diretor->id }}">
          {{ $diretor->nome }}
        </label>
      </div>
      @endforeach
    </div>
    @endif

    <div class="d-flex gap-2">
      <button type="submit" class="btn btn-success">Atualizar</button>
      <a href="{{ route('filmes.index') }}" class="btn btn-secondary">Cancelar</a>
    </div>
  </form>
</div>
@endsection