document.addEventListener('DOMContentLoaded', () => {
  let carrito = JSON.parse(localStorage.getItem('carrito')) || [];

  const contenedor = document.getElementById('carrito-contenido');
  const totalContenedor = document.getElementById('carrito-total');

  function renderCarrito() {
    contenedor.innerHTML = '';
    let total = 0;

    if (carrito.length === 0) {
      contenedor.innerHTML = '<p class="text-center">Tu carrito está vacío.</p>';
      totalContenedor.innerHTML = '';
      return;
    }

    carrito.forEach(p => {
      const card = document.createElement('div');
      card.className = 'col-md-12 mb-3';
      card.innerHTML = `
          <div class="card shadow-lg">
            <div class="row g-0 align-items-center">
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title text-nombre-producto">${p.nombre}</h5>
                  <p class="card-text">Cantidad: ${p.cantidad}</p>
                  <p class="card-text fw-bold">Total: ${(p.precio * p.cantidad).toFixed(2)} €</p>
                  <button class="btn-rojo" onclick="eliminarProducto('${p.id}')">Eliminar</button>
                </div>
              </div>
              <div class="col-md-4 text-center p-2">
                <a href="productos.html#${p.id}">
                  <img src="${p.imagen || 'assets/no-imagen.jpg'}" alt="${p.nombre}" class="img-fluid rounded" style="max-height: 120px; cursor: pointer;">
                </a>
              </div>
            </div>
          </div>
        `;
      contenedor.appendChild(card);
      total += p.precio * p.cantidad;
    });

    totalContenedor.innerHTML = `
      <div class="d-flex flex-column align-items-end pe-3">
        <h4 class="mb-3">Total: ${total.toFixed(2)} €</h4>
        <div class="d-flex gap-2">
          <button class="btn-dorado">Finalizar compra</button>
          <button class="btn-rojo" onclick="vaciarCarrito()">Vaciar carrito</button>
        </div>
      </div>
    `;

  }

  // Eliminar de uno en uno
  window.eliminarProducto = function (id) {
    const index = carrito.findIndex(p => p.id === id);
    if (index !== -1) {
      if (carrito[index].cantidad > 1) {
        carrito[index].cantidad -= 1;
      } else {
        carrito.splice(index, 1);
      }
      localStorage.setItem('carrito', JSON.stringify(carrito));
      renderCarrito();
    }
  };
  // Vaciar carrito
  window.vaciarCarrito = function () {
    if (confirm("¿Estás seguro de que quieres vaciar el carrito?")) {
      carrito = [];
      localStorage.setItem('carrito', JSON.stringify(carrito));
      renderCarrito();
    }
  };


  renderCarrito();
});
