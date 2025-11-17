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
    Schema::create('filmes', function (Blueprint $table) {
      $table->id();
      $table->string('nome', 100);
      $table->string('sinopse', 100)->nullable();
      $table->integer('duracao')->nullable();
      $table->string('classificacao', 2)->default('L');
      $table->timestamps();
    });
  }

  /**
   * Reverse the migrations.
   */
  public function down(): void
  {
    Schema::dropIfExists('filmes');
  }
};
