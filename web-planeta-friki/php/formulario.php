<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
  $nombre = htmlspecialchars($_POST["nombre"]);
  $telefono = htmlspecialchars($_POST["telefono"]);
  $correo = htmlspecialchars($_POST["correo"]);
  $mensaje = htmlspecialchars($_POST["mensaje"]);

  $contenido = "----------------------------------\n";
  $contenido .= "Fecha: " . date("Y-m-d H:i:s") . "\n";
  $contenido .= "Nombre: $nombre\n";
  $contenido .= "Teléfono: $telefono\n";
  $contenido .= "Correo: $correo\n";
  $contenido .= "Mensaje:\n$mensaje\n\n";

  $ruta = "../mensajes.txt";
  file_put_contents($ruta, $contenido, FILE_APPEND | LOCK_EX);
}
?>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Mensaje guardado</title>
  <link rel="stylesheet" href="../css/styles.css">
  <!-- Bootstrap CDN -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
  <main class="container py-5">
    <h2 class="titulo text-center mb-4">Mensaje recibido</h2>

    <div class="d-flex justify-content-center">
      <div class="text-center" style="max-width: 600px; border: 2px solid #FDC6B5; border-radius: 10px; padding: 20px;">
        <p>Gracias <strong><?= $nombre ?></strong>, tu mensaje ha sido guardado correctamente.</p>
        <div class="mt-4">
          <a href="../contacto.html" class="btn-dorado">Volver al formulario</a>
        </div>
      </div>
    </div>
  </main>
</body>
</html>
