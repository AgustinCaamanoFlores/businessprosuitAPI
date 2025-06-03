# Pool de Cambios Específicos - BusinessProSuite API V0.3

**Versión Objetivo**: V0.3  
**Fecha de Planificación**: 3 de Junio de 2025  
**Prioridad**: Alta a Crítica  

## 🎯 Resumen

Este documento contiene **cambios específicos y accionables** para la próxima iteración de desarrollo. Cada elemento incluye pasos de implementación detallados, requisitos técnicos y criterios de aceptación.

---

## 🔥 **PRIORIDAD CRÍTICA** - Semana 1

### **TAREA 1: Implementación del Módulo de Industria de Construcción**
**Prioridad**: Crítica  
**Esfuerzo**: 5-7 días  
**Dependencias**: Arquitectura base (completada)

#### **Implementación Técnica:**
1. **Estructura de Entidades**:
   ```java
   @Entity
   public class ProyectoConstruction {
       private Long id;
       private String nombreProyecto;
       private String tipoProyecto; // RESIDENCIAL, COMERCIAL, INDUSTRIAL
       private BigDecimal presupuestoEstimado;
       private LocalDate fechaInicio;
       private LocalDate fechaFinEsperada;
       private String ubicacion;
       private String estado; // PLANIFICACION, EN_PROGRESO, COMPLETADO, EN_PAUSA
       // ... campos de auditoría
   }
   
   @Entity 
   public class TrabajadorConstruction {
       private Long id;
       private String nombre;
       private String especialidad; // ELECTRICISTA, PLOMERO, CARPINTERO, etc.
       private String nivelCertificacion;
       private BigDecimal tarifaPorHora;
       private Boolean estaActivo;
   }
   
   @Entity
   public class UsoMaterial {
       private Long id;
       private Long proyectoId;
       private String nombreMaterial;
       private Integer cantidadUsada;
       private String unidad; // TONELADAS, METROS_CUBICOS, PIEZAS
       private BigDecimal costoUnitario;
       private LocalDate fechaUso;
   }
   ```

2. **Capa de Repositorio**:
   ```java
   public interface ProyectoConstructionRepository extends JpaRepository<ProyectoConstruction, Long> {
       List<ProyectoConstruction> findByEstado(String estado);
       List<ProyectoConstruction> findByTipoProyectoAndEstado(String tipo, String estado);
       
       @Query("SELECT p FROM ProyectoConstruction p WHERE p.fechaInicio BETWEEN :inicio AND :fin")
       List<ProyectoConstruction> findProyectosPorRangoFecha(@Param("inicio") LocalDate inicio, 
                                                           @Param("fin") LocalDate fin);
   }
   ```

3. **Capa de Servicio**:
   ```java
   @Service
   public class ProyectoConstructionService {
       public ProyectoConstructionDTO crearProyecto(CrearProyectoRequest request);
       public ProyectoConstructionDTO actualizarProyecto(Long id, ActualizarProyectoRequest request);
       public List<ProyectoConstructionDTO> obtenerProyectosPorEstado(String estado);
       public ProgresoProyectoDTO calcularProgresoProyecto(Long proyectoId);
       public AnalisisPresupuestoDTO analizarUtilizacionPresupuesto(Long proyectoId);
   }
   ```

