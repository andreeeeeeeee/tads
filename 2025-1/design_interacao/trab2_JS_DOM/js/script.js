function generateGalleryHtml() {
  // Seleciona os itens da galeria e os valores de estilo configurados pelo usuário
  const galleryItems = document.querySelectorAll('#gallery-items-container .gallery-item');
  const galleryBg = document.getElementById('gallery-bg').value;
  const cardBg = document.getElementById('card-bg').value;
  const cardBorder = document.getElementById('card-border').value || '1px solid #000';
  const cardSpacing = document.getElementById('card-spacing').value || '10px';
  const cardWidth = document.getElementById('card-width').value || '200px';
  const cardTextColor = document.getElementById('card-text-color').value;

  // Mapeia os itens da galeria para gerar o HTML de cada card
  const cardsHtml = Array.from(galleryItems).map((item, i) => {
    const imgElement = item.querySelector('.card-preview-image');
    const imgSrc = imgElement && imgElement.src ? imgElement.src : '';
    const title = item.querySelector('.card-title-input').value || `Título ${i + 1}`;
    const description = item.querySelector('.card-description-input').value || `Descrição ${i + 1}`;

    // Retorna o HTML de cada card com os estilos configurados
    return `
      <div class="card" style="
        background-color: ${cardBg};
        border: ${cardBorder};
        margin: ${cardSpacing};
        width: ${cardWidth};
        color: ${cardTextColor};
      ">
        ${imgSrc ? `<img src="${imgSrc}" alt="Imagem ${i + 1}" style="width: 100%;">` : ''}
        <h5>${title}</h5>
        <p>${description}</p>
      </div>
    `;
  }).join('');

  // Retorna o HTML completo da galeria
  return `
    <div class="gallery" style="display: flex; flex-wrap: wrap; gap: ${cardSpacing}; background-color: ${galleryBg};">
      ${cardsHtml}
    </div>
  `;
}

function generateFormHtml() {
  const formTitle = document.getElementById('form-title').value;
  const formBg = document.getElementById('form-bg').value;
  const formBorder = document.getElementById('form-border').value || '0px';

  // Mapeia os itens do formulário para gerar os campos
  const formItems = Array.from(document.querySelectorAll('#form-items-container .form-item')).map((item, i) => {
    const label = item.querySelector('.form-label-input').value || `Campo ${i + 1}`;
    const type = item.querySelector('.form-type-select').value;

    // Retorna o HTML de cada campo, considerando o tipo (input ou select)
    return type === 'select'
      ? `<label>${label}: <select><option>Opção 1</option><option>Opção 2</option></select></label>`
      : `<label>${label}: <input type="${type}"></label>`;
  }).join('');

  // Retorna o HTML completo do formulário
  return `
    <form style="background-color: ${formBg}; border: ${formBorder};">
      ${formTitle && `<h2>${formTitle}</h2>`}
      ${formItems}
    </form>
  `;
}

function generateFooterHtml() {
  const footerText = document.getElementById('footer-text').value;
  const footerBg = document.getElementById('footer-bg').value;
  const footerTextColor = document.getElementById('footer-text-color').value;
  const footerFontSize = document.getElementById('footer-font-size').value || '12px';
  const footerTextAlign = document.getElementById('footer-text-align').value;

  // Retorna o HTML do rodapé com os estilos configurados
  const footerHtml = `
    <footer style="
      background-color: ${footerBg};
      color: ${footerTextColor};
      font-size: ${footerFontSize};
      text-align: ${footerTextAlign};
      padding: 10px;
    ">
      <p>${footerText}</p>
    </footer>
  `;
  return footerHtml;
}

function generatePage(headerHtml) {
  // Configurações do menu
  const menuItems = document.getElementById('menu-items').value.split(',');
  const menuBg = document.getElementById('menu-bg').value;
  const menuTextColor = document.getElementById('menu-text-color').value;
  const menuBorder = document.getElementById('menu-border').value || '0px';
  const menuSpacing = document.getElementById('menu-spacing').value || '0px';
  const menuImageInput = document.getElementById('menu-image');
  let menuImageSrc = '';

  // Verifica se há uma imagem para o menu
  if (menuImageInput.files && menuImageInput.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      menuImageSrc = e.target.result;

      // Gera o HTML do menu com a imagem
      const menuHtml = `
        <nav style="
          background-color: ${menuBg};
          color: ${menuTextColor};
          border: ${menuBorder};
          padding: ${menuSpacing};
        ">
          <img src="${menuImageSrc}" alt="Imagem do Menu">
          <ul>
            ${menuItems.map(item => `<li style="color: ${menuTextColor};">${item.trim()}</li>`).join('')}
          </ul>
        </nav>
      `;

      generateFullPage(headerHtml, menuHtml);
    };
    reader.readAsDataURL(menuImageInput.files[0]);
  } else {
    // Gera o HTML do menu sem imagem
    const menuHtml = `
      <nav style="
        background-color: ${menuBg};
        color: ${menuTextColor};
        border: ${menuBorder};
        padding: ${menuSpacing};
      ">
        <ul>
          ${menuItems.map(item => `<li style="color: ${menuTextColor};">${item.trim()}</li>`).join('')}
        </ul>
      </nav>
    `;

    generateFullPage(headerHtml, menuHtml);
  }
}

function generateFullPage(headerHtml, menuHtml) {
  const galleryHtml = generateGalleryHtml();
  const formHtml = generateFormHtml();
  const footerHtml = generateFooterHtml();

  // Monta o HTML final da página
  const html = `
    <!DOCTYPE html>
    <html lang="pt-br">
    <head>
      <meta charset="UTF-8">
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <title>Título da Página</title>
    </head>
    <body>
      ${headerHtml}
      ${menuHtml}
      ${galleryHtml}
      ${formHtml}
      ${footerHtml}
    </body>
    </html>
  `;

  // Exibe o HTML gerado no preview e no campo de código gerado
  document.getElementById('preview').innerHTML = html;
  document.getElementById('generated-code').textContent = html;
}

// Evento para gerar o cabeçalho e iniciar a geração da página
document.getElementById('generate-html').addEventListener('click', () => {
  const headerText = document.getElementById('header-text').value;
  const headerBg = document.getElementById('header-bg').value;
  const headerTextColor = document.getElementById('header-text-color').value;
  const headerBorder = document.getElementById('header-border').value || '0px';
  const headerSpacing = document.getElementById('header-spacing').value || 0;
  const headerImageInput = document.getElementById('header-image');
  let headerImageSrc = '';

  // Verifica se há uma imagem para o cabeçalho
  if (headerImageInput.files && headerImageInput.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      headerImageSrc = e.target.result;

      // Gera o HTML do cabeçalho com a imagem
      const headerHtml = `
        <header style="
          background-color: ${headerBg};
          color: ${headerTextColor};
          border: ${headerBorder};
          padding: ${headerSpacing};
          display: flex;
          align-items: center;
          justify-content: space-between;
        ">
          ${headerText && `<div><h1>${headerText}</h1></div>`}
          <div>
            <img src="${headerImageSrc}" alt="Imagem do Cabeçalho" style="max-height: 100px;">
          </div>
        </header>
      `;

      generatePage(headerHtml);
    };
    reader.readAsDataURL(headerImageInput.files[0]);
  } else {
    // Gera o HTML do cabeçalho sem imagem
    const headerHtml = `
      <header style="
        background-color: ${headerBg};
        color: ${headerTextColor};
        border: ${headerBorder};
        padding: ${headerSpacing};
        display: flex;
        align-items: center;
        justify-content: space-between;
      ">
        ${headerText && `<div><h1>${headerText}</h1></div>`}
      </header>
    `;

    generatePage(headerHtml);
  }
});

document.getElementById('add-gallery-item').addEventListener('click', () => {
  const container = document.getElementById('gallery-items-container');
  const index = container.children.length + 1;

  // HTML do novo item da galeria
  const cardHtml = `
    <div class="gallery-item mb-3">
      <label>Imagem do Card ${index}:</label>
      <input type="file" class="form-control card-image-input" accept="image/*">
      <img class="card-preview-image mt-2" style="max-width: 100%; display: none;">
      <label>Título do Card ${index}:</label>
      <input type="text" class="form-control card-title-input" placeholder="Título">
      <label>Descrição do Card ${index}:</label>
      <textarea class="form-control card-description-input" placeholder="Descrição"></textarea>
      <button type="button" class="btn btn-danger btn-sm remove-gallery-item mt-2">Remover</button>
    </div>
  `;
  container.insertAdjacentHTML('beforeend', cardHtml);

  // Adiciona evento para pré-visualizar a imagem carregada
  const newCard = container.querySelector('.gallery-item:last-child');
  const fileInput = newCard.querySelector('.card-image-input');
  const previewImage = newCard.querySelector('.card-preview-image');

  fileInput.addEventListener('change', (e) => {
    const file = e.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (event) => {
        previewImage.src = event.target.result;
        previewImage.style.display = 'block';
      };
      reader.readAsDataURL(file);
    } else {
      previewImage.style.display = 'none';
      previewImage.src = '';
    }
  });

  // Adiciona evento para remover o item da galeria
  newCard.querySelector('.remove-gallery-item').addEventListener('click', (e) => {
    e.target.closest('.gallery-item').remove(); // Remove o item correspondente
  });
});

document.getElementById('add-form-item').addEventListener('click', () => {
  const container = document.getElementById('form-items-container');

  const formItem = document.createElement('div');
  formItem.classList.add('form-item', 'mb-3', 'border', 'p-2', 'rounded');

  const labelInput = document.createElement('input');
  labelInput.type = 'text';
  labelInput.placeholder = 'Texto do Label';
  labelInput.classList.add('form-label-input', 'form-control', 'mb-2');

  const typeSelect = document.createElement('select');
  typeSelect.classList.add('form-type-select', 'form-control', 'mb-2');
  ['text', 'number', 'password', 'tel', 'email', 'select', 'date'].forEach(type => {
    const option = document.createElement('option');
    option.value = type;
    option.textContent = type;
    typeSelect.appendChild(option);
  });

  const deleteButton = document.createElement('button');
  deleteButton.type = 'button';
  deleteButton.textContent = 'Excluir';
  deleteButton.classList.add('btn', 'btn-danger', 'btn-sm');

  deleteButton.addEventListener('click', () => {
    container.removeChild(formItem);
  });

  // Adiciona os elementos criados ao item do formulário
  formItem.appendChild(labelInput);
  formItem.appendChild(typeSelect);
  formItem.appendChild(deleteButton);

  container.appendChild(formItem);
});

document.getElementById('save-code').addEventListener('click', () => {
  const code = document.getElementById('generated-code').textContent;
  localStorage.setItem('htmlCode', code);
  alert('Código salvo no LocalStorage!');
});

document.getElementById('load-code').addEventListener('click', () => {
  const code = localStorage.getItem('htmlCode');
  if (code) {
    document.getElementById('generated-code').textContent = code;
    document.getElementById('preview').innerHTML = code;
  } else {
    alert('Nenhum código encontrado no LocalStorage.');
  }
});

document.getElementById('clear-storage').addEventListener('click', () => {
  localStorage.clear();
  alert('LocalStorage limpo!');
});