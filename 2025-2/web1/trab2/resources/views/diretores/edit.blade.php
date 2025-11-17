@extends('layouts.app')

@section('content')
<div class="container">
  <h1>Editar Diretor</h1>
  <form action="{{ route('diretores.update', $diretor->id) }}" method="POST">
    @csrf
    @method('PUT')

    <div class="mb-3">
      <label for="nome" class="form-label">Nome do Diretor *</label>
      <input type="text" name="nome" id="nome" class="form-control" value="{{ old('nome', $diretor->nome) }}" required maxlength="50">
    </div>

    <div class="mb-3">
      <label for="nota_media" class="form-label">Nota Média</label>
      <input type="number" name="nota_media" id="nota_media" class="form-control" value="{{ old('nota_media', $diretor->nota_media) }}" min="0" max="10" step="0.1" placeholder="0.0 a 10.0">
      <div class="form-text">Nota de 0 a 10</div>
    </div>

    <div class="mb-3">
      <label for="idade" class="form-label">Idade</label>
      <input type="number" name="idade" id="idade" class="form-control" value="{{ old('idade', $diretor->idade) }}" min="1" max="150">
    </div>

    <div class="mb-3">
      <label for="premios" class="form-label">Número de Prêmios</label>
      <input type="number" name="premios" id="premios" class="form-control" value="{{ old('premios', $diretor->premios) }}" min="0">
    </div>

    @if(isset($filmes) && $filmes->count() > 0)
    <div class="mb-3">
      <label class="form-label">Filmes</label>
      @foreach($filmes as $filme)
      <div class="form-check">
        <input class="form-check-input" type="checkbox" name="filmes[]" value="{{ $filme->id }}" id="filme{{ $filme->id }}"
          {{ $diretor->filmes->contains($filme->id) ? 'checked' : '' }}>
        <label class="form-check-label" for="filme{{ $filme->id }}">
          {{ $filme->nome }}
        </label>
      </div>
      @endforeach
    </div>
    @endif

    <div class="d-flex gap-2">
      <button type="submit" class="btn btn-success">Atualizar</button>
      <a href="{{ route('diretores.index') }}" class="btn btn-secondary">Cancelar</a>
    </div>
  </form>
</div>
@endsection