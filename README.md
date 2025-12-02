#Concesionaria - Trabajo Práctico Integrador (Programación 2 – Java)

Este proyecto corresponde al Trabajo Práctico Integrador de la materia **Programación 2**, orientado a reforzar los conceptos de:

- Programación Orientada a Objetos (POO)
- Herencia y Polimorfismo
- Interfaces
- Excepciones
- Enumeraciones
- Estructuras de datos (List, Queue)
- Persistencia mediante serialización
- Manejo de menú interactivo por consola
- Buenas prácticas de organización del código

El sistema simula la administración de una **concesionaria de vehículos**, permitiendo gestionar un inventario persistente y un flujo de trabajo para vehículos usados que deben pasar por un taller.

---

## 📁 Contenido del proyecto

### 🔹 **Paquetes principales**
El repositorio está organizado de forma modular:

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

## 🚀 Funcionalidades principales

### ✔ **Gestión de inventario**
- Agregar vehículos
- Listar vehículos
- Buscar por:
  - Marca
  - Modelo
  - Año
  - Estado (nuevo/usado)
  - Combinaciones (marca + modelo, etc.)
- Modificar datos de un vehículo
- Eliminar vehículos con selección por índice

### ✔ **Soporte para distintos tipos de vehículos**
Cada tipo posee atributos particulares:

| Tipo | Atributos específicos |
|------|------------------------|
| Automóvil | Carrocería de auto |
| Camioneta | Carrocería + capacidad de carga |
| Motocicleta | Tipo de moto + cilindrada |

### ✔ **Taller para vehículos usados**
Los vehículos usados ingresan automáticamente a una **cola FIFO**:

- Visualización de la cola
- Selección manual de cuál procesar
- Paso por lavado y mantenimiento

### ✔ **Persistencia automática**
El inventario se guarda en un archivo binario: ```vehiculos.dat```


Se carga automáticamente al iniciar el programa y se guarda:

- Al agregar
- Al modificar
- Al eliminar
- Al cerrar el programa

---

## 🛠 Tecnologías utilizadas

- **Java 17+**
- Programación Orientada a Objetos
- Serialización (`ObjectOutputStream / ObjectInputStream`)
- Estructuras de datos (`ArrayList`, `Queue`)
- Excepciones personalizadas
- Menú interactivo por consola

---

## ▶️ Ejecución

1. Clonar el repositorio:

```bash
git clone https://github.com/erickvicentin/tpi-programacion-2.git
cd tpi-programacion-2
```
2. Compilar el proyecto:
```
javac -d out src/**/*.java
```
3. Ejecutar:
```
java -cp out Main
```

📌 Estructura del menú principal
```
========= CONCESIONARIA =========
1. Agregar vehículo
2. Listar vehículos
3. Buscar vehículo
4. Eliminar vehículo
5. Procesar vehículo en taller
6. Modificar vehículo
0. Salir
```

📘 Objetivos pedagógicos

El proyecto permite practicar:

- Construcción de jerarquías de clases

- Uso de interfaces y polimorfismo

- Organización modular del código

- Manejo seguro de entrada del usuario

- Filtrado avanzado mediante Streams

- Separación de responsabilidades (utils, servicios, persistencia)

- Persistencia simple pero efectiva

- Uso de UUID como identificador único

📝 Autores: Vicentin Erick / Aail Luciano

Trabajo práctico realizado como parte de la materia Programación 2 de la Tecnicatura Universitaria en Programacion de la Universidad Tecnologica Nacional - Facultad Regional Resistencia.


