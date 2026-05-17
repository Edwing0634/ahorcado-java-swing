# Manual de Usuario — Juego del Ahorcado

> Convertir este documento a PDF para la entrega final.
> Antes de exportar: insertar capturas de pantalla del juego y
> pegar el enlace de YouTube en la sección correspondiente.

## 1. Introducción

El Juego del Ahorcado es un juego clásico donde debes adivinar una
palabra secreta letra por letra antes de que se complete el dibujo
del ahorcado.

## 2. Requisitos e instalación

1. Tener instalado Java (JDK 11 o superior).
2. Descomprimir la carpeta del proyecto.
3. Compilar (ver README) y ejecutar:
   `java -cp "out;recursos" Main`

## 3. Cómo jugar

1. **Pantalla de inicio:** elige un nivel de dificultad:
   - **Fácil:** 8 intentos, palabras cortas, 3 pistas.
   - **Medio:** 7 intentos, palabras medianas, 2 pistas.
   - **Difícil:** 6 intentos, palabras largas, 1 pista.
2. **Adivinar:** haz clic en las letras del teclado en pantalla.
   - Letra correcta → aparece en la palabra (botón verde).
   - Letra incorrecta → se dibuja una parte del muñeco (botón rojo)
     y baja el contador de intentos.
3. **Pistas:** el botón "Usar pista" da, en orden:
   1. La categoría de la palabra.
   2. Una letra revelada al azar.
   3. Una pista escrita relacionada.
4. **Fin de la partida:**
   - Ganas si completas la palabra antes de agotar los intentos.
   - Pierdes si el muñeco se completa (7 errores).
   - Puedes volver a jugar o salir.

## 4. Partes del ahorcado

Cada error dibuja una parte, en este orden: cabeza, torso, brazo
derecho, brazo izquierdo, pierna derecha, pierna izquierda y la
cuerda final.

## 5. Capturas de pantalla

[Insertar aquí capturas: pantalla de inicio, juego en curso,
pantalla de victoria y de derrota]

## 6. Video de presentación

Enlace de YouTube: [pegar aquí el enlace]

## 7. Créditos

Desarrollado por: [nombres del equipo]
Asignatura: Programación Orientada a Objetos.
