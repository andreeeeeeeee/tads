// Todas as funções devem ser implementadas sem efeitos colaterais, isto é,
// elas não devem modificar o array de entrada (não são in-place).

function first(array, count = 0) {
  const a = [];

  for (let i = 0; i < count && i < array.length; i++) a.push(array[i]);

  return a;
}

function last(array, count) {
  const a = [];

  if (count)
    for (let i = array.length - count; i < array.length; i++) a.push(array[i]);
  else
    a.push(array[array.length - 1]);

  return a;
}

function tail(array) {
  const a = [];

  for (let i = 1; i < array.length; i++) a.push(array[i]);

  return a;
}

function without(array, del) {
  const a = [];

  for (let i = 0; i < array.length; i++)
    if (array[i] != del) a.push(array[i]);

  return a;
}

function union() {
  const a = [];

  for (let i = 0; i < arguments.length; i++)
    for (let j = 0; j < arguments[i].length; j++)
      a.push(arguments[i][j]);

  return a;
}

function unique(array) {
  const a = [];

  for (let i = 0; i < array.length; i++)
    if (!a.includes(array[i])) a.push(array[i])

  return a;
}

function intersection(array1, array2) {
  const a = [];

  for (let i = 0; i < array1.length; i++)
    if (array2.includes(array1[i]) && !a.includes(array1[i])) a.push(array1[i])

  return a;
}

function difference(array1, array2) {
  const a = [];

  for (let i = 0; i < array1.length; i++)
    if (!array2.includes(array1[i])) a.push(array1[i])

  for (let i = 0; i < array2.length; i++)
    if (!array1.includes(array2[i])) a.push(array2[i])


  return a;
}

function zip() {
  const a = [];

  let maxLength = arguments[0].length;

  for (let i = 1; i < arguments.length; i++)
    if (maxLength < arguments[i].length) maxLength = arguments[i].length

  for (let i = 0; i < maxLength; i++) {
    const b = [];

    for (let j = 0; j < arguments.length; j++) b.push(arguments[j][i])

    a[i] = b;
  }


  return a;
}

function compact(array) {
  const a = [];

  for (let i = 0; i < array.length; i++)
    if (array[i] || typeof array[i] == "boolean" || typeof array[i] == "string") a.push(array[i])

  return a;
}

function flatten(array, depth = 1) {
  const a = [];
  let count = 0;
  for (let i = 0; i < array.length; i++) {
    if (Array.isArray(array[i])) {
      if (count < depth) {
        const flat = flatten(array[i], depth - 1);
        count++;

        for (let j = 0; j < flat.length; j++) a.push(flat[j])
      } else a[a.length] = array[i]
    } else {
      a.push(array[i])
      count = 0
    }
  }

  return a;
}

function equals(array1, array2) {
  if (array1.length !== array2.length) return false;
  for (let i = 0; i < array1.length; i++) {
    if (Array.isArray(array1[i])) {
      if (!Array.isArray(array2[i]) || !equals(array1[i], array2[i])) return false;
    } else if (array1[i] !== array2[i]) return false;
  }

  return true;
}

const array1 = [12, 34, 56, 12, 67];
const array2 = ["js", "java", "c#", "python"];

console.log(first(array1)); // 12
console.log(first(array1, 3)); // [12, 34, 56]
console.log(first([], 3)); // []

console.log(last(array1)); // 67
console.log(last(array1, 3)); // [56, 12, 67]

console.log(tail(array1)); // [34, 56, 12, 67]
console.log(tail([])); // []

console.log(without(array1, 34)); // [12, 56, 12, 67]
console.log(without(array1, 12)); // [34, 56, 67]

console.log(union(array1, array2)); // [12, 34, 56, 67, 'js', 'java', 'c#', 'python']
console.log(union(array1, array2, [89, 34, "ruby", "js"])); // [12, 34, 56, 67, 'js', 'java', 'c#', 'python', 89, 'ruby']

console.log(unique(array1)); // [12, 34, 56, 67]
console.log(unique(["a", "a", "a"])); // []
console.log(unique(["a", "b", "a", "b"])); // ['a', 'b']

console.log(intersection(["a", 4, "c", 8], [8, "b", "c", 34])); // ['c', 8]
console.log(intersection([8, "a", 4, "c", 8], [8, "b", "c", 34])); // [8, 'c']

console.log(difference(["a", 4, "c", 8], [8, "b", "c", 34])); // ['a', 4, 'b', 34]
console.log(difference([], array1)); // []
console.log(difference(array1, [])); // [12, 34, 56, 12, 67]
console.log(difference(array1, array2)); // [12, 34, 56, 12, 67]
console.log(difference(array1, [56, 67])); // [12, 34, 12]

console.log(zip([12, 45], [67, 90])); // [[12, 67], [45, 90]]
console.log(zip(array2, [67, 90, 52, 56])); // [['js', 67], ['java', 90], ['c#', 52], ['python', 56]]
console.log(
  zip(
    array2,
    [67, 90, 52, 56],
    ["brendan eich", "james gosling", "anders hejlsberg", "guido van rossum"]
  )
);
// [['js', 67, 'brendan eich'], ['java', 90, 'james gosling'], ['c#', 52, 'anders hejlsberg'], ['python', 56, 'guido van rossum']]
console.log(zip([12, 45, 89], [67, 90])); // [[12, 67], [45, 90], [89, undefined]]
console.log(zip([12, 45])); // [[12], [45]]

console.log(compact([45, 23])); // [45, 23]
console.log(compact([45, 23, null])); // [45, 23]
console.log(compact([NaN, 23, null, 12])); // [23, 12]
console.log(compact([NaN, 23, null, 12, undefined, 78])); // [23, 12, 78]
console.log(compact([NaN, 23, null, 12, undefined, 78, 0, false, ""])); // [23, 12, 78, 0, false, '']
console.log(compact(array1)); // [12, 34, 56, 12, 67]

const depth = 2; // profundidade
const nested = [34, 54, [45, 23, [78, 90, [65]]], 12];
console.log(flatten([34, 54, [45, 23], 12])); // [34, 54, 45, 23, 12]
console.log(flatten([34, 54, [45, 23], 12, [78, 90]])); // [34, 54, 45, 23, 12, 78, 90]
console.log(flatten([34, 54, [45, 23, [78, 90]], 12])); // [34, 54, 45, 23, [78, 90], 12]
console.log(flatten([34, 54, [45, 23, [78, 90]], 12], depth)); // [34, 54, 45, 23, 78, 90, 12]
console.log(flatten(nested, depth)); // [34, 54, 45, 23, 78, 90, [65], 12]
console.log(flatten(nested, 3)); // [34, 54, 45, 23, 78, 90, 65, 12]
console.log(flatten(array1)); // [12, 34, 56, 12, 67]

console.log(equals([1, 2, 3], [1, 2, 3])); // true
console.log(equals([1, 2, 3], [1, 3, 2])); // false
console.log(equals(array1, array2)); // false
console.log(equals([1, [2, 3], 4], [1, [2, 3], 4])); // true
console.log(equals([1, [2, 3], 4], [1, [3, 2], 4])); // false
console.log(equals(nested, nested)); // true
console.log(equals(nested, [34, 54, [45, 23, [78, 90, [65]]], 12])); // true
console.log(equals([34, 54, [45, 23, [78, 90, [65]]], 12], nested)); // true

console.log(array1); // [12, 34, 56, 12, 67]
console.log(array2); // ['js', 'java', 'c#', 'python']
