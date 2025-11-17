# README - Microservicio de Gestión de Inventario

## Descripción
Este microservicio es responsable de gestionar el inventario de productos, incluyendo la consulta y actualización de existencias. Está diseñado con **Spring Boot** y sigue principios de programación reactiva con **Project Reactor**. Además, utiliza **RabbitMQ** para la comunicación asíncrona entre servicios.


# 📦 eCommerce API

API RESTful para la gestión de inventario y ventas en una aplicación de eCommerce.  
Ofrece endpoints para **productos** y **ventas**, y un diseño escalable con enfoque **Clean Architecture**.

---

## 🚀 Diseño General
- **Estilo:** REST con JSON.
- **Base de Datos:** Releacionales.

- **Endpoint:** `/inventory/{sku}`
- **Método:** `GET`
- **Descripción:** Recupera la información de un producto específico en el inventario.
- **Respuesta:**
  - **200 OK:** Devuelve un objeto `ProductDto` con los detalles del producto.
  - **400 Bad Request:** Si el SKU es inválido.
- **Ejemplo de respuesta exitosa:**
-
```json
{
  "sku": "123",
  "quantity": 10
}
```
---

## 🔑 Puntos finales principales


### Productos
- `GET /inventory/{id}` → Consulta el inventario de un producto por ID.

---

## ⚙️ Configuración



2. Configurar variables de entorno (`.env`):
   ```env
   spring.jackson.default-property-inclusion=non_null

    json.file.path=products.json
    maximum.retries=3
    retry.cycle=1

    spring.rabbitmq.host=localhost
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest
    queue.name=update-stock-queue
    exchange.name=update-stock-exchange
    spring.rabbitmq.listener.simple.auto-startup=true
    spring.rabbitmq.listener.simple.concurrency=5
    spring.rabbitmq.listener.simple.max-concurrency=10
   ```

3. Instalar dependencias y correr el servidor:
   ```bash
   ejecutar rabbit de forma local 
   ```

---

## 🏗️ Decisiones arquitectónicas clave
- **Base de datos relacionales** elegido por su flexibilidad en almacenar productos.
- **Clean Architecture**: separación en capas (`applications`, `domain`, `infraestructure`) para mantenibilidad y pruebas.
- **mensajería asíncrona** con RabbitMQ para desacoplar servicios y mejorar la escalabilidad.
- **Programación reactiva** con Spring WebFlux para manejar cargas concurrentes de manera eficiente.
- **Persistencia en memoria** con H2 para pruebas rápidas y desarrollo ágil.
- **Control de versiones** con `@Version` para manejar la concurrencia en actualizaciones de stock.
- **Pruebas unitarias** con JUnit 5 y Mockito para asegurar la calidad del código.
- **Documentación clara** de la API con Swagger/OpenAPI para facilitar el uso por parte de otros desarrolladores.
- **Manejo de errores** con excepciones personalizadas para una mejor experiencia de usuario y depuración.
- **Tolerancia a fallas** implementada con reintentos en la persistencia de datos.

---
