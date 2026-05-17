# Diseño — README futurista del Juego del Ahorcado

**Fecha:** 2026-05-17
**Alcance:** rediseñar `README.md` (un solo archivo, presentación). No toca código.

## Objetivo

Convertir el README plano actual en uno visualmente "top / futurista": banners
SVG animados (servicios externos), efecto typing, badges neon, emojis, tablas.
El contenido técnico (compilar, ejecutar, POO) debe quedar siempre legible aunque
los SVG externos fallen. Idioma: español, tono llamativo. Lo evalúa el profesor
(rúbrica, criterio documentación) y sirve de portafolio.

## Decisiones registradas

- **Animaciones:** GitHub bloquea JS/CSS; se usan SVG de terceros vía URL (estándar
  de repos "top"). Servicios: `capsule-render` (banner ondulado animado superior e
  inferior), `readme-typing-svg` (máquina de escribir), `shields.io` (badges
  `for-the-badge`). Todos gratuitos, se renderizan en GitHub sin instalar nada.
- **Paleta:** degradado morado → cian futurista. Hex guía: `8A2BE2` (morado),
  `00E5FF` (cian), fondo oscuro `0D1117` (estilo GitHub dark).
- **Idioma/tono:** español, llamativo con emojis, info técnica clara.
- **Autor:** único — **Edwin González**, Universidad EAN, asignatura POO. (NO equipo;
  reemplaza el placeholder "[Completar con los nombres del equipo]").
- **Riesgo aceptado:** los SVG dependen de servidores externos; si caen, esas
  imágenes no cargan pero el resto del README sí. Riesgo bajo, aceptado por el usuario.

## Estructura del README (orden final)

1. Banner superior animado — `capsule-render` tipo `waving`, degradado morado/cian,
   título "JUEGO DEL AHORCADO", subtítulo "Proyecto POO · Java Swing".
2. Typing effect — `readme-typing-svg` con frases rotando: "🎮 Juego del Ahorcado",
   "☕ Java + Swing", "🎯 POO: herencia, polimorfismo, encapsulación",
   "🏆 200 palabras · 5 categorías · 3 niveles". Colores del SVG en cian.
3. Fila de badges centrada (`for-the-badge`, colores morado/cian/oscuro):
   Java 25 · Swing · POO · ✅ 58 pruebas · 🎯 200 palabras · 🎚️ 3 niveles ·
   📜 Académico · "Made with ☕".
4. Descripción del juego con emojis de categorías (🐶 🌍 ⚽ 🛠️ 🍕).
5. 🖼️ Capturas / Demo — placeholders marcados (inicio, juego, victoria, derrota,
   GIF de demo) con instrucción HTML-comment para que el usuario los reemplace.
6. ✨ Características — tabla emoji: 3 niveles, 3 pistas (categoría/letra/texto),
   200 palabras, 5 categorías, dibujo Java2D, teclado A-Z+Ñ en pantalla, sin
   dependencias externas.
7. 🎚️ Niveles — tabla: Fácil 🟢 (8 intentos / 3 pistas / 3-5 letras),
   Medio 🔵 (7 / 2 / 6-8), Difícil 🔴 (6 / 1 / 9+).
8. 🚀 Instalación y ejecución — requisitos (JDK 11+, probado JDK 25) y bloques
   PowerShell: compilar, ejecutar juego (`java -cp "out;recursos" Main`), pruebas
   (`java -cp out PruebasPartida`). Comandos sin cambios respecto al README actual.
9. 🏗️ Arquitectura — estructura de carpetas con emojis (modelo/vista/controlador/
   recursos/pruebas/docs) + los 3 conceptos POO (herencia/polimorfismo con
   `NivelDificultad`, encapsulación, ArrayList). Mantiene la info técnica actual.
10. 👤 Autor — Edwin González · Universidad EAN · Programación Orientada a Objetos.
11. 🎥 Video de presentación — placeholder para enlace de YouTube.
12. Footer animado — `capsule-render` tipo `waving` invertido (cierre), degradado
    morado/cian, texto "Gracias por jugar 🎮".

## Criterios de aceptación

- El README renderiza correctamente en GitHub (markdown válido, SVG por URL).
- Si los SVG externos no cargan, el texto técnico (compilar/ejecutar/POO/autor)
  sigue siendo legible y completo.
- Autor = solo Edwin González (sin placeholder de equipo).
- Comandos de compilar/ejecutar/pruebas idénticos a los verificados (no inventar).
- Las tablas de niveles reflejan los valores reales del modelo
  (Facil=8/3, Medio=7/2, Dificil=6/1) ya verificados en el proyecto.
- Un solo commit, solo `README.md` (+ este spec en docs/).
