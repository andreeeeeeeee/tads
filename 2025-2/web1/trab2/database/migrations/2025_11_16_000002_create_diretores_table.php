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
    Schema::create('diretores', function (Blueprint $table) {
      $table->id();
      $table->string('nome', 50);
      $table->double('nota_media')->nullable();
      $table->integer('idade')->nullable();
      $table->integer('premios')->nullable();
      $table->timestamps();
    });
  }

  /**
   * Reverse the migrations.
   */
  public function down(): void
  {
    Schema::dropIfExists('diretores');
  }
};
