# Satélite Zoom Earth — versión 5

Aplicación Android para elegir una vista de Zoom Earth, encuadrarla y usarla como fondo. La
pantalla principal conserva sus controles y permite desplazarse en pantallas pequeñas.

## Compilar en GitHub Actions

Sube el contenido de esta carpeta a la raíz de un repositorio de GitHub. El flujo de trabajo
compila `assembleDebug` y publica el APK como artefacto; también se puede iniciar manualmente
desde la pestaña **Actions**.

Sin secretos de firma configurados, Android genera una firma debug para el APK. Para que una
instalación nueva sustituya a una versión ya instalada, configura en **Settings → Secrets and
variables → Actions** estos secretos:

- `ANDROID_KEYSTORE_BASE64`
- `ANDROID_KEYSTORE_PASSWORD`
- `ANDROID_KEY_ALIAS`
- `ANDROID_KEY_PASSWORD`

Guarda el keystore como secreto en Base64; no lo subas al repositorio ni compartas contraseñas en
el chat. Si necesitas actualizar una instalación firmada anteriormente, usa la misma clave de
firma de esa instalación. Si esa clave ya estuvo en un repositorio público, considera que pudo
quedar expuesta y rota la firma; Android no permite instalar una firma distinta encima de la
instalación anterior.

## Compatibilidad y rendimiento

- Android 6.0 (API 23) o posterior.
- Capturas limitadas a 2560 px en el lado largo y 3 MP para reducir el consumo de memoria.
- La imagen se captura en JPEG de calidad 92 y la espera fija de carga del mapa se redujo de 9 a
  5 segundos; en conexiones lentas, Zoom Earth puede tardar más en completar las teselas.
- El selector de vista, el recorte y la actualización del fondo al desbloquear se mantienen.
- La actualización en segundo plano puede seguir dependiendo de las restricciones de batería
  específicas del fabricante; la app conserva el ajuste para excluirla de esas restricciones.
