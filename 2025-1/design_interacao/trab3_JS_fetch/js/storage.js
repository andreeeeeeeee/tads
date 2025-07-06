export function saveToLocalStorage() {
  const generatedCode = document.getElementById("generated-code").textContent;
  localStorage.setItem("generatedHTML", generatedCode);
}

export function loadFromLocalStorage() {
  const savedCode = localStorage.getItem("generatedHTML");
  if (savedCode) {
    document.getElementById("preview").innerHTML = savedCode;
    document.getElementById("generated-code").textContent = savedCode;
  }
}

export function clearLocalStorage() {
  localStorage.removeItem("generatedHTML");
  document.getElementById("preview").innerHTML = "";
  document.getElementById("generated-code").textContent = "";
}

export function setupStorageEvents() {
  document.getElementById("save-code").addEventListener("click", saveToLocalStorage);
  document.getElementById("load-code").addEventListener("click", loadFromLocalStorage);
  document.getElementById("clear-storage").addEventListener("click", clearLocalStorage);
}