document.addEventListener('DOMContentLoaded', () => {
  const productos = document.querySelectorAll('.producto');

  productos.forEach(img => {
    img.addEventListener('click', () => {
      const id = img.dataset.id;
      const nombre = img.dataset.nombre;
      const precio = parseFloat(img.dataset.precio);
      const imagen = img.src; // añadimos la imagen

      let carrito = JSON.parse(localStorage.getItem('carrito')) || [];
      const existente = carrito.find(p => p.id === id);

      if (existente) {
        existente.cantidad += 1;
      } else {
        carrito.push({ id, nombre, precio, cantidad: 1, imagen }); // guardamos la imagen
      }

      localStorage.setItem('carrito', JSON.stringify(carrito));
      alert(`${nombre} se ha añadido al carrito 🛒`);
    });
  });
});
