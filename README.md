# ProgramacionConcurrente SpringBatch
 * Este es mi repositorio: https://github.com/Marc20035/BancoBatch.git
# Enunciado:
Enunciado de la Práctica Spring Batch

Objetivo:

El objetivo de esta práctica es desarrollar una aplicación de procesamiento por batchs, utilizando la programación concurrente para optimizar el rendimiento y minimizar el tiempo de procesamiento.

El programa deberá leer un gran volumen de datos, procesar estos datos de manera eficiente utilizando programación concurrente y, finalmente, almacenar los datos procesados. El contexto de los datos será definido a continuación.

Contexto:

Imaginemos que somos parte de una gran organización que maneja una enorme cantidad de datos de transacciones bancarias. Estos datos son almacenados en archivos planos y se necesita un programa que pueda procesar estos datos por la noche, cuando la base de datos no está bloqueada por otras tareas.

Además, se necesita que el programa pueda reanudar el trabajo en caso de fallos y sea capaz de manejar transacciones. Cada transacción constará de un lote de procesamiento de datos, por ejemplo, una confirmación cada 5,000 inserciones.

El programa deberá estar construido de tal manera que pueda dividirse en pequeñas tareas que se ejecuten en secuencia, generando flujos de trabajo, de modo similar a los microservicios.

Detalles del Proyecto:

El programa deberá implementar las siguientes funciones:

Leer los datos del archivo de transacciones bancarias.
Procesar los datos utilizando programación concurrente. Esta etapa deberá incluir la validación de datos y el procesamiento y cálculos sobre los datos.
Almacenar los datos procesados en la base de datos.
Implementar un programador que encadene las tareas y administre las operaciones de flujo.
Recursos:

Para realizar esta práctica, se sugiere utilizar Java con el framework Spring Batch debido a su eficacia para el procesamiento por batchs. No obstante, puedes elegir el lenguaje de programación y las herramientas que prefieras, siempre que permitan la programación concurrente y sean adecuados para el procesamiento por batchs.

Se proporcionará un conjunto de datos de transacciones bancarias ficticias para fines de prueba.

Entrega:

La entrega de la práctica deberá incluir el código fuente de la aplicación, junto con una documentación que explique el diseño del programa, cómo se implementó la programación concurrente, y cómo se gestiona el flujo de trabajo. Además, debes incluir un análisis del rendimiento de la aplicación y cómo la programación concurrente ha optimizado el tiempo de procesamiento.

Rúbrica para la Práctica de Programación Concurrente:

Funcionalidad del programa (40 puntos):

El programa puede leer correctamente los datos del archivo de transacciones bancarias (10 puntos).
El programa procesa los datos correctamente utilizando programación concurrente. Esto incluye la validación de los datos y los cálculos realizados sobre los datos (15 puntos).
El programa almacena correctamente los datos procesados en la base de datos (10 puntos).
Implementación correcta del programador que encadena las tareas y administra las operaciones de flujo (5 puntos).
Concurrencia y Optimización (30 puntos):

El programa implementa correctamente la programación concurrente para optimizar el tiempo de procesamiento (15 puntos).
El programa muestra una mejora significativa en el tiempo de procesamiento en comparación con una versión no concurrente del mismo (15 puntos).
Manejo de errores y robustez (10 puntos):

El programa puede manejar errores y excepciones de manera adecuada, incluyendo la capacidad de reanudar trabajos erróneos (5 puntos).
El programa es robusto y puede manejar diferentes casos de entrada, incluyendo datos inválidos o problemáticos (5 puntos).
Documentación (20 puntos):

La documentación explica correctamente el diseño del programa, cómo se implementó la programación concurrente, y cómo se gestiona el flujo de trabajo (10 puntos).
La documentación incluye un análisis del rendimiento de la aplicación, explicando cómo la programación concurrente ha optimizado el tiempo de procesamiento (10 puntos).
Los puntos se otorgarán de manera proporcional en cada categoría en función de la correcta implementación y cumplimiento de los requisitos. En caso de presentar dudas o dificultades durante la realización de la práctica, es recomendable consultar con el profesor para obtener la orientación necesaria.
# Overleaf:
https://www.overleaf.com/9987142741mfcchbdmxkvw#72637a