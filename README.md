# Ledger-KT

Sistema de contabilidad (Ledger) robusto desarrollado con **Kotlin** y **Spring Boot**, diseñado para gestionar
transacciones financieras entre cuentas con un enfoque en integridad de datos e idempotencia.

## 🚀 Tecnologías

- **Kotlin** 2.2.21
- **Spring Boot** 4.0.6 (Spring Framework)
- **Spring Data JPA** con **H2 Database** (En memoria)
- **Kotlin Coroutines** para procesamiento asíncrono y paralelo.
- **JUnit 5**, **MockK** y **Kotest** para pruebas unitarias e integración.
- **Gradle** (Kotlin DSL) como gestor de dependencias.

## 🏗️ Arquitectura y Diseño

El proyecto sigue principios de **Diseño Orientado al Dominio (DDD)** y una arquitectura limpia/hexagonal simplificada,
organizada por contextos delimitados:

- **Account**: Gestión de cuentas de usuario y monedas.
- **Transaction**: Orquestación de transferencias de fondos, asegurando idempotencia mediante claves únicas.
- **Ledger**: El libro mayor donde se registran todas las entradas (DEBIT/CREDIT) para auditoría e integridad.
- **Common**: Utilidades compartidas como logging.

## ✨ Funcionalidades Principales

### 1. API de Transferencias

Permite realizar movimientos de dinero entre dos cuentas de forma atómica y segura.

- **Endpoint**: `POST /api/ledger/transfer`
- **Idempotencia**: Soporta `idempotencyKey` para evitar procesar la misma transacción múltiples veces en caso de
  reintentos.

### 2. Procesamiento de Transacciones por Lotes (Scheduler)

`TransactionScheduler` lee periódicamente archivos CSV de transacciones (ubicados en
`src/main/resources/transactions.csv`) para su procesamiento automático.

### 3. Reconciliación del Libro Mayor (Scheduler)

`LedgerScheduler` utiliza **Corrutinas** y **Semáforos** para verificar la consistencia de los saldos en el libro mayor
de manera eficiente y paralela, alertando sobre cualquier descuadre.

## 🛠️ Configuración y Ejecución

### Requisitos

- JDK 21

### Ejecución local

Para ejecutar la aplicación, utiliza el wrapper de Gradle:

```bash
./gradlew bootRun
```

La consola de H2 estará disponible en `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ledger`).

### Ejecución de Pruebas

El proyecto incluye pruebas de integración que verifican el flujo completo de transferencia:

```bash
./gradlew test
```

## 📁 Estructura del Proyecto

- `src/main/kotlin`: Contiene la lógica de negocio organizada por dominios.
- `src/main/resources`: Configuraciones y archivos de datos (CSV).
- `src/test/kotlin`: Pruebas unitarias e integrales (IT).
