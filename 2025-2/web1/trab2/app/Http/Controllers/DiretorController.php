<?php

namespace App\Http\Controllers;

use App\Models\Diretor;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;
use Illuminate\Support\Facades\Validator;

class DiretorController extends Controller
{
  public function indexView()
  {
    $diretores = Diretor::with('filmes')->orderBy('created_at', 'desc')->get();
    return view('diretores.index', compact('diretores'));
  }

  public function createView()
  {
    $filmes = \App\Models\Filme::all();
    return view('diretores.create', compact('filmes'));
  }

  public function editView(string $id)
  {
    $diretor = Diretor::with('filmes')->findOrFail($id);
    $filmes = \App\Models\Filme::all();
    return view('diretores.edit', compact('diretor', 'filmes'));
  }

  public function showView(string $id)
  {
    $diretor = Diretor::with('filmes')->findOrFail($id);
    return view('diretores.show', compact('diretor'));
  }

  public function index(): JsonResponse
  {
    try {
      $diretores = Diretor::with('filmes')
        ->orderBy('created_at', 'desc')
        ->get();

      return response()->json([
        'success' => true,
        'data' => $diretores,
        'count' => $diretores->count()
      ], 200);
    } catch (\Exception $e) {
      return response()->json([
        'success' => false,
        'message' => 'Erro ao listar diretores',
        'error' => config('app.debug') ? $e->getMessage() : null
      ], 500);
    }
  }

  public function store(Request $request)
  {
    try {
      $validator = Validator::make($request->all(), [
        'nome' => 'required|string|max:50',
        'nota_media' => 'nullable|numeric|min:0|max:10',
        'idade' => 'nullable|integer|min:1|max:150',
        'premios' => 'nullable|integer|min:0',
        'filmes' => 'nullable|array',
        'filmes.*' => 'exists:filmes,id'
      ], [
        'nome.required' => 'O campo nome é obrigatório.',
        'nome.max' => 'O nome não pode ter mais de 50 caracteres.',
        'nota_media.numeric' => 'A nota média deve ser um valor numérico.',
        'nota_media.min' => 'A nota média não pode ser negativa.',
        'nota_media.max' => 'A nota média não pode ser maior que 10.',
        'idade.integer' => 'A idade deve ser um número inteiro.',
        'idade.min' => 'A idade deve ser pelo menos 1.',
        'idade.max' => 'A idade não pode ser maior que 150.',
        'premios.integer' => 'O número de prêmios deve ser um número inteiro.',
        'premios.min' => 'O número de prêmios não pode ser negativo.',
        'filmes.array' => 'Filmes deve ser um array.',
        'filmes.*.exists' => 'Um ou mais filmes não existem.'
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

      $diretor = Diretor::create($request->only(['nome', 'nota_media', 'idade', 'premios']));

      if ($request->has('filmes') && is_array($request->filmes)) {
        $diretor->filmes()->attach($request->filmes);
      }

      $diretor->load('filmes');

      if ($request->expectsJson()) {
        return response()->json([
          'success' => true,
          'message' => 'Diretor criado com sucesso',
          'data' => $diretor
        ], 201);
      }

      return redirect()->route('diretores.index')->with('success', 'Diretor criado com sucesso!');
    } catch (\Exception $e) {
      if ($request->expectsJson()) {
        return response()->json([
          'success' => false,
          'message' => 'Erro ao criar diretor',
          'error' => config('app.debug') ? $e->getMessage() : null
        ], 500);
      }
      return redirect()->back()->with('error', 'Erro ao criar diretor: ' . $e->getMessage());
    }
  }

  public function show(string $id): JsonResponse
  {
    try {
      $diretor = Diretor::with('filmes')->find($id);

      if (!$diretor) {
        return response()->json([
          'success' => false,
          'message' => 'Diretor não encontrado'
        ], 404);
      }

      return response()->json([
        'success' => true,
        'data' => $diretor
      ], 200);
    } catch (\Exception $e) {
      return response()->json([
        'success' => false,
        'message' => 'Erro ao buscar diretor',
        'error' => config('app.debug') ? $e->getMessage() : null
      ], 500);
    }
  }

  public function update(Request $request, string $id)
  {
    try {
      $diretor = Diretor::find($id);

      if (!$diretor) {
        if ($request->expectsJson()) {
          return response()->json([
            'success' => false,
            'message' => 'Diretor não encontrado'
          ], 404);
        }
        return redirect()->route('diretores.index')->with('error', 'Diretor não encontrado!');
      }

      $validator = Validator::make($request->all(), [
        'nome' => 'sometimes|required|string|max:50',
        'nota_media' => 'nullable|numeric|min:0|max:10',
        'idade' => 'nullable|integer|min:1|max:150',
        'premios' => 'nullable|integer|min:0',
        'filmes' => 'nullable|array',
        'filmes.*' => 'exists:filmes,id'
      ], [
        'nome.required' => 'O campo nome é obrigatório.',
        'nome.max' => 'O nome não pode ter mais de 50 caracteres.',
        'nota_media.numeric' => 'A nota média deve ser um valor numérico.',
        'nota_media.min' => 'A nota média não pode ser negativa.',
        'nota_media.max' => 'A nota média não pode ser maior que 10.',
        'idade.integer' => 'A idade deve ser um número inteiro.',
        'idade.min' => 'A idade deve ser pelo menos 1.',
        'idade.max' => 'A idade não pode ser maior que 150.',
        'premios.integer' => 'O número de prêmios deve ser um número inteiro.',
        'premios.min' => 'O número de prêmios não pode ser negativo.',
        'filmes.array' => 'Filmes deve ser um array.',
        'filmes.*.exists' => 'Um ou mais filmes não existem.'
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

      $diretor->update($request->only(['nome', 'nota_media', 'idade', 'premios']));

      if ($request->has('filmes') && is_array($request->filmes)) {
        $diretor->filmes()->sync($request->filmes);
      }

      $diretor->load('filmes');

      if ($request->expectsJson()) {
        return response()->json([
          'success' => true,
          'message' => 'Diretor atualizado com sucesso',
          'data' => $diretor
        ], 200);
      }

      return redirect()->route('diretores.index')->with('success', 'Diretor atualizado com sucesso!');
    } catch (\Exception $e) {
      if ($request->expectsJson()) {
        return response()->json([
          'success' => false,
          'message' => 'Erro ao atualizar diretor',
          'error' => config('app.debug') ? $e->getMessage() : null
        ], 500);
      }
      return redirect()->back()->with('error', 'Erro ao atualizar diretor: ' . $e->getMessage());
    }
  }

  public function destroy(Request $request, string $id)
  {
    try {
      $diretor = Diretor::find($id);

      if (!$diretor) {
        if ($request->expectsJson()) {
          return response()->json([
            'success' => false,
            'message' => 'Diretor não encontrado'
          ], 404);
        }
        return redirect()->route('diretores.index')->with('error', 'Diretor não encontrado!');
      }

      $diretor->delete();

      if ($request->expectsJson()) {
        return response()->json([
          'success' => true,
          'message' => 'Diretor deletado com sucesso'
        ], 200);
      }

      return redirect()->route('diretores.index')->with('success', 'Diretor deletado com sucesso!');
    } catch (\Exception $e) {
      if ($request->expectsJson()) {
        return response()->json([
          'success' => false,
          'message' => 'Erro ao deletar diretor',
          'error' => config('app.debug') ? $e->getMessage() : null
        ], 500);
      }
      return redirect()->back()->with('error', 'Erro ao deletar diretor: ' . $e->getMessage());
    }
  }
}
