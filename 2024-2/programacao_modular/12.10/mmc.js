function mmc(n1, n2) {
  const maior = n2 > n1 ? n2 : n1;
  const menor = n2 < n1 ? n2 : n1;

  let multiplo = maior;
  while (!(multiplo % menor == 0) && (multiplo += maior));
  return multiplo;
}

const res = mmc(4, 7);
console.log(res); // 28

// Casos de Teste (Test Case)
console.log(mmc(7, 4)); // 28
console.log(mmc(4, 12)); // 12
console.log(mmc(42, 87)); // 1218
// https://www.wolframalpha.com/input?i=Least+common+multiple+42%2C+87
