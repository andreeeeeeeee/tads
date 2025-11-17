<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Filme extends Model
{
    use HasFactory;

    protected $table = 'filmes';

    protected $fillable = [
        'nome',
        'sinopse',
        'duracao',
        'classificacao',
    ];

    protected $casts = [
        'duracao' => 'integer',
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
    ];

    public static $rules = [
        'nome' => 'required|string|max:100',
        'sinopse' => 'nullable|string|max:100',
        'duracao' => 'nullable|integer|min:1',
        'classificacao' => 'nullable|string|max:2',
    ];

    /**
     * Relacionamento muitos-para-muitos com Diretores
     */
    public function diretores(): BelongsToMany
    {
        return $this->belongsToMany(Diretor::class, 'filmes_diretores', 'filme_id', 'diretor_id');
    }
}