4. **Endpoints del Controlador**:
   ```java
   @RestController
   @RequestMapping("/api/v1/construction")
   public class ConstructionController {
       
       @PostMapping("/proyectos")
       public ResponseEntity<ProyectoConstructionDTO> crearProyecto(@Valid @RequestBody CrearProyectoRequest request);
       
       @GetMapping("/proyectos")
       public ResponseEntity<List<ProyectoConstructionDTO>> obtenerTodosProyectos();
       
       @GetMapping("/proyectos/{id}/progreso")
       public ResponseEntity<ProgresoProyectoDTO> obtenerProgresoProyecto(@PathVariable Long id);
       
       @PostMapping("/proyectos/{id}/materiales")
       public ResponseEntity<UsoMaterialDTO> agregarUsoMaterial(@PathVariable Long id, 
                                                               @Valid @RequestBody UsoMaterialRequest request);
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Todas las entidades creadas con relaciones apropiadas
- [ ] Operaciones CRUD para proyectos, trabajadores, materiales
- [ ] Algoritmo de cálculo de progreso implementado
- [ ] Funcionalidad de seguimiento de presupuesto
- [ ] Documentación Swagger completa
- [ ] Cobertura de tests unitarios >80%

---

### **TAREA 2: Características Avanzadas del Módulo de Finanzas**
**Prioridad**: Alta  
**Esfuerzo**: 4-5 días  
**Dependencias**: Estructura básica de finanzas (parcialmente completa)

#### **Implementación Técnica:**
1. **Sistema de Asiento Contable Mejorado**:
   ```java
   @Entity
   public class AsientoContableAvanzado {
       private Long id;
       private String tipoAsiento; // DEBITO, CREDITO, AJUSTE
       private BigDecimal monto;
       private String codigoCuenta;
       private String descripcion;
       private LocalDateTime fechaTransaccion;
       private String numeroReferencia;
       private Long empresaId;
       private String estadoAprobacion; // PENDIENTE, APROBADO, RECHAZADO
       private Long aprobadoPor;
   }
   ```

2. **Reportes Financieros**:
   ```java
   @Service
   public class ServicioReportesFinancieros {
       public BalanceGeneralDTO generarBalanceGeneral(Long empresaId, LocalDate fechaCorte);
       public EstadoResultadosDTO generarEstadoResultados(Long empresaId, LocalDate fechaInicio, LocalDate fechaFin);
       public EstadoFlujoEfectivoDTO generarFlujoEfectivo(Long empresaId, LocalDate fechaInicio, LocalDate fechaFin);
   }
   ```

3. **Gestión de Presupuestos**:
   ```java
   @Entity
   public class Presupuesto {
       private Long id;
       private Long empresaId;
       private String periodoPresupuesto; // MENSUAL, TRIMESTRAL, ANUAL
       private LocalDate fechaInicio;
       private LocalDate fechaFin;
       private Map<String, BigDecimal> presupuestosCategorias; // Categoría -> Monto
       private String estado; // BORRADOR, ACTIVO, EXPIRADO
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Contabilidad de doble entrada implementada
- [ ] Generación de reportes financieros (Balance General, PyG, Flujo de Efectivo)
- [ ] Comparación presupuesto vs real
- [ ] Implementación básica de soporte multi-moneda
- [ ] Rastro de auditoría para todas las transacciones financieras

---

## ⚡ **PRIORIDAD ALTA** - Semana 2

### **TAREA 3: Seguridad y Autenticación Mejorada**
**Prioridad**: Alta  
**Esfuerzo**: 3-4 días

#### **Implementación Técnica:**
1. **Autenticación Multi-Factor (MFA)**:
   ```java
   @Entity
   public class TokenMfa {
       private Long id;
       private Long usuarioId;
       private String valorToken;
       private String tipoToken; // SMS, EMAIL, TOTP
       private LocalDateTime expiraEn;
       private Boolean fueUsado;
   }
   
   @Service
   public class ServicioMfa {
       public String generarTokenMfa(Long usuarioId, TipoMfa tipo);
       public boolean validarTokenMfa(Long usuarioId, String token);
       public void enviarTokenMfa(Long usuarioId, String token, TipoMfa tipo);
   }
   ```

2. **Integración OAuth2**:
   ```java
   @Configuration
   @EnableOAuth2Client
   public class ConfigOAuth2 {
       // Configuración OAuth2 de Google
       // Configuración OAuth2 de Microsoft
       // Proveedores OAuth2 personalizados
   }
   ```

3. **Gestión Avanzada de Roles**:
   ```java
   @Entity
   public class Rol {
       private Long id;
       private String nombre;
       private String descripcion;
       private Set<Permiso> permisos;
       private Boolean esRolSistema;
   }
   
   @Entity  
   public class Permiso {
       private Long id;
       private String nombre;
       private String recurso;
       private String accion; // CREAR, LEER, ACTUALIZAR, ELIMINAR
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Implementación MFA para email y SMS
- [ ] Integración OAuth2 con Google y Microsoft
- [ ] Sistema de permisos granulares
- [ ] Mejoras en gestión de sesiones
- [ ] Logging de auditoría de seguridad

---

### **TAREA 4: Dashboard de Análisis en Tiempo Real**
**Prioridad**: Alta  
**Esfuerzo**: 5-6 días

#### **Implementación Técnica:**
1. **Recolección de Métricas**:
   ```java
   @Service
   public class ServicioRecoleccionMetricas {
       @EventListener
       public void manejarAccionUsuario(EventoAccionUsuario evento);
       
       @EventListener
       public void manejarLlamadaApi(EventoLlamadaApi evento);
       
       @Scheduled(fixedDelay = 60000) // Cada minuto
       public void recolectarMetricasSistema();
   }
   ```

2. **Endpoints del Dashboard**:
   ```java
   @RestController
   @RequestMapping("/api/v1/analytics")
   public class ControllerAnalytics {
       
       @GetMapping("/dashboard/resumen")
       public ResponseEntity<ResumenDashboardDTO> obtenerResumen();
       
       @GetMapping("/metricas/uso-api")
       public ResponseEntity<MetricasUsoApiDTO> obtenerMetricasUsoApi(@RequestParam String periodo);
       
       @GetMapping("/metricas/negocio")
       public ResponseEntity<MetricasNegocioDTO> obtenerMetricasNegocio(@RequestParam Long empresaId);
   }
   ```

3. **Actualizaciones en Tiempo Real**:
   ```java
   @Component
   public class ManejadorMetricasWebSocket {
       @MessageMapping("/metricas/suscribir")
       public void suscribirseAMetricas(Principal principal);
       
       @Scheduled(fixedDelay = 5000) // Cada 5 segundos
       public void transmitirMetricas();
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Dashboard en tiempo real con actualizaciones WebSocket
- [ ] Seguimiento de métricas de uso de API
- [ ] Cálculo de KPIs de negocio
- [ ] Widgets de dashboard personalizables
- [ ] Funcionalidad de exportación para reportes

---

## 📊 **PRIORIDAD MEDIA** - Semana 3

### **TAREA 5: Gestión Avanzada de Inventario**
**Prioridad**: Media  
**Esfuerzo**: 4-5 días

#### **Implementación Técnica:**
1. **Gestión de Stock**:
   ```java
   @Entity
   public class ItemInventario {
       private Long id;
       private String sku;
       private String nombre;
       private String categoria;
       private Integer stockActual;
       private Integer stockMinimo;
       private Integer stockMaximo;
       private BigDecimal costoUnitario;
       private String proveedor;
       private LocalDate fechaUltimoRestock;
   }
   
   @Service
   public class ServicioAlertasStock {
       @Scheduled(fixedDelay = 3600000) // Cada hora
       public void verificarAlertasStockBajo();
       
       public void activarOrdenRestock(Long itemId);
   }
   ```

2. **Reorden Automatizado**:
   ```java
   @Entity
   public class ReglaReorden {
       private Long id;
       private Long itemId;
       private Integer puntoReorden;
       private Integer cantidadReorden;
       private Boolean ordenAutomatica;
       private String proveedorId;
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Seguimiento de niveles de stock con alertas
- [ ] Cálculos automatizados de punto de reorden
- [ ] Integración de gestión de proveedores
- [ ] Métodos de valoración de inventario (FIFO, LIFO, Promedio)
- [ ] Historial de movimientos de stock

---

### **TAREA 6: Mejora del Módulo de RRHH**
**Prioridad**: Media  
**Esfuerzo**: 4-5 días

#### **Implementación Técnica:**
1. **Gestión de Empleados**:
   ```java
   @Entity
   public class Empleado {
       private Long id;
       private String codigoEmpleado;
       private String nombres;
       private String apellidos;
       private String email;
       private String departamento;
       private String posicion;
       private LocalDate fechaContratacion;
       private BigDecimal salario;
       private String estadoEmpleo; // ACTIVO, INACTIVO, TERMINADO
   }
   ```

2. **Sistema de Nómina**:
   ```java
   @Service
   public class ServicioNomina {
       public EjecucionNominaDTO procesarNomina(Long empresaId, LocalDate inicioPeríodo, LocalDate finPeríodo);
       public ComprobanteNominaDTO generarComprobante(Long empleadoId, Long ejecucionNominaId);
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Operaciones CRUD de empleados
- [ ] Procesamiento básico de nómina
- [ ] Integración de seguimiento de tiempo
- [ ] Gestión de permisos
- [ ] Marco de evaluación de desempeño

---

## 🔧 **INFRAESTRUCTURA** - Semana 4

### **TAREA 7: Optimización de Performance**
**Prioridad**: Media  
**Esfuerzo**: 3-4 días

#### **Implementación Técnica:**
1. **Optimización de Base de Datos**:
   ```sql
   -- Agregar índices para campos consultados frecuentemente
   CREATE INDEX idx_proyecto_construccion_estado ON proyecto_construccion(estado);
   CREATE INDEX idx_asiento_contable_empresa_fecha ON asiento_contable(empresa_id, fecha_transaccion);
   ```

2. **Estrategia de Cache**:
   ```java
   @Service
   public class ServicioCache {
       @Cacheable(value = "metricas-dashboard", key = "#empresaId")
       public MetricasDashboardDTO obtenerMetricasDashboard(Long empresaId);
       
       @CacheEvict(value = "metricas-dashboard", key = "#empresaId")
       public void invalidarCacheDashboard(Long empresaId);
   }
   ```

3. **Limitación de Rate API**:
   ```java
   @Component
   public class FiltroLimitacionRate implements Filter {
       // Implementar limitación de rate por usuario/IP
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Optimización de consultas de base de datos
- [ ] Implementación de cache Redis
- [ ] Limitación de rate de API
- [ ] Configuración de monitoreo de performance
- [ ] Completar testing de carga

---

### **TAREA 8: Testing y Aseguramiento de Calidad**
**Prioridad**: Media  
**Esfuerzo**: 3-4 días

#### **Implementación Técnica:**
1. **Tests de Integración**:
   ```java
   @SpringBootTest
   @Testcontainers
   public class TestIntegracionModuloConstruction {
       @Container
       static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");
       
       @Test
       public void testFlujoCompletoProyecto();
   }
   ```

2. **Testing de Contratos API**:
   ```java
   @RestClientTest(ConstructionController.class)
   public class TestContratoControllerConstruction {
       // Testear que contratos API coincidan con especificación OpenAPI
   }
   ```

#### **Criterios de Aceptación:**
- [ ] Cobertura de tests de integración >70%
- [ ] Tests de contrato API para todos los endpoints
- [ ] Tests de performance para rutas críticas
- [ ] Testing de penetración de seguridad
- [ ] Validación de documentación

---

## 📋 **Guías de Implementación**

### **Estándares de Calidad de Código:**
- Todo código nuevo debe tener >80% de cobertura de tests
- Seguir patrones arquitectónicos existentes
- Usar manejo apropiado de excepciones
- Implementar logging comprensivo
- Agregar documentación OpenAPI para todos los endpoints

### **Estrategia de Migración de Base de Datos:**
- Usar migraciones Flyway para cambios de esquema
- Asegurar compatibilidad hacia atrás
- Testear migraciones en datos de muestra
- Documentar cambios que rompen compatibilidad

### **Requisitos de Seguridad:**
- Todos los endpoints requieren autenticación a menos que sean explícitamente públicos
- Implementar validación apropiada de entrada
- Usar consultas parametrizadas para prevenir inyección SQL
- Logging de eventos de seguridad para rastro de auditoría

### **Objetivos de Performance:**
- Tiempo de respuesta API <200ms para operaciones simples
- Operaciones complejas (reportes) <2 segundos
- Consultas de base de datos optimizadas con indexación apropiada
- Implementar cache para datos accedidos frecuentemente

---

## 🎯 **Métricas de Éxito**

### **Criterios de Éxito Semana 1:**
- Módulo de construcción completamente funcional
- Características avanzadas de finanzas operativas
- Todos los tests pasando
- Documentación actualizada

### **Criterios de Éxito Semana 2:**
- Características de seguridad mejorada activas
- Dashboard de analytics en tiempo real funcionando
- Mejoras de performance medidas
- Tests de integración completos

### **Criterios de Éxito Semana 3-4:**
- Todas las características V0.3 implementadas
- Performance del sistema optimizada
- Aseguramiento de calidad completo
- Listo para deployment de producción

**Esfuerzo Total Estimado**: 25-30 días  
**Finalización Objetivo**: Fin de Junio 2025  
**Nivel de Riesgo**: Medio (manejable con arquitectura actual) 