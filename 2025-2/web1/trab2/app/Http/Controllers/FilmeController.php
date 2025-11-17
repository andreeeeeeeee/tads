<?php

namespace App\Http\Controllers;

use App\Models\Filme;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Validator;

class FilmeController extends Controller
{
  public function index(): JsonResponse
  {
    try {
      $filmes = Filme::with('diretores')
        ->orderBy('created_at', 'desc')
        ->get();

      return response()->json([
        'success' => true,
        'data' => $filmes,
        'count' => $filmes->count()
      ], 200);
    } catch (\Exception $e) {
      return response()->json([
        'success' => false,
        'message' => 'Erro ao listar filmes',
        'error' => config('app.debug') ? $e->getMessage() : null
      ], 500);
    }
  }

  public function store(Request $request)
  {
    try {
      $validator = Validator::make($request->all(), [
        'nome' => 'required|string|max:100',
        'sinopse' => 'nullable|string|max:100',
        'duracao' => 'nullable|integer|min:1',
        'classificacao' => 'nullable|string|max:2',
        'diretores' => 'nullable|array',
        'diretores.*' => 'exists:diretores,id'
      ], [
        'nome.required' => 'O campo nome é obrigatório.',
        'nome.max' => 'O nome não pode ter mais de 100 caracteres.',
        'duracao.integer' => 'A duração deve ser um número inteiro.',
        'duracao.min' => 'A duração deve ser pelo menos 1 minuto.',
        'diretores.array' => 'Diretores deve ser um array.',
        'diretores.*.exists' => 'Um ou mais diretores não existem.'
      ]);

      if ($validator->fails()) {
        if ($request->expectsJson()) {
          return response()->json([
            'success' => false,
            'message' => 'Dados de entrada inválidos',
            'errors' => $validator->errors()
          ], 422);
        }
        return redirect()->back()->withErrors($validator)->withInput();
      }

      $filme = Filme::create($request->only(['nome', 'sinopse', 'duracao', 'classificacao']));

      if ($request->has('diretores') && is_array($request->diretores)) {
        $filme->diretores()->attach($request->diretores);
      }

      $filme->load('diretores');

      if ($request->expectsJson()) {
        return response()->json([
          'success' => true,
          'message' => 'Filme criado com sucesso',
          'data' => $filme
        ], 201);
      }

      return redirect()->route('filmes.index')->with('success', 'Filme criado com sucesso!');
    } catch (\Exception $e) {
      if ($request->expectsJson()) {
        return response()->json([
          'success' => false,
          'message' => 'Erro ao criar filme',
          'error' => config('app.debug') ? $e->getMessage() : null
        ], 500);
      }
      return redirect()->back()->with('error', 'Erro ao criar filme: ' . $e->getMessage());
    }
  }

  public function show(string $id): JsonResponse
  {
    try {
      $filme = Filme::with('diretores')->find($id);

      if (!$filme) {
        return response()->json([
          'success' => false,
          'message' => 'Filme não encontrado'
        ], 404);
      }

      return response()->json([
        'success' => true,
        'data' => $filme
      ], 200);
    } catch (\Exception $e) {
      return response()->json([
        'success' => false,
        'message' => 'Erro ao buscar filme',
        'error' => config('app.debug') ? $e->getMessage() : null
      ], 500);
    }
  }

  public function update(Request $request, string $id)
  {
    try {
      $filme = Filme::find($id);

      if (!$filme) {
        if ($request->expectsJson()) {
          return response()->json([
            'success' => false,
            'message' => 'Filme não encontrado'
          ], 404);
        }
        return redirect()->route('filmes.index')->with('error', 'Filme não encontrado!');
      }

      $validator = Validator::make($request->all(), [
        'nome' => 'sometimes|required|string|max:100',
        'sinopse' => 'nullable|string|max:100',
        'duracao' => 'nullable|integer|min:1',
        'classificacao' => 'nullable|string|max:2',
        'diretores' => 'nullable|array',
        'diretores.*' => 'exists:diretores,id'
      ], [
        'nome.required' => 'O campo nome é obrigatório.',
        'nome.max' => 'O nome não pode ter mais de 100 caracteres.',
        'duracao.integer' => 'A duração deve ser um número inteiro.',
        'duracao.min' => 'A duração deve ser pelo menos 1 minuto.',
        'diretores.array' => 'Diretores deve ser um array.',
        'diretores.*.exists' => 'Um ou mais diretores não existem.'
      ]);

      if ($validator->fails()) {
        if ($request->expectsJson()) {
          return response()->json([
            'success' => false,
            'message' => 'Dados de entrada inválidos',
            'errors' => $validator->errors()
          ], 422);
        }
        return redirect()->back()->withErrors($validator)->withInput();
      }

      $filme->update($request->only(['nome', 'sinopse', 'duracao', 'classificacao']));

      if ($request->has('diretores') && is_array($request->diretores)) {
        $filme->diretores()->sync($request->diretores);
      }

      $filme->load('diretores');

      if ($request->expectsJson()) {
        return response()->json([
          'success' => true,
          'message' => 'Filme atualizado com sucesso',
          'data' => $filme
        ], 200);
      }

      return redirect()->route('filmes.index')->with('success', 'Filme atualizado com sucesso!');
    } catch (\Exception $e) {
      if ($request->expectsJson()) {
        return response()->json([
          'success' => false,
          'message' => 'Erro ao atualizar filme',
          'error' => config('app.debug') ? $e->getMessage() : null
        ], 500);
      }
      return redirect()->back()->with('error', 'Erro ao atualizar filme: ' . $e->getMessage());
    }
  }

  public function destroy(Request $request, string $id)
  {
    try {
      $filme = Filme::find($id);

      if (!$filme) {
        if ($request->expectsJson()) {
          return response()->json([
            'success' => false,
            'message' => 'Filme não encontrado'
          ], 404);
        }
        return redirect()->route('filmes.index')->with('error', 'Filme não encontrado!');
      }

      $filme->delete();

      if ($request->expectsJson()) {
        return response()->json([
          'success' => true,
          'message' => 'Filme deletado com sucesso'
        ], 200);
      }

      return redirect()->route('filmes.index')->with('success', 'Filme deletado com sucesso!');
    } catch (\Exception $e) {
      if ($request->expectsJson()) {
        return response()->json([
          'success' => false,
          'message' => 'Erro ao deletar filme',
          'error' => config('app.debug') ? $e->getMessage() : null
        ], 500);
      }
      return redirect()->back()->with('error', 'Erro ao deletar filme: ' . $e->getMessage());
    }
  }

  public function indexView()
  {
    $filmes = Filme::with('diretores')->orderBy('created_at', 'desc')->get();
    return view('filmes.index', compact('filmes'));
  }

  public function createView()
  {
    $diretores = \App\Models\Diretor::all();
    return view('filmes.create', compact('diretores'));
  }

  public function editView(string $id)
  {
    $filme = Filme::with('diretores')->findOrFail($id);
    $diretores = \App\Models\Diretor::all();
    return view('filmes.edit', compact('filme', 'diretores'));
  }

  public function showView(string $id)
  {
    $filme = Filme::with('diretores')->findOrFail($id);
    return view('filmes.show', compact('filme'));
  }
}
