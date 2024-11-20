function substring(string, start, end = string.length) {
  let s = "";
  if (start < 0) {
    start = 0;
  }

  for (let i = start; i < string.length && i < end; i++) {
    s += string[i];
  }

  return s;
}

function split(string, separator) {
  let a = [];
  let start = 0;

  for (let i = 0; i < string.length; i++) {
    if (string[i] == separator) {
      let substr = substring(string, start, i);
      a.push(substr);
      start = i + 1;
    }

    if (i == string.length - 1 && string[i] != separator) {
      let substr = substring(string, start);
      a.push(substr);
    }
  }
  return a;
}

function join(array, separator) {
  let s = "";

  for (let i = 0; i < array.length; i++) {
    if (i == array.length - 1) {
      s += array[i];
    } else {
      s += array[i] + separator;
    }
  }

  return s;
}

function camelCase(string) {
  const a = split(string, " ");

  for (let i = 1; i < a.length; i++)
    a[i] = a[i][0].toUpperCase() + substring(a[i], 1);

  const s = join(a, "");
  return s;
}

function PascalCase(string) {
  const a = split(string, " ");

  for (let i = 0; i < a.length; i++)
    a[i] = a[i][0].toUpperCase() + substring(a[i], 1);

  const s = join(a, "");
  return s;
}

function snake_case(string) {
  const a = split(string, " ");

  for (let i = 0; i < a.length; i++) {
    a[i] = a[i].toLowerCase();
  }

  const s = join(a, "_");
  return s;
}

function kebabcase(string) {
  const a = split(string, " ");

  for (let i = 0; i < a.length; i++) {
    a[i] = a[i].toLowerCase();
  }

  const s = join(a, "-");
  return s;
}

const cargo = "desenvolvedora";
console.log(substring(cargo, 5, 10));
console.log(substring(cargo, 5, 25));

const disciplina = "progamação modular tads ";
console.log(split(disciplina, " "));

const nomeSobrenome = ["Gabriel", "Jacinto"];
console.log(join(nomeSobrenome, " "));

const salsichaocase = "salsichao case";
console.log(camelCase(salsichaocase));
console.log(PascalCase(salsichaocase));
console.log(snake_case(salsichaocase));
console.log(kebabcase(salsichaocase));