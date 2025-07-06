import { setupEditorEvents } from "./editor.js";
import { setupStorageEvents } from "./storage.js";

document.addEventListener("DOMContentLoaded", () => {
  setupEditorEvents();
  setupStorageEvents();
});