
# Prompt de Contexto para Copilot

## Objetivo del proyecto
Diseñar y prototipar una mejora para un sistema de gestión de inventario existente que opera en un entorno distribuido. El objetivo es:  
- Optimizar la **consistencia del inventario**.  
- Reducir la **latencia de actualización** y los **costos operativos**.  
- Garantizar **seguridad** y **observabilidad**.  

---

## Contexto del sistema actual
- Empresa minorista con varias tiendas.  
- Cada tienda tiene una **base de datos local** que se sincroniza con la base de datos central cada **15 minutos**.  
- Los clientes consultan el stock en línea, pero el sistema presenta:  
  - **Retrasos** en la actualización del inventario.  
  - **Inconsistencias** que provocan pérdida de ventas.  
- Backend actual: **monolítico** e integrado con una **aplicación web heredada**.  

---

## Requerimientos de diseño técnico
1.  **Tecnología del Backend**:
    -   **Lenguaje**: Java 19
    -   **Framework**: Spring Boot 3.5.4
    -   **Compilación**: Gradle
    -   **Arquitectura**: Enfoque multi-módulo DDD/Hexagonal.
    -   **Manejo de peticiones**: Spring WebFlux (reactivo).
    -   **Persistencia**: Base de datos en memoria H2 con R2DBC.
    -   **Mensajería**: RabbitMQ para la publicación de eventos.
    -  **Control de versiones**: Uso de `@Version` para manejar la concurrencia en las actualizaciones de stock.
    -  **Pruebas**: JUnit 5 y Mockito para pruebas unitarias.

2. Diseñar la **API de inventario**, incluyendo:  
   - Operaciones clave: consulta de stock, actualización de stock, sincronización.  
   - Justificación de las decisiones de diseño de API y de arquitectura.  

3. **Backend prototipo**:  
   - Implementar un **prototipo simplificado** de los servicios backend.  
   - Persistencia simulada con **JSON/CSV locales** o **base de datos en memoria** (SQLite, H2, etc.).  
   - Manejo de **concurrencia en actualizaciones de stock**.  
   - Implementar **tolerancia a fallas** básica.  
   - Elegir y justificar entre **consistencia vs disponibilidad**.  

4. **Requisitos no funcionales**:  
   - Buenas prácticas de manejo de errores.  
   - Documentación clara.  
   - Pruebas básicas unitarias/integración.  

---

## Uso de herramientas
- Se permite y recomienda el uso de **GenAI (Copilot, ChatGPT, etc.)** e IDE con agentes de asistencia.  
- El sistema debe aprovechar estas herramientas para:  
  - Generar ideas de arquitectura.  
  - Sugerir código repetitivo.  
  - Mejorar eficiencia en el desarrollo.  

---

## Documentación esperada
- **README** breve que incluya:  
  - Diseño de la API.  
  - Endpoints principales.  
  - Instrucciones de configuración.  
  - Decisiones arquitectónicas clave.  
- (Opcional) Un **diagrama** de arquitectura o flujo de sincronización.  

---

## Estrategia técnica
- Definir la **pila de tecnología** del backend.  
- Explicar cómo la arquitectura propuesta mejora **consistencia, latencia y costos**.  
- Integrar pruebas y observabilidad.  
- Usar principios modernos (ejemplo: **microservicios + CQRS + event-driven con RabbitMQ/Kafka** si aplica).  

---
