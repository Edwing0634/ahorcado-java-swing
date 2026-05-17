# Juego del Ahorcado Interactivo con Java Swing

Proyecto de Programación Orientada a Objetos (POO Virtual). Juego del
ahorcado clásico con interfaz gráfica Swing, 3 niveles de dificultad,
sistema de 3 pistas y banco de 200 palabras en 5 categorías.

## Requisitos

- Java JDK 11 o superior (probado con JDK 25).

## Cómo compilar

Desde la raíz del proyecto, en PowerShell:

```powershell
$archivos = Get-ChildItem -Recurse -Filter *.java -Path src,pruebas | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $archivos
```

## Cómo ejecutar el juego

```powershell
java -cp "out;recursos" Main
```

## Cómo ejecutar las pruebas

```powershell
java -cp out PruebasPartida
```

## Estructura

- `src/modelo/` — lógica pura del juego (sin Swing).
- `src/vista/` — pantallas Swing y dibujo del ahorcado.
- `src/controlador/` — une vista y modelo.
- `recursos/palabras.txt` — banco de 200 palabras.
- `pruebas/` — pruebas de la lógica.
- `docs/` — diagrama de clases, manual de usuario, guion del video.

## Conceptos de POO aplicados

- **Herencia y polimorfismo:** clase abstracta `NivelDificultad` con
  subclases `Facil`, `Medio`, `Dificil`; `filtrarPalabras()` es polimórfico.
- **Encapsulación:** atributos privados con acceso por getters en todo
  el modelo.
- **ArrayList:** banco de palabras, letras acertadas y falladas.

## Autores

[Completar con los nombres del equipo]

## Video de presentación

[Pegar aquí el enlace de YouTube cuando esté listo]
