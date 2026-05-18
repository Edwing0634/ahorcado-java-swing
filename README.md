<!-- ════════════════════ BANNER SUPERIOR ANIMADO ════════════════════ -->
<div align="center">

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0:8A2BE2,100:00E5FF&height=200&section=header&text=JUEGO%20DEL%20AHORCADO&fontSize=46&fontColor=ffffff&animation=fadeIn&fontAlignY=38&desc=Proyecto%20POO%20%C2%B7%20Java%20Swing&descAlignY=58&descSize=18" alt="banner"/>

<!-- ════════════════════ TYPING EFFECT ════════════════════ -->
<a href="https://github.com/Edwing0634/ahorcado-java-swing">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=24&duration=2800&pause=900&color=00E5FF&center=true&vCenter=true&width=620&lines=%F0%9F%8E%AE+Juego+del+Ahorcado+Interactivo;%E2%98%95+Hecho+en+Java+%2B+Swing;%F0%9F%8E%AF+POO%3A+herencia%2C+polimorfismo%2C+encapsulaci%C3%B3n;%F0%9F%8F%86+200+palabras+%C2%B7+5+categor%C3%ADas+%C2%B7+3+niveles" alt="typing"/>
</a>

<!-- ════════════════════ BADGES NEON ════════════════════ -->
<p>
  <img src="https://img.shields.io/badge/Java-25-8A2BE2?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/GUI-Swing-00E5FF?style=for-the-badge&logo=java&logoColor=white" alt="Swing"/>
  <img src="https://img.shields.io/badge/Paradigma-POO-8A2BE2?style=for-the-badge" alt="POO"/>
  <img src="https://img.shields.io/badge/Pruebas-58%20%E2%9C%85-00E5FF?style=for-the-badge" alt="Pruebas"/>
</p>
<p>
  <img src="https://img.shields.io/badge/Palabras-200%20%F0%9F%8E%AF-8A2BE2?style=for-the-badge" alt="Palabras"/>
  <img src="https://img.shields.io/badge/Niveles-3%20%F0%9F%8E%9A%EF%B8%8F-00E5FF?style=for-the-badge" alt="Niveles"/>
  <img src="https://img.shields.io/badge/Proyecto-Acad%C3%A9mico%20EAN-8A2BE2?style=for-the-badge" alt="Académico"/>
  <img src="https://img.shields.io/badge/Made%20with-%E2%98%95%20%26%20%E2%9D%A4%EF%B8%8F-00E5FF?style=for-the-badge" alt="Made with"/>
</p>

</div>

---

## 🎮 ¿De qué trata?

Un **juego del ahorcado clásico** con interfaz gráfica en **Java Swing**, desarrollado como proyecto de **Programación Orientada a Objetos**. Adivina la palabra secreta letra por letra antes de que se complete el dibujo del ahorcado. 💀

Banco de **200 palabras** repartidas en 5 categorías:

> 🐶 **Animales** &nbsp;·&nbsp; 🌍 **Países** &nbsp;·&nbsp; ⚽ **Deportes** &nbsp;·&nbsp; 🛠️ **Objetos** &nbsp;·&nbsp; 🍕 **Comidas**

Con **3 pistas** disponibles por partida y **dibujo del muñeco animado** con Java2D que se va completando con cada error.

---

## 🖼️ Capturas & Demo

<div align="center">

<!-- 📸 Reemplaza estos textos por tus capturas reales:
     arrastra la imagen aquí en GitHub o usa: ![inicio](docs/capturas/inicio.png) -->

| 🏠 Pantalla de inicio | 🎯 Jugando |
|:---:|:---:|
| _( pega aquí la captura del menú de dificultad )_ | _( pega aquí la captura de una partida en curso )_ |
| 🏆 **Victoria** | 💀 **Derrota** |
| _( pega aquí la captura de "¡GANASTE!" )_ | _( pega aquí la captura de "PERDISTE" + muñeco completo )_ |

