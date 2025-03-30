const elementType = document.getElementById('elementType');
const elementContent = document.getElementById('elementContent');
const elementImage = document.getElementById('elementImage');
const bgColor = document.getElementById('bgColor');
const textColor = document.getElementById('textColor');
const borderStyle = document.getElementById('borderStyle');
const elementSize = document.getElementById('elementSize');
const addElementButton = document.getElementById('addElementButton');
const headerPreview = document.getElementById('headerPreview');
const headerBgColor = document.getElementById('headerBgColor');

let editingElement = null;

headerBgColor.addEventListener('input', () => {
  headerPreview.style.backgroundColor = headerBgColor.value;
});

elementType.addEventListener('change', () => {
  if (elementType.value === 'image') {
    textColor.parentElement.classList.add('d-none');
    elementContent.classList.add('d-none');
    elementImage.classList.remove('d-none');
  } else {
    textColor.parentElement.classList.remove('d-none');
    elementContent.classList.remove('d-none');
    elementImage.classList.add('d-none');
  }
});

addElementButton.addEventListener('click', () => {
  if (!editingElement && headerPreview.children.length >= 3) {
    alert('Você só pode adicionar até 3 elementos.');
    return;
  }

  if (elementType.value === 'text' && !elementContent.value) {
    alert('Você deve preencher o conteúdo do elemento.');
    return;
  }

  const element = editingElement || document.createElement('div');
  element.classList.add('element-preview');
  element.style.backgroundColor = bgColor.value || 'transparent';
  element.style.border = `2px ${borderStyle.value} black`;
  element.style.width = `${elementSize.value || 200}px`;
  element.style.height = `${elementSize.value || 200}px`;
  element.style.display = 'flex';
  element.style.alignItems = 'center';
  element.style.justifyContent = 'center';
  element.style.color = textColor.value;

  if (elementType.value === 'text') {
    element.innerHTML = '';
    const text = document.createElement('p');
    text.textContent = elementContent.value;
    element.appendChild(text);
  } else if (elementType.value === 'image') {
    element.removeChild(element.querySelector('p'));
    const reader = new FileReader();
    reader.onload = function (e) {
      const img = document.createElement('img');
      img.src = e.target.result;
      img.style.maxWidth = '100%';
      img.style.maxHeight = '100%';
      element.appendChild(img);
    };
    reader.readAsDataURL(elementImage.files[0]);
  }

  if (!editingElement) {
    headerPreview.appendChild(element);
    element.addEventListener('click', () => editElement(element));
  }

  editingElement = null;
  resetForm();
});

function rgbToHex(rgb) {
  const [r, g, b] = rgb.match(/\d+/g);
  const formattedR = Number(r).toString(16).padStart(2, '0');
  const formattedG = Number(g).toString(16).padStart(2, '0');
  const formattedB = Number(b).toString(16).padStart(2, '0');
  return `#${formattedR}${formattedG}${formattedB}`;
}

function editElement(element) {
  editingElement = element;

  bgColor.value = element.style.backgroundColor ? rgbToHex(element.style.backgroundColor) : '#ffffff';
  borderStyle.value = element.style.border.split(' ')[1] || 'none';
  elementSize.value = parseInt(element.style.width) || 200;
  textColor.value = element.style.color ? rgbToHex(element.style.color) : '#000000';

  if (element.querySelector('p')) {
    elementType.value = 'text';
    elementContent.value = element.querySelector('p').textContent;
    textColor.parentElement.classList.remove('d-none');
    elementContent.classList.remove('d-none');
    elementImage.classList.add('d-none');
  } else if (element.querySelector('img')) {
    console.log(element.querySelector('img').src);
    elementType.value = 'image';
    elementImage.value = element.querySelector('img').src;
    textColor.parentElement.classList.add('d-none');
    elementContent.classList.add('d-none');
    elementImage.classList.remove('d-none');
  }
}

function resetForm() {
  elementType.value = 'text';
  elementContent.value = '';
  elementImage.value = '';
  bgColor.value = '#ffffff';
  textColor.value = '#000000';
  borderStyle.value = 'none';
  elementSize.value = '';
  textColor.parentElement.classList.remove('d-none');
  elementContent.classList.remove('d-none');
  elementImage.classList.add('d-none');
}