<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Relations\BelongsToMany;

class Diretor extends Model
{
    use HasFactory;

    protected $table = 'diretores';

    protected $fillable = [
        'nome',
        'nota_media',
        'idade',
        'premios',
    ];

    protected $casts = [
        'nota_media' => 'double',
        'idade' => 'integer',
        'premios' => 'integer',
        'created_at' => 'datetime',
        'updated_at' => 'datetime',
    ];

    public static $rules = [
        'nome' => 'required|string|max:50',
        'nota_media' => 'nullable|numeric|min:0|max:10',
        'idade' => 'nullable|integer|min:1|max:150',
        'premios' => 'nullable|integer|min:0',
    ];

    /**
     * Relacionamento muitos-para-muitos com Filmes
     */
    public function filmes(): BelongsToMany
    {
        return $this->belongsToMany(Filme::class, 'filmes_diretores', 'diretor_id', 'filme_id');
    }
}