🎬 _Opcional: graba un GIF corto jugando y pégalo aquí para un demo animado._

</div>

---

## ✨ Características

| | Característica | Detalle |
|:---:|:---|:---|
| 🎚️ | **3 niveles de dificultad** | Fácil, Medio y Difícil (más intentos/pistas en Fácil) |
| 💡 | **Sistema de 3 pistas** | 1️⃣ categoría · 2️⃣ revelar una letra · 3️⃣ pista escrita |
| 📚 | **Banco de 200 palabras** | 40 por cada una de las 5 categorías |
| 🎨 | **Dibujo Java2D** | El ahorcado se dibuja por partes según los errores (cuerda roja al perder) |
| ⌨️ | **Teclado en pantalla** | Botones A–Z + Ñ; verde al acertar, rojo al fallar |
| 🧩 | **POO pura** | Herencia, polimorfismo y encapsulación reales |
| 📦 | **Sin dependencias** | Solo el JDK — nada que instalar aparte de Java |

---

## 🎚️ Niveles de dificultad

| Nivel | Intentos | Pistas | Longitud de palabra |
|:---|:---:|:---:|:---:|
| 🟢 **Fácil** | 8 | 3 | 3–5 letras |
| 🔵 **Medio** | 7 | 2 | 6–8 letras |
| 🔴 **Difícil** | 6 | 1 | 9+ letras |

---

## 🚀 Instalación y ejecución

### ✅ Requisitos
- **Java JDK 11 o superior** (probado con **JDK 25**). No necesitas nada más.

### 1️⃣ Compilar
Desde la raíz del proyecto, en **PowerShell**:

```powershell
$archivos = Get-ChildItem -Recurse -Filter *.java -Path src,pruebas | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -d out $archivos
```

### 2️⃣ Jugar 🎮

```powershell
java -cp "out;recursos" Main
```

> Se abre la ventana con la pantalla de inicio. Elige dificultad y ¡a jugar!

### 3️⃣ Ejecutar las pruebas 🧪

```powershell
java -cp out PruebasPartida
```

> Corre la batería de pruebas de la lógica del juego (debe mostrar **58 pasadas, 0 fallidas**).

---

## 🏗️ Arquitectura

Diseño en **3 capas** desacopladas:

```
📁 src/
 ├── 📂 modelo/        🧠 Lógica pura del juego (sin Swing)
 ├── 📂 vista/         🎨 Pantallas Swing + dibujo del ahorcado
 └── 📂 controlador/   🔗 Une vista y modelo
📄 recursos/palabras.txt   📚 Banco de 200 palabras
📁 pruebas/                🧪 Pruebas de la lógica
📁 docs/                   📖 Diagrama de clases y manual de usuario
```

### 🧩 Conceptos de POO aplicados

- **🧬 Herencia y polimorfismo** — clase abstracta `NivelDificultad` con subclases `Facil`, `Medio`, `Dificil`; el método `filtrarPalabras()` se comporta distinto según el nivel.
- **🔒 Encapsulación** — atributos privados con acceso por *getters* en todo el modelo.
- **📋 ArrayList** — gestión dinámica del banco de palabras y de las letras acertadas/falladas.

---

## 👤 Autor

<div align="center">

**Edwin González**
🎓 Universidad EAN · 📚 Programación Orientada a Objetos

</div>

---

## 🎥 Video de presentación

<div align="center">

🔗 _[ Pega aquí el enlace de YouTube cuando esté listo ]_

</div>

---

<!-- ════════════════════ FOOTER ANIMADO ════════════════════ -->
<div align="center">

<img width="100%" src="https://capsule-render.vercel.app/api?type=waving&color=0:00E5FF,100:8A2BE2&height=140&section=footer&text=Gracias%20por%20jugar%20%F0%9F%8E%AE&fontSize=24&fontColor=ffffff&animation=fadeIn&fontAlignY=70" alt="footer"/>

</div>
