#!/usr/bin/env python3
"""
Análisis de Dependencias - BusinessProSuiteAPI Modularization
==============================================================

Este script analiza las dependencias entre módulos para identificar:
1. Dependencias cruzadas problemáticas
2. God Objects (como ConfigCompany)
3. Bounded contexts mal definidos
4. Sugerencias de refactorización
"""

import os
import re
import json
from collections import defaultdict, Counter
from pathlib import Path
import matplotlib.pyplot as plt
import networkx as nx

class DependencyAnalyzer:
    def __init__(self, src_path="src/main/java/com/businessprosuite/api"):
        self.src_path = Path(src_path)
        self.modules = [
            'config', 'company', 'customer', 'finance', 'hr', 
            'inventory', 'asset', 'leasing', 'workflow', 'security',
            'analytics', 'audit', 'education', 'agriculture', 'retail',
            'logistics', 'healthcare', 'construction', 'manufacturing',
            'notification', 'gdpr', 'etl', 'error', 'subs', 'schema',
            'user', 'document', 'core'
        ]
        self.dependencies = defaultdict(set)
        self.god_objects = defaultdict(int)
        self.cross_module_usage = defaultdict(list)
        
    def scan_files(self):
        """Escanea todos los archivos Java para encontrar imports y dependencias"""
        print("🔍 Escaneando archivos Java...")
        
        for java_file in self.src_path.rglob("*.java"):
            module = self._get_module_from_path(java_file)
            if not module:
                continue
                
            content = java_file.read_text(encoding='utf-8', errors='ignore')
            imports = self._extract_imports(content)
            
            for imp in imports:
                target_module = self._get_module_from_import(imp)
                if target_module and target_module != module:
                    self.dependencies[module].add(target_module)
                    self.cross_module_usage[f"{module}->{target_module}"].append(str(java_file))
                    
            # Detectar God Objects
            self._detect_god_objects(content, java_file)
            
    def _get_module_from_path(self, file_path):
        """Extrae el módulo desde la ruta del archivo"""
        path_parts = file_path.parts
        try:
            api_index = path_parts.index('api')
            if api_index + 1 < len(path_parts):
                next_part = path_parts[api_index + 1]
                if next_part in self.modules:
                    return next_part
                # Buscar en subdirectorios (dto, model, service, etc.)
                for i in range(api_index + 2, len(path_parts)):
                    if path_parts[i] in self.modules:
                        return path_parts[i]
        except ValueError:
            pass
        return None
        
    def _extract_imports(self, content):
        """Extrae todas las declaraciones import de un archivo"""
        import_pattern = r'import\s+com\.businessprosuite\.api\.([^;]+);'
        return re.findall(import_pattern, content)
        
    def _get_module_from_import(self, import_path):
        """Determina el módulo desde una declaración import"""
        parts = import_path.split('.')
        for part in parts:
            if part in self.modules:
                return part
        return None
        
    def _detect_god_objects(self, content, file_path):
        """Detecta objetos que son referenciados por muchos módulos"""
        # Buscar referencias a ConfigCompany, Company, etc.
        god_candidates = ['ConfigCompany', 'Company', 'SecurityUser', 'User']
        
        for candidate in god_candidates:
            if candidate in content:
                self.god_objects[candidate] += 1
                
    def analyze_coupling(self):
        """Analiza el acoplamiento entre módulos"""
        print("📊 Analizando acoplamiento entre módulos...")
        
        coupling_report = {
            'high_coupling': [],
            'moderate_coupling': [],
            'low_coupling': [],
            'god_objects': dict(self.god_objects),
            'dependency_matrix': {}
        }
        
        # Crear matriz de dependencias
        for module in self.modules:
            coupling_report['dependency_matrix'][module] = {}
            for target in self.modules:
                if target in self.dependencies[module]:
                    coupling_report['dependency_matrix'][module][target] = len(
                        [f for f in self.cross_module_usage[f"{module}->{target}"]]
                    )
                else:
                    coupling_report['dependency_matrix'][module][target] = 0
                    
        # Clasificar acoplamiento
        for module, deps in self.dependencies.items():
            coupling_score = len(deps)
            if coupling_score >= 5:
                coupling_report['high_coupling'].append({
                    'module': module,
                    'dependencies': list(deps),
                    'score': coupling_score
                })
            elif coupling_score >= 3:
                coupling_report['moderate_coupling'].append({
                    'module': module,
                    'dependencies': list(deps),
                    'score': coupling_score
                })
            else:
                coupling_report['low_coupling'].append({
                    'module': module,
                    'dependencies': list(deps),
                    'score': coupling_score
                })
                
        return coupling_report
        
    def suggest_refactoring(self, coupling_report):
        """Genera sugerencias de refactorización"""
        print("💡 Generando sugerencias de refactorización...")
        
        suggestions = {
            'immediate_actions': [],
            'architectural_changes': [],
            'god_object_solutions': [],
            'module_boundaries': []
        }
        
        # Acciones inmediatas para alto acoplamiento
        for item in coupling_report['high_coupling']:
            module = item['module']
            deps = item['dependencies']
            suggestions['immediate_actions'].append({
                'priority': 'HIGH',
                'module': module,
                'issue': f'Alto acoplamiento con {len(deps)} módulos',
                'dependencies': deps,
                'solution': f'Extraer {module} a bounded context separado con event-driven communication'
            })
            
        # God Objects
        for obj, count in coupling_report['god_objects'].items():
            if count > 10:  # Usado en más de 10 lugares
                suggestions['god_object_solutions'].append({
                    'priority': 'CRITICAL',
                    'object': obj,
                    'usage_count': count,
                    'solution': f'Fragmentar {obj} en Value Objects específicos por dominio'
                })
                
        # Cambios arquitectónicos
        core_modules = ['config', 'company', 'user', 'security']
        business_modules = ['finance', 'hr', 'inventory', 'customer', 'asset']
        
        for module in coupling_report['high_coupling']:
            mod_name = module['module']
            if mod_name in core_modules:
                suggestions['architectural_changes'].append({
                    'type': 'CORE_MODULE_EXTRACTION',
                    'module': mod_name,
                    'target_structure': 'bps-core',
                    'reason': 'Módulo core con alta dependencia'
                })
            elif mod_name in business_modules:
                suggestions['architectural_changes'].append({
                    'type': 'BUSINESS_MODULE_EXTRACTION',
                    'module': mod_name,
                    'target_structure': 'bps-business',
                    'reason': 'Módulo de negocio con acoplamiento'
                })
                
        return suggestions
        
    def generate_visualization(self, coupling_report):
        """Genera visualizaciones del acoplamiento"""
        print("📈 Generando visualizaciones...")
        
        # Crear grafo de dependencias
        G = nx.DiGraph()
        
        for module, deps in self.dependencies.items():
            G.add_node(module)
            for dep in deps:
                G.add_edge(module, dep)
                
        # Configurar layout
        plt.figure(figsize=(15, 10))
        pos = nx.spring_layout(G, k=3, iterations=50)
        
        # Colorear nodos según el nivel de acoplamiento
        node_colors = []
        for node in G.nodes():
            deps_count = len(self.dependencies[node])
            if deps_count >= 5:
                node_colors.append('red')      # Alto acoplamiento
            elif deps_count >= 3:
                node_colors.append('orange')   # Medio acoplamiento
            else:
                node_colors.append('lightgreen')  # Bajo acoplamiento
                
        # Dibujar grafo
        nx.draw(G, pos, 
               node_color=node_colors,
               node_size=1000,
               with_labels=True,
               font_size=8,
               font_weight='bold',
               arrows=True,
               arrowsize=20,
               edge_color='gray',
               alpha=0.7)
               
        plt.title("Mapa de Dependencias entre Módulos\n" +
                 "🔴 Alto Acoplamiento | 🟠 Medio Acoplamiento | 🟢 Bajo Acoplamiento",
                 fontsize=14, fontweight='bold')
        plt.savefig('module-dependencies-graph.png', dpi=300, bbox_inches='tight')
        plt.close()
        
        # Generar heatmap de acoplamiento
        self._generate_heatmap(coupling_report)
        
    def _generate_heatmap(self, coupling_report):
        """Genera heatmap de la matriz de dependencias"""
        import numpy as np
        import seaborn as sns
        
        matrix = coupling_report['dependency_matrix']
        modules = list(matrix.keys())
        
        # Crear matriz numpy
        size = len(modules)
        heatmap_data = np.zeros((size, size))
        
        for i, mod1 in enumerate(modules):
            for j, mod2 in enumerate(modules):
                if mod2 in matrix[mod1]:
                    heatmap_data[i][j] = matrix[mod1][mod2]
                    
        # Crear heatmap
        plt.figure(figsize=(12, 10))
        sns.heatmap(heatmap_data, 
                   xticklabels=modules,
                   yticklabels=modules,
                   annot=True,
                   fmt='g',
                   cmap='Reds',
                   cbar_kws={'label': 'Número de Referencias'})
        plt.title('Matriz de Acoplamiento entre Módulos', fontsize=14, fontweight='bold')
        plt.xlabel('Módulo Dependencia')
        plt.ylabel('Módulo Origen')
        plt.xticks(rotation=45, ha='right')
        plt.yticks(rotation=0)
        plt.tight_layout()
        plt.savefig('coupling-heatmap.png', dpi=300, bbox_inches='tight')
        plt.close()
        
    def generate_report(self):
        """Genera reporte completo de análisis"""
        print("📋 Generando reporte completo...")
        
        # Ejecutar análisis
        self.scan_files()
        coupling_report = self.analyze_coupling()
        suggestions = self.suggest_refactoring(coupling_report)
        
        # Generar visualizaciones
        self.generate_visualization(coupling_report)
        
        # Crear reporte markdown
        report_content = self._create_markdown_report(coupling_report, suggestions)
        
        with open('DEPENDENCY_ANALYSIS_REPORT.md', 'w', encoding='utf-8') as f:
            f.write(report_content)
            
        # Guardar datos JSON para procesamiento posterior
        analysis_data = {
            'coupling_report': coupling_report,
            'suggestions': suggestions,
            'cross_module_usage': dict(self.cross_module_usage)
        }
        
        with open('dependency_analysis.json', 'w', encoding='utf-8') as f:
            json.dump(analysis_data, f, indent=2, ensure_ascii=False)
            
        print("✅ Reporte generado: DEPENDENCY_ANALYSIS_REPORT.md")
        print("✅ Datos guardados: dependency_analysis.json")
        print("✅ Gráficos generados: module-dependencies-graph.png, coupling-heatmap.png")
        
    def _create_markdown_report(self, coupling_report, suggestions):
        """Crea el contenido del reporte en markdown"""
        report = f"""# 📊 ANÁLISIS DE DEPENDENCIAS - BusinessProSuiteAPI

## 🎯 RESUMEN EJECUTIVO

**God Objects Detectados**: {len(coupling_report['god_objects'])}
**Módulos con Alto Acoplamiento**: {len(coupling_report['high_coupling'])}
**Módulos con Acoplamiento Moderado**: {len(coupling_report['moderate_coupling'])}
**Módulos con Bajo Acoplamiento**: {len(coupling_report['low_coupling'])}

---

## ❌ PROBLEMAS CRÍTICOS IDENTIFICADOS

### 🔴 MÓDULOS CON ALTO ACOPLAMIENTO
"""
        
        for item in coupling_report['high_coupling']:
            report += f"""
#### {item['module'].upper()} (Score: {item['score']})
- **Dependencias**: {', '.join(item['dependencies'])}
- **Impacto**: Alto riesgo para migración a microservicios
- **Acción Requerida**: Refactorización inmediata
"""

        report += """
### 👹 GOD OBJECTS DETECTADOS
"""
        
        for obj, count in coupling_report['god_objects'].items():
            if count > 5:
                report += f"""
- **{obj}**: Usado en {count} lugares
  - **Problema**: Viola principio de responsabilidad única
  - **Solución**: Fragmentar en Value Objects específicos
"""

        report += """
---

## 💡 SUGERENCIAS DE REFACTORIZACIÓN

### 🚨 ACCIONES INMEDIATAS (PRIORIDAD ALTA)
"""
        
        for action in suggestions['immediate_actions']:
            report += f"""
#### {action['module'].upper()}
- **Problema**: {action['issue']}
- **Dependencias Problemáticas**: {', '.join(action['dependencies'])}
- **Solución**: {action['solution']}
"""

        report += """
### 🏗️ CAMBIOS ARQUITECTÓNICOS RECOMENDADOS
"""
        
        for change in suggestions['architectural_changes']:
            report += f"""
- **Módulo**: {change['module']}
- **Tipo**: {change['type']}
- **Destino**: {change['target_structure']}
- **Razón**: {change['reason']}
"""

        report += """
---

## 📈 MÉTRICAS DE MODULARIDAD

### Distribución de Acoplamiento
"""
        
        total_modules = len(coupling_report['high_coupling']) + len(coupling_report['moderate_coupling']) + len(coupling_report['low_coupling'])
        
        if total_modules > 0:
            high_pct = (len(coupling_report['high_coupling']) / total_modules) * 100
            mod_pct = (len(coupling_report['moderate_coupling']) / total_modules) * 100
            low_pct = (len(coupling_report['low_coupling']) / total_modules) * 100
            
            report += f"""
- 🔴 **Alto Acoplamiento**: {high_pct:.1f}% ({len(coupling_report['high_coupling'])} módulos)
- 🟠 **Acoplamiento Moderado**: {mod_pct:.1f}% ({len(coupling_report['moderate_coupling'])} módulos)  
- 🟢 **Bajo Acoplamiento**: {low_pct:.1f}% ({len(coupling_report['low_coupling'])} módulos)

### Objetivo Post-Modularización
- 🎯 **Alto Acoplamiento**: < 10%
- 🎯 **Acoplamiento Moderado**: < 30%
- 🎯 **Bajo Acoplamiento**: > 60%
"""

        report += """
---

## 🛠️ PLAN DE IMPLEMENTACIÓN

### FASE 1: Resolución God Objects (1-2 semanas)
1. Fragmentar ConfigCompany en value objects específicos
2. Crear interfaces para comunicación entre módulos
3. Implementar Event-Driven patterns

### FASE 2: Extracción Módulos Core (2-3 semanas)  
1. Extraer módulos con bajo acoplamiento primero
2. Crear shared modules para componentes comunes
3. Implementar bounded contexts claros

### FASE 3: Refactorización Alto Acoplamiento (3-4 semanas)
1. Aplicar patterns de desacoplamiento
2. Implementar APIs bien definidas
3. Testing de integración por módulo

---

## 📋 CHECKPOINTS DE VALIDACIÓN

- [ ] **Sin dependencias circulares**
- [ ] **God Objects fragmentados** 
- [ ] **Bounded contexts definidos**
- [ ] **Event-driven communication implementado**
- [ ] **APIs de módulo bien definidas**
- [ ] **Tests de integración por módulo**
- [ ] **Métricas de acoplamiento mejoradas**

---

*Reporte generado automáticamente por DependencyAnalyzer*
"""
        
        return report

def main():
    """Función principal"""
    print("🚀 INICIANDO ANÁLISIS DE DEPENDENCIAS")
    print("====================================")
    
    if not Path("src/main/java/com/businessprosuite/api").exists():
        print("❌ Error: No se encontró el directorio source de la API")
        print("   Asegúrate de ejecutar desde el directorio raíz del proyecto")
        return
        
    analyzer = DependencyAnalyzer()
    analyzer.generate_report()
    
    print("")
    print("🎉 ANÁLISIS COMPLETADO")
    print("======================")
    print("📄 Revisa DEPENDENCY_ANALYSIS_REPORT.md para el reporte completo")
    print("📊 Revisa los gráficos generados para visualizar dependencias")
    print("🔧 Usa las sugerencias para planificar la refactorización")

if __name__ == "__main__":
    main() 