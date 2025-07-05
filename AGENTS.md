# Guía para agentes de Codex

Estas instrucciones aplican a todo el repositorio.

- Utiliza indentación de 4 espacios en los archivos Java y de 2 espacios en los archivos XML.
- Al agregar nuevas entidades JPA, declara sus nombres completos en `src/main/resources/META-INF/persistence.xml`. Colócalas justo debajo de la etiqueta `<provider>` y antes de `<exclude-unlisted-classes>`, respetando la sangría de 4 espacios.
- Ejecuta `./gradlew test` tras cualquier modificación de código o configuración para asegurar que las pruebas sigan pasando.
- Si tus cambios añaden funcionalidades visibles, actualiza `CHANGELOG.md` y `README.md` cuando corresponda.
- Evita introducir dependencias no presentes en `build.gradle` sin revisar primero.
