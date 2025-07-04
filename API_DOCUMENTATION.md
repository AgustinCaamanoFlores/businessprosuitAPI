# BusinessProSuite API Documentation

Este documento resume los cambios recientes implementados y cómo ejecutar la API en macOS con procesador M4 Max.

## Nuevas características

- **Inventario**
  - Soporte de códigos de barras en `InventoryWarehouse` mediante el nuevo campo `invWhseBarcode`.
  - Endpoint `/api/inventory/products/{id}/forecast` para obtener la cantidad recomendada de reabastecimiento.
- **Finanzas**
  - Nuevas entidades de Cuentas por Cobrar (`AccountsReceivable`) y Cuentas por Pagar (`AccountsPayable`) junto a sus controladores REST.
- **Recursos Humanos**
  - Se añadió gestión de nómina con el nuevo endpoint `/api/hr/payrolls` para crear y consultar pagos a empleados.

## Ejecución en macOS (M4 Max)

1. Instalar [Java 17](https://adoptium.net/) y [Gradle 8](https://gradle.org/).
2. Configurar MySQL 8 y las variables de entorno descritas en `README.md`.
3. Ejecutar:

   ```bash
   ./gradlew bootRun
   ```

4. Acceder a `http://localhost:8080/swagger-ui.html` para explorar los endpoints.

La API ha sido probada con éxito en macOS con procesador M4 Max utilizando Java 17 y Gradle 8.
