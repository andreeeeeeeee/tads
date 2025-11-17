<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
  /**
   * Run the migrations.
   */
  public function up(): void
  {
    Schema::create('filmes_diretores', function (Blueprint $table) {
      $table->foreignId('filme_id')->constrained('filmes')->onDelete('cascade');
      $table->foreignId('diretor_id')->constrained('diretores')->onDelete('cascade');
      $table->primary(['filme_id', 'diretor_id']);
    });
  }

  /**
   * Reverse the migrations.
   */
  public function down(): void
  {
    Schema::dropIfExists('filmes_diretores');
  }
};
