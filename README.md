# Concesionaria – Simulador de Gestión de Vehículos 🚗

## ✨ ¿Qué es esto?

Este proyecto es un programa en **Java** que simula una concesionaria de vehículos. Permite crear, administrar y persistir un inventario de distintos tipos de vehículos (automóviles, camionetas, motocicletas), con funcionalidad de:

- alta, baja y modificación de vehículos  
- búsqueda flexible por diversos criterios  
- listado completo del inventario  
- cálculo de “precio estimado de venta” según reglas propias por tipo de vehículo  
- manejo de taller para vehículos usados: cola de mantenimiento, lavado, procesamiento individual  
- persistencia automática mediante serialización: los datos se guardan y cargan desde un archivo `vehiculos.dat`

El objetivo del proyecto es aplicar principios de **Programación Orientada a Objetos (POO)**, buenas prácticas en diseño de software, uso de colecciones, manejo de archivos, y lógica de negocio realista.

---

## 📂 Estructura del proyecto

```
src/
├─ enums/ → Enumeraciones (Color, carrocerías, tipos de moto)
├─ excepciones/ → Excepciones personalizadas
├─ interfaces/ → Interfaces Lavable y Mantenible
├─ persistencia/ → Utilidades para guardar/cargar datos
├─ servicios/ → Taller, Lavadero, Wrapper
├─ utils/ → Herramientas de entrada, impresión y manejo de enums
├─ vehiculos/ → Clases base y derivadas (Automóvil, Camioneta, Motocicleta)
├─ Concesionaria → Lógica interna del sistema
└─ Main.java → Interfaz por consola
```

---


---

## 🛠️ Tecnologías y conceptos utilizados

- **Java 17+**  
- Programación Orientada a Objetos: herencia, polimorfismo, interfaces, métodos abstractos  
- Uso de **UUID** para identificar vehículos de forma única  
- Uso de `List`, `Queue` y Streams para colección y filtrado de datos  
- Serialización con `ObjectOutputStream / ObjectInputStream` para persistencia  
- Validación de entrada de datos (año, estado, opciones del menú, etc.)  
- Interfaz por consola con menús interactivos  
- Diseño modular con separación de responsabilidades (dominio, persistencia, utilidades, lógica de negocio, UI)  

---

## 🚀 Cómo compilar y ejecutar

Desde la raíz del proyecto:

```bash
# 1. Compilar todos los archivos .java
javac -d out src/**/*.java

# 2. Ejecutar la aplicación
java -cp out Main
```

Al iniciar, si existe vehiculos.dat, el programa carga automáticamente el inventario.
El inventario se guarda automáticamente en las siguientes situaciones:

- al agregar un vehículo

- al modificar un vehículo

- al eliminar un vehículo

- al procesar un vehículo en el taller

- al salir del programa

✅ Funcionalidades principales

- Agregar vehículos (automóvil, camioneta, motocicleta), con sus atributos específicos

- Listar todos los vehículos del inventario

- Búsqueda flexible: por marca, modelo, año, estado (nuevo/usado) o combinaciones

- Modificar datos de los vehículos

- Eliminar vehículos, seleccionando entre coincidencias

- Calcular precio estimado de venta según tipo de vehículo y sus atributos

- Cola de taller para vehículos usados: listar, seleccionar y procesar uno por uno

- Persistencia de datos: guardar y cargar inventario automáticamente

📈 Lógica de cálculo de precio de venta

Para cada tipo de vehículo se aplica una fórmula diferente:
| Tipo de vehículo | Precio base / Reglas                                                                                          |
| ---------------- | ------------------------------------------------------------------------------------------------------------- |
| **Automóvil**    | Base: 20.000 (nuevo) / 15.000 (usado) → se descuenta $500 por año de antigüedad (mínimo $7.500)               |
| **Camioneta**    | Base: 30.000 (nuevo) / 20.000 (usado) → + $5.000 por cada 1.000 kg de carga → − $500 por año (mínimo $10.000) |
| **Motocicleta**  | Base: 5.000 → + $500 por cada 50 cc → − $200 por año (mínimo $3.000)                                          |

📋 Buenas prácticas de diseño aplicadas

- Cada clase tiene una responsabilidad clara.

- Uso de abstracción y polimorfismo: método calcularPrecioDeVenta() definido en la superclase.

- Separación entre lógica de dominio, persistencia, utilidades e interfaz de usuario.

- Validaciones para entrada de datos.

- Persistencia serializada para mantener estado entre ejecuciones.

- Uso de identificador único (UUID) para cada vehículo, garantizando unicidad global.

## 📝 Autores: Vicentin Erick / Aail Luciano

Trabajo práctico realizado como parte de la materia Programación 2 de la Tecnicatura Universitaria en Programacion de la Universidad Tecnologica Nacional - Facultad Regional Resistencia.


