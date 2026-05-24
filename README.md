# Prueba Técnica – Mercadona  
Backend con Spring Boot · Carga inicial desde Excel

Este proyecto implementa una API REST básica para gestionar **tiendas**, **secciones** y **trabajadores**.  
Los datos iniciales se cargan automáticamente desde un archivo **Excel (`data.xlsx`)** al arrancar la aplicación.

---

## Tecnologías utilizadas

- Java 17 (OpenJDK Temurin)
- Spring Boot 3
- Spring Data JPA
- H2 Database (en memoria)
- Apache POI (lectura de Excel)
- Maven Wrapper (`mvnw`)
- Git Bash

---

## Estructura del proyecto

src/
├── main/
│   ├── java/com/mercadona/test/
│   │   ├── model/        → Entidades JPA (Store, Section, Worker)
│   │   ├── repository/   → Repositorios JPA
│   │   ├── controller/   → Controladores REST
│   │   ├── service/      → Lógica de lectura de Excel
│   │   └── DataSeeder.java → Carga inicial desde Excel
│   └── resources/
│       ├── application.properties
│       └── data.xlsx     → Archivo Excel con datos iniciales
└── test/                 → Tests automáticos


---

## 📄 Archivo Excel (`data.xlsx`)

El archivo debe ubicarse en:

src/main/resources/data.xlsx


El Excel contiene **3 hojas**:

### 1. stores
| nombre |
|--------|
| Mercadona - Valencia Centro |

### 2. sections
| nombre | horasNecesarias | storeNombre |
|--------|------------------|-------------|
| Frutas y Verduras | 40 | Mercadona - Valencia Centro |
| Pescadería | 30 | Mercadona - Valencia Centro |
| Cajas | 50 | Mercadona - Valencia Centro |

### 3. workers
| nombre | apellidos | dni | horasDisponibles |
|--------|-----------|-----|------------------|
| Ana | García López | 12345678A | 40 |
| Luis | Martínez Pérez | 87654321B | 35 |
| María | Sánchez Ruiz | 11223344C | 20 |

---

## Ejecución del proyecto

1. Java 17 configurado en JAVA_HOME
La aplicación está desarrollada con Java 17, por lo que necesitas:

Tener instalado un JDK 17 (no JRE)

Tener configurada la variable de entorno JAVA_HOME apuntando a la carpeta del JDK

Tener el binario java disponible en tu PATH

Ejemplo en Git Bash:

bash:
export JAVA_HOME="/c/Program Files/Eclipse Adoptium/jdk-17.0.19.10-hotspot"
export PATH="$JAVA_HOME/bin:$PATH"
Puedes comprobarlo con:

bash:
java -version

2. Maven Wrapper (incluido en el proyecto)
No necesitas instalar Maven en tu sistema.
El proyecto ya incluye el Maven Wrapper, que son estos archivos:

Código:
mvnw
mvnw.cmd
.mvn/
Esto permite ejecutar Maven así:

Desde la raíz del proyecto (donde está `pom.xml`):
bash:
./mvnw spring-boot:run

Si la carga del Excel es correcta, verás:
>>> Datos cargados desde Excel correctamente


## Endpoints disponibles
1. Listar todas las tiendas
Código:
GET /stores
Ejemplo de respuesta:
json:
[
  {
    "id": 1,
    "nombre": "Mercadona - Valencia Centro"
  }
]
2. Listar secciones de una tienda
Código:
GET /stores/{storeId}/sections
Ejemplo de respuesta:
json:
[
  {
    "id": 1,
    "nombre": "Frutas y Verduras",
    "horasNecesarias": 40
  },
  {
    "id": 2,
    "nombre": "Pescadería",
    "horasNecesarias": 30
  }
]
3. Listar trabajadores de una tienda
Código:
GET /stores/{storeId}/workers
Ejemplo de respuesta:
json:
[
  {
    "id": 1,
    "nombre": "Ana",
    "apellidos": "García López",
    "dni": "12345678A",
    "horasDisponibles": 40
  }
]



## Carga automática de datos
La clase DataSeeder se ejecuta al iniciar la aplicación:

Lee data.xlsx con Apache POI

Crea la tienda

Crea las secciones asociadas

Crea los trabajadores

Inserta todo en la base de datos H2

Autor
Gonzalo Martín
Prueba técnica Mercadona – Backend
2026