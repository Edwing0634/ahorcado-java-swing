# Diseño — Juego del Ahorcado Interactivo con Java Swing

**Fecha:** 2026-05-17
**Proyecto:** Programación Orientada a Objetos (POO Virtual)
**Repositorio GitHub:** `ahorcado-java-swing` (público, se crea tras generar el esqueleto)

---

## 1. Objetivo

Desarrollar un juego del ahorcado clásico en Java + Swing donde el usuario adivina una
palabra secreta. El proyecto debe demostrar Programación Orientada a Objetos
(encapsulación, herencia, polimorfismo), uso de `ArrayList`, e interfaz gráfica Swing,
maximizando el puntaje de la rúbrica (24 puntos totales).

### Mapa de la rúbrica

| Criterio | Puntos | Cómo se cubre |
|---|---|---|
| Aplicación de POO | /8 | Clase abstracta `NivelDificultad` + subclases (herencia, polimorfismo); encapsulación en todo el modelo |
| Funcionalidad del juego | /8 | 7 intentos, 3 pistas, 5 categorías, retroalimentación visual |
| Interfaz gráfica (Swing) | /6 | 3 pantallas con `CardLayout`, teclado en pantalla, dibujo Java2D del ahorcado |
| Gestión de datos (ArrayList) | /4 | `ArrayList` para banco de palabras, letras acertadas/falladas |
| Video, documentación y entregables | /4 | Diagrama de clases, contenido del manual, guion de video, código comentado |

---

## 2. Restricciones técnicas

- **Lenguaje:** Java (JDK 25 disponible en el entorno; el código usa solo APIs estándar
  compatibles con Java 11+ para portabilidad).
- **Sin dependencias externas.** Solo el JDK. Compilable con `javac`, empaquetable como
  `.jar` ejecutable.
- **Paradigma:** POO. Estructura de datos requerida: `ArrayList`. Interfaz: Swing.
- **Banco:** 200 palabras en 5 categorías (40 por categoría).

---

## 3. Arquitectura

Aplicación de escritorio en **3 capas** con límites claros:

```
ahorcado-java-swing/
├── src/
│   ├── Main.java                 # punto de entrada
│   ├── modelo/                   # lógica pura, SIN Swing
│   │   ├── NivelDificultad.java  # clase abstracta
│   │   ├── Facil.java            # subclase
│   │   ├── Medio.java            # subclase (default, 7 intentos)
│   │   ├── Dificil.java          # subclase
│   │   ├── Categoria.java        # enum (5 categorías)
│   │   ├── Palabra.java          # texto + categoría + pista escrita
│   │   ├── BancoPalabras.java    # carga palabras.txt → ArrayList
│   │   └── Partida.java          # estado y reglas del juego
│   ├── vista/                    # pantallas Swing
│   │   ├── PantallaInicio.java
│   │   ├── PantallaJuego.java
│   │   ├── PantallaFin.java
│   │   └── PanelAhorcado.java    # dibujo Java2D
│   └── controlador/
│       └── ControladorJuego.java # conecta vista ↔ modelo
├── recursos/
│   └── palabras.txt              # 200 palabras (formato: categoria;palabra;pista)
├── pruebas/
│   └── PruebasPartida.java       # pruebas de la lógica pura (main con ✓/✗)
├── docs/
│   ├── diagrama-clases.md        # Mermaid + PlantUML
│   ├── manual-usuario.md         # contenido para el PDF
│   ├── guion-video.md            # guion sugerido para el equipo
│   └── superpowers/specs/        # este documento
├── .gitignore
└── README.md
```

**Principio clave:** la capa `modelo` no conoce Swing. `Partida` se puede ejecutar y
probar sin abrir ninguna ventana. El `controlador` es el único punto donde vista y
modelo se tocan, manteniendo las capas desacopladas.

### Flujo de la aplicación

```
Main → PantallaInicio (elegir dificultad)
         ↓
       PantallaJuego (jugar: teclado en pantalla + 3 pistas)
         ↓ (ganó o perdió)
       PantallaFin (resultado + palabra revelada)
         ↓ "Jugar de nuevo"
       PantallaInicio
```

Una sola ventana `JFrame` con `CardLayout` alterna entre las 3 pantallas.

---

## 4. Modelo de clases (POO)

### 4.1 Herencia y polimorfismo

```
NivelDificultad (abstracta)
├── Facil    → 8 intentos,  palabras de 3-5 letras,  3 pistas
├── Medio    → 7 intentos,  palabras de 6-8 letras,  2 pistas
└── Dificil  → 6 intentos,  palabras de 9+ letras,   1 pista
```

`NivelDificultad` (clase abstracta):
- Métodos abstractos: `getIntentosMaximos()`, `getPistasDisponibles()`, `getNombre()`.
- Método polimórfico concreto: `filtrarPalabras(List<Palabra>)` — cada subclase decide
  qué palabras le sirven según longitud (implementación distinta por subclase mediante
  un método abstracto auxiliar `aplicaLongitud(int)`).

El enunciado pide "máximo 7 intentos" → ese es el nivel **Medio** (default). Fácil y
Difícil aportan la "dificultad progresiva" que también menciona el enunciado.

### 4.2 Resto del modelo (encapsulación)

Todos los atributos `private`, acceso por getters. Sin setters donde no haga falta.

- **`Categoria`** — `enum { ANIMALES, PAISES, DEPORTES, OBJETOS, COMIDAS }`. Cada valor
  con un nombre legible para mostrar en pantalla.

- **`Palabra`** — atributos: `texto` (String), `categoria` (Categoria),
  `pistaTexto` (String). Inmutable tras construirse. Método `getTextoNormalizado()`
  para comparar sin distinguir mayúsculas/acentos.

- **`BancoPalabras`** — carga `recursos/palabras.txt` a un `ArrayList<Palabra>`.
  - `cargar()` lee el archivo línea por línea (formato `categoria;palabra;pista`).
  - Líneas mal formadas o vacías se **saltan** (no rompen la carga); se cuenta cuántas.
  - Si el archivo no existe o no hay ninguna palabra válida → lanza excepción propia
    `BancoPalabrasException` que el `Main` captura y muestra en un `JOptionPane`.
  - `palabraAleatoria(NivelDificultad)` — filtra con `nivel.filtrarPalabras(...)` y
    devuelve una al azar. Si el filtro deja la lista vacía, hace *fallback* a todo el
    banco (robustez).

- **`Partida`** — corazón de la lógica. Atributos privados:
  - `palabra` (Palabra), `nivel` (NivelDificultad)
  - `letrasAcertadas` (`ArrayList<Character>`)
  - `letrasFalladas` (`ArrayList<Character>`)
  - `intentosRestantes` (int), `pistasUsadas` (int)

  Métodos públicos:
  - `intentarLetra(char) → ResultadoIntento` (enum: ACIERTO, FALLO, YA_USADA)
  - `usarPista() → String` — devuelve el texto de la siguiente pista o lanza estado
    "sin pistas". El número de pista usada determina el contenido (ver 4.3).
  - `getPalabraVisible() → String` — ej: `_ A _ A` (espacios entre caracteres).
  - `estaGanada()`, `estaPerdida()`, `estaTerminada()`.
  - Getters de intentos restantes, errores cometidos (0–7 para el dibujo), pistas
    restantes, letras falladas.

### 4.3 Sistema de 3 pistas

Gestionado dentro de `Partida.usarPista()`. El contador `pistasUsadas` decide qué
pista entregar (limitado por `nivel.getPistasDisponibles()`):

1. **Pista 1:** la categoría de la palabra.
2. **Pista 2:** revela una letra aleatoria aún no descubierta (se añade a acertadas).
3. **Pista 3:** la pista escrita (`pistaTexto`) que viene en el archivo.

---

## 5. Vista (Swing) y controlador

### 5.1 Pantallas

**`PantallaInicio`** — título, instrucción breve, 3 botones de dificultad
(Fácil / Medio / Difícil), botón Salir.

**`PantallaJuego`** — `BorderLayout`:
- Centro-izquierda: `PanelAhorcado` (dibujo Java2D).
- Arriba: categoría (oculta hasta usar pista 1), intentos restantes, contador de pistas.
- Centro-derecha: palabra como guiones `_ _ _ _`; lista de letras falladas.
- Abajo: teclado en pantalla A–Z + Ñ (27 botones). Al pulsar uno: se deshabilita;
  verde si acierta, rojo si falla.
- Lateral: 3 botones de pista; se deshabilitan al usarse o si el nivel no las permite.

**`PantallaFin`** — mensaje "¡Ganaste!" / "Perdiste", palabra correcta revelada,
botones "Jugar de nuevo" (→ PantallaInicio) y "Salir".

### 5.2 `PanelAhorcado`

`JPanel` que sobreescribe `paintComponent(Graphics g)`. Usa Java2D
(`Graphics2D`, antialiasing). Recibe el número de errores (0–7) vía setter +
`repaint()`. Dibuja:
- Siempre: la horca (base, poste, viga, cuerda corta).
- Incrementalmente según errores: **1=cabeza, 2=torso, 3=brazo derecho,
  4=brazo izquierdo, 5=pierna derecha, 6=pierna izquierda, 7=cuerda final**
  (el ahorcado completo = derrota).

### 5.3 `ControladorJuego`

- Construye `Partida` con el `NivelDificultad` elegido en la pantalla inicio.
- Registra los `ActionListener` de: cada letra del teclado, los 3 botones de pista,
  "jugar de nuevo".
- Tras cada acción: actualiza palabra visible, estado del teclado, `PanelAhorcado`,
  contadores; luego consulta `estaGanada()` / `estaPerdida()` y, si terminó, cambia a
  `PantallaFin`.
- Único punto de contacto vista ↔ modelo.

### 5.4 Manejo de errores

- `palabras.txt` ausente o sin palabras válidas → `JOptionPane` con mensaje claro y
  cierre limpio (sin stack trace al usuario).
- Líneas mal formadas en el archivo → se saltan, no abortan la carga.
- El `main` envuelve el arranque en `try/catch` de `BancoPalabrasException`.

---

## 6. Banco de palabras (`recursos/palabras.txt`)

- Formato por línea: `categoria;palabra;pista`
  - Ejemplo: `ANIMALES;elefante;Es el mamífero terrestre más grande`
- 200 líneas válidas: **40 por cada una de las 5 categorías**
  (ANIMALES, PAISES, DEPORTES, OBJETOS, COMIDAS).
- Codificación UTF-8. Las palabras pueden tener tildes/Ñ; la comparación de letras
  se normaliza para que el jugador no tenga que escribir acentos.
- Distribución de longitudes pensada para que cada nivel tenga suficientes palabras:
  cada categoría incluye palabras cortas (3-5), medias (6-8) y largas (9+).

---

## 7. Pruebas

`pruebas/PruebasPartida.java` — clase con `main` (sin framework externo). Verifica la
lógica pura e imprime ✓/✗ por caso:

- Acertar una letra presente.
- Fallar una letra ausente (decrementa intentos).
- Letra ya usada (no penaliza dos veces).
- Ganar al completar la palabra.
- Perder al agotar los intentos del nivel.
- Las 3 pistas devuelven el contenido correcto y respetan el límite del nivel.
- `BancoPalabras` carga el archivo y filtra por nivel correctamente.
- Polimorfismo: cada `NivelDificultad` reporta sus intentos/pistas esperados.

Es rápido de ejecutar y mostrar en el video, y prueba el modelo sin depender de la UI.

---

## 8. Entregables

| Entregable | Responsable | Estado |
|---|---|---|
| Código fuente organizado y comentado (Javadoc) | Implementación | Lo genero |
| Banco 200 palabras / 5 categorías | Implementación | Lo genero |
| Diagrama de clases (Mermaid + PlantUML en `docs/`) | Implementación | Lo genero; el equipo lo exporta a imagen para el PDF |
| Manual de usuario (contenido Markdown en `docs/`) | Implementación + equipo | Genero el contenido; el equipo lo pasa a PDF y agrega el link de YouTube |
| Guion de video (`docs/guion-video.md`) | Implementación | Genero un guion sugerido |
| Video en YouTube | **Equipo** | Fuera de alcance de la implementación |
| Diagrama exportado a imagen | **Equipo** | Fuera de alcance (se entrega el código del diagrama) |
| PDF final del manual | **Equipo** | Fuera de alcance (se entrega el contenido) |
| ZIP final con todo | **Equipo** | Se indica qué incluir |
| Repositorio GitHub (`ahorcado-java-swing`, público) | Implementación | Se crea tras el esqueleto; primer commit con estructura real. Compañeros se agregan como colaboradores |

### Fuera de alcance (explícito)

La implementación **no** graba/edita/sube el video, **no** genera el PDF final, y
**no** exporta el diagrama a imagen. Para esos tres entregables se entrega el insumo
listo (contenido del manual, código del diagrama, guion del video) y el equipo los
finaliza.

---

## 9. Decisiones de diseño registradas

1. **Herencia vía niveles de dificultad** (no jerarquía de pistas ni modos de juego):
   demostración de POO natural y fácil de explicar en el video.
2. **Banco en archivo de texto** (no hardcoded ni JSON): demuestra I/O + ArrayList,
   editable sin recompilar, sin librerías externas.
3. **Dibujo Java2D** (no imágenes PNG ni ASCII): profesional, sin dependencias, suma
   al criterio de interfaz atractiva.
4. **Teclado en pantalla A–Z** (no campo de texto): más intuitivo, evita validar
   entradas inválidas, se ve pulido.
5. **Sin JUnit**: clase de pruebas con `main` propia para no complicar el empaquetado
   académico.
6. **Repo git independiente**: el directorio estaba dentro de un repo padre ajeno
   (otro proyecto); se inicializó un repo git propio para aislar el historial.
