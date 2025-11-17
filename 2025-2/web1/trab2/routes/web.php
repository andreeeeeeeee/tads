<?php

use App\Http\Controllers\FilmeController;
use App\Http\Controllers\DiretorController;
use Illuminate\Support\Facades\Route;
use Illuminate\Foundation\Inspiring;


Route::get('/', function () {
  return redirect()->route('filmes.index');
});

Route::get('/filmes', [FilmeController::class, 'indexView'])->name('filmes.index');
Route::get('/filmes/criar', [FilmeController::class, 'createView'])->name('filmes.create');
Route::post('/filmes', [FilmeController::class, 'store'])->name('filmes.store');
Route::get('/filmes/{id}', [FilmeController::class, 'showView'])->name('filmes.show');
Route::get('/filmes/{id}/editar', [FilmeController::class, 'editView'])->name('filmes.edit');
Route::put('/filmes/{id}', [FilmeController::class, 'update'])->name('filmes.update');
Route::delete('/filmes/{id}', [FilmeController::class, 'destroy'])->name('filmes.destroy');

Route::get('/diretores', [DiretorController::class, 'indexView'])->name('diretores.index');
Route::get('/diretores/criar', [DiretorController::class, 'createView'])->name('diretores.create');
Route::post('/diretores', [DiretorController::class, 'store'])->name('diretores.store');
Route::get('/diretores/{id}', [DiretorController::class, 'showView'])->name('diretores.show');
Route::get('/diretores/{id}/editar', [DiretorController::class, 'editView'])->name('diretores.edit');
Route::put('/diretores/{id}', [DiretorController::class, 'update'])->name('diretores.update');
Route::delete('/diretores/{id}', [DiretorController::class, 'destroy'])->name('diretores.destroy');
