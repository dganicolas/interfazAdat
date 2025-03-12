# Documentación de la Pantalla `crearTarea`

Este componente permite a los usuarios crear una nueva tarea. Utiliza Jetpack Compose para la interfaz de usuario y se integra con un `ViewModel` (`TareasViewModel`) para gestionar la lógica de negocio relacionada con la creación de tareas.

---

## Estructura del Componente

### Propiedades y Parámetros

- **`navControlador: NavHostController`**: Controlador de navegación para manejar la navegación entre pantallas.
- **`tareasViewModel: TareasViewModel`**: ViewModel que gestiona la lógica de creación de tareas.
- **`modifier: Modifier`**: Modificador de diseño para personalizar la apariencia del componente.

---

### Estado del Componente

- **`nombre: String`**: Almacena el nombre de la tarea ingresado por el usuario.
- **`descripcion: String`**: Almacena la descripción de la tarea ingresada por el usuario.
- **`autor: String`**: Almacena el autor de la tarea. Este campo se autocompleta con el nombre del usuario actual y no es editable si el usuario tiene el rol de "user".

---

### Interfaz de Usuario

El componente consta de los siguientes elementos:

1. **Campo de Texto para el Nombre de la Tarea**:
  - **Etiqueta**: "Nombre de la tarea".
  - **Valor**: `nombre`.
  - **Acción**: Actualiza el estado `nombre` cuando el usuario escribe.

2. **Campo de Texto para la Descripción de la Tarea**:
  - **Etiqueta**: "Descripción de la tarea".
  - **Valor**: `descripcion`.
  - **Acción**: Actualiza el estado `descripcion` cuando el usuario escribe.

3. **Campo de Texto para el Autor de la Tarea**:
  - **Etiqueta**: "Autor de la tarea".
  - **Valor**: `autor`.
  - **Acción**: Este campo se autocompleta con el nombre del usuario actual. Solo es editable si el usuario tiene el rol de "admin".

4. **Botón "Crear Tarea"**:
  - **Acción**: Crea una nueva tarea si los campos `nombre`, `descripcion` y `autor` no están vacíos.
  - **Habilitación**: Solo está habilitado si todos los campos requeridos están completos.
  - **Lógica**: Llama a `tareasViewModel.crearTarea()` para enviar la nueva tarea a la API.

5. **Botón "Volver"**:
  - **Acción**: Navega de vuelta a la pantalla principal (`menu`).

---

## Lógica de Negocio

### Creación de una Tarea

1. **Validación de Campos**:
  - El botón "Crear tarea" solo está habilitado si los campos `nombre`, `descripcion` y `autor` no están vacíos.

2. **Creación del Objeto `CreateTaskDto`**:
  - Se crea un objeto `CreateTaskDto` con los valores ingresados por el usuario.

3. **Llamada al ViewModel**:
  - Se llama a `tareasViewModel.crearTarea()` para enviar la nueva tarea a la API.
  - Parámetros:
    - `nuevaTarea`: Objeto `CreateTaskDto` con los datos de la tarea.
    - `context`: Contexto actual para mostrar mensajes de retroalimentación (por ejemplo, Toast).
    - `navControlador`: Controlador de navegación para redirigir al usuario después de crear la tarea.

---

## Ejemplo de Uso

```kotlin
@Composable
fun PantallaCrearTarea(navControlador: NavHostController, tareasViewModel: TareasViewModel) {
    crearTarea(
        navControlador = navControlador,
        tareasViewModel = tareasViewModel,
        modifier = Modifier.fillMaxSize()
    )
}
```
# Documentación de la Pantalla `darDeAltaScreen`

Este componente permite a los usuarios registrarse en la aplicación. Utiliza Jetpack Compose para la interfaz de usuario y se integra con un `ViewModel` (`UsuariosViewModel`) para gestionar la lógica de negocio relacionada con el registro de usuarios.

---

## Estructura del Componente

### Propiedades y Parámetros

- **`navControlador: NavHostController`**: Controlador de navegación para manejar la navegación entre pantallas.
- **`usuariosViewmodel: UsuariosViewModel`**: ViewModel que gestiona la lógica de registro de usuarios.

---

### Estado del Componente

- **`username: String`**: Almacena el nombre de usuario ingresado por el usuario.
- **`email: String`**: Almacena el correo electrónico ingresado por el usuario.
- **`password: String`**: Almacena la contraseña ingresada por el usuario.
- **`passwordRepeat: String`**: Almacena la repetición de la contraseña ingresada por el usuario.
- **`calle: String`**: Almacena la calle de la dirección ingresada por el usuario.
- **`numero: String`**: Almacena el número de la dirección ingresada por el usuario.
- **`cp: String`**: Almacena el código postal de la dirección ingresada por el usuario.
- **`ciudad: String`**: Almacena la ciudad de la dirección ingresada por el usuario.
- **`municipio: String`**: Almacena el municipio de la dirección ingresada por el usuario.
- **`provincia: String`**: Almacena la provincia de la dirección ingresada por el usuario.
- **`cargando: Boolean`**: Indica si se está realizando una operación de carga (por ejemplo, durante el registro).

---

### Interfaz de Usuario

El componente consta de los siguientes elementos:

1. **Mensaje de Bienvenida**:
   - **Texto**: "Bienvenido a registro\n por favor complete los campos".

2. **Campo de Texto para el Nombre de Usuario**:
   - **Etiqueta**: "username".
   - **Valor**: `username`.
   - **Validación**: El campo debe tener más de 3 caracteres.

3. **Campo de Texto para el Correo Electrónico**:
   - **Etiqueta**: "email".
   - **Valor**: `email`.
   - **Validación**: El campo debe ser un correo electrónico válido.

4. **Campo de Texto para la Contraseña**:
   - **Etiqueta**: "password".
   - **Valor**: `password`.
   - **Validación**: El campo debe tener más de 6 caracteres.

5. **Campo de Texto para Repetir la Contraseña**:
   - **Etiqueta**: "passwordRepeat".
   - **Valor**: `passwordRepeat`.
   - **Validación**: El campo debe tener más de 6 caracteres.

6. **Campos de Texto para la Dirección**:
   - **Calle**: Almacena la calle de la dirección.
   - **Código Postal**: Almacena el código postal de la dirección.
   - **Número**: Almacena el número de la dirección.
   - **Ciudad**: Almacena la ciudad de la dirección.
   - **Municipio**: Almacena el municipio de la dirección.
   - **Provincia**: Almacena la provincia de la dirección.

7. **Indicador de Carga**:
   - **CircularProgressIndicator**: Se muestra mientras se realiza una operación de carga.

8. **Botón "Registrarse"**:
   - **Acción**: Registra al usuario si todos los campos requeridos están completos y son válidos.
   - **Habilitación**: Solo está habilitado si no se está realizando una operación de carga (`cargando = false`).
   - **Lógica**: Llama a `usuariosViewmodel.darDeAlta()` para enviar los datos del usuario a la API.

9. **Botón "Volver"**:
   - **Acción**: Navega de vuelta a la pantalla principal (`menu`).

---

## Lógica de Negocio

### Registro de un Usuario

1. **Validación de Campos**:
   - El botón "Registrarse" solo está habilitado si todos los campos requeridos están completos y son válidos.

2. **Creación del Objeto `UsuarioRegisterDTO`**:
   - Se crea un objeto `UsuarioRegisterDTO` con los valores ingresados por el usuario.

3. **Llamada al ViewModel**:
   - Se llama a `usuariosViewmodel.darDeAlta()` para enviar los datos del usuario a la API.
   - Parámetros:
     - `usuario`: Objeto `UsuarioRegisterDTO` con los datos del usuario.
     - `context`: Contexto actual para mostrar mensajes de retroalimentación (por ejemplo, Toast).

4. **Indicador de Carga**:
   - Mientras se realiza la operación de registro, se muestra un indicador de carga (`CircularProgressIndicator`).

---

## Ejemplo de Uso

```kotlin
@Composable
fun PantallaRegistro(navControlador: NavHostController) {
    val usuariosViewmodel: UsuariosViewModel = viewModel()
    darDeAltaScreen(
        navControlador = navControlador,
        usuariosViewmodel = usuariosViewmodel
    )
}
```

# Documentación de la Pantalla `listarUsuarios`

Este componente permite listar y gestionar usuarios registrados en la aplicación. Utiliza Jetpack Compose para la interfaz de usuario y se integra con un `ViewModel` (`UsuariosViewModel`) para gestionar la lógica de negocio relacionada con la obtención y eliminación de usuarios.

---

## Estructura del Componente

### Propiedades y Parámetros

- **`navControlador: NavHostController`**: Controlador de navegación para manejar la navegación entre pantallas.
- **`usuariosViewmodel: UsuariosViewModel`**: ViewModel que gestiona la lógica de obtención y eliminación de usuarios.
- **`modifier: Modifier`**: Modificador de diseño para personalizar la apariencia del componente.

---

### Estado del Componente

- **`usuarios: State<List<UsuarioDTO>>`**: Almacena la lista de usuarios obtenida desde el ViewModel.
- **`mostrarDialogo: Boolean`**: Controla la visibilidad del diálogo de confirmación para eliminar un usuario.

---

### Interfaz de Usuario

El componente consta de los siguientes elementos:

1. **Indicador de Carga**:
  - **CircularProgressIndicator**: Se muestra mientras se cargan los usuarios.

2. **Botón "Volver"**:
  - **Acción**: Navega de vuelta a la pantalla principal (`menu`).

3. **Mensaje de Lista Vacía**:
  - **Texto**: "No hay usuarios disponibles." (Se muestra si no hay usuarios en la lista).

4. **Lista de Usuarios**:
  - **LazyColumn**: Muestra una lista de usuarios utilizando el componente `UsuarioItem`.

5. **Componente `UsuarioItem`**:
  - **Card**: Contiene la información de un usuario.
    - **Nombre de Usuario**: Muestra el nombre del usuario.
    - **Correo Electrónico**: Muestra el correo electrónico del usuario.
    - **Rol**: Muestra el rol del usuario.
  - **Botón "Eliminar"**:
    - **Acción**: Muestra un diálogo de confirmación para eliminar al usuario.
  - **Diálogo de Confirmación**:
    - **Título**: "Confirmar eliminación".
    - **Mensaje**: "¿Estás seguro de eliminar al usuario [nombre]?".
    - **Botones**:
      - **Eliminar**: Llama a la función `onClick` para eliminar al usuario.
      - **Cancelar**: Cierra el diálogo sin realizar ninguna acción.

---

## Lógica de Negocio

### Obtención de Usuarios

1. **Carga Inicial**:
  - Al iniciar la pantalla, se llama a `usuariosViewmodel.listarUsuarios(context)` para obtener la lista de usuarios desde la API.

2. **Indicador de Carga**:
  - Mientras se realiza la operación de carga, se muestra un indicador de carga (`CircularProgressIndicator`).

3. **Manejo de Lista Vacía**:
  - Si no hay usuarios disponibles, se muestra un mensaje indicando que no hay usuarios.

### Eliminación de Usuarios

1. **Diálogo de Confirmación**:
  - Al hacer clic en el botón "Eliminar", se muestra un diálogo de confirmación.

2. **Llamada al ViewModel**:
  - Si se confirma la eliminación, se llama a `usuariosViewmodel.eliminarusuario(context, username)` para eliminar al usuario de la API.

---

## Ejemplo de Uso

```kotlin
@Composable
fun PantallaListarUsuarios(navControlador: NavHostController) {
    val usuariosViewmodel: UsuariosViewModel = viewModel()
    listarUsuarios(
        navControlador = navControlador,
        usuariosViewmodel = usuariosViewmodel,
        modifier = Modifier.fillMaxSize()
    )
}
```

# Documentación de la Pantalla `Login`

Este componente permite a los usuarios iniciar sesión en la aplicación. Utiliza Jetpack Compose para la interfaz de usuario y se integra con Retrofit para realizar llamadas a la API y autenticar al usuario.

---

## Estructura del Componente

### Propiedades y Parámetros

- **`navControlador: NavHostController`**: Controlador de navegación para manejar la navegación entre pantallas.

---

### Estado del Componente

- **`username: String`**: Almacena el nombre de usuario ingresado por el usuario.
- **`password: String`**: Almacena la contraseña ingresada por el usuario.
- **`titulo: String`**: Almacena un título o mensaje de estado (no se utiliza en el código actual).
- **`cargando: Boolean`**: Indica si se está realizando una operación de carga (por ejemplo, durante el inicio de sesión).
- **`estadoBoton: Boolean`**: Controla el estado de los botones (habilitado/deshabilitado) durante el inicio de sesión.

---

### Interfaz de Usuario

El componente consta de los siguientes elementos:

1. **Imagen de Fondo**:
  - **Image**: Muestra una imagen de fondo relacionada con el tema de la aplicación.

2. **Indicador de Carga**:
  - **CircularProgressIndicator**: Se muestra mientras se realiza la operación de inicio de sesión.

3. **Título**:
  - **Texto**: "Entrar a la app".

4. **Campo de Texto para el Nombre de Usuario**:
  - **Etiqueta**: "username".
  - **Validación**: El campo debe tener más de 3 caracteres.

5. **Campo de Texto para la Contraseña**:
  - **Etiqueta**: "contraseña".
  - **Validación**: El campo debe tener al menos 6 caracteres.

6. **Botón "Iniciar Sesión"**:
  - **Acción**: Realiza el inicio de sesión si los campos `username` y `password` no están vacíos.
  - **Habilitación**: Solo está habilitado si los campos requeridos están completos y no se está realizando una operación de carga.
  - **Lógica**:
    - Llama a la API para autenticar al usuario.
    - Almacena el token, nombre de usuario, contraseña y rol del usuario en la clase `Usuario`.
    - Navega a la pantalla principal (`menu`) si el inicio de sesión es exitoso.
    - Muestra un mensaje de error si el inicio de sesión falla.

7. **Botón "Registrarse"**:
  - **Acción**: Navega a la pantalla de registro (`registrarse`).
  - **Habilitación**: Solo está habilitado si no se está realizando una operación de carga.

---

## Lógica de Negocio

### Inicio de Sesión

1. **Validación de Campos**:
  - El botón "Iniciar Sesión" solo está habilitado si los campos `username` y `password` no están vacíos.

2. **Llamada a la API**:
  - Se llama a `RetrofitClient.instance.loginUsuario()` para autenticar al usuario.
  - Parámetros:
    - `LoginUsuarioDTO`: Objeto que contiene el nombre de usuario y la contraseña.

3. **Almacenamiento de Datos**:
  - Si el inicio de sesión es exitoso, se almacenan los siguientes datos en la clase `Usuario`:
    - **Token**: Token de autenticación.
    - **Nombre**: Nombre de usuario.
    - **Contraseña**: Contraseña del usuario.
    - **Email**: Correo electrónico del usuario (obtenido mediante una llamada adicional a la API).
    - **Rol**: Rol del usuario (admin o user).

4. **Navegación**:
  - Si el inicio de sesión es exitoso, se navega a la pantalla principal (`menu`).

5. **Manejo de Errores**:
  - Si el inicio de sesión falla, se muestra un mensaje de error utilizando `Toast`.

---

## Ejemplo de Uso

```kotlin
@Composable
fun PantallaLogin(navControlador: NavHostController) {
    Login(navControlador = navControlador)
}
```

# Documentación de la Pantalla `Menu` y Componentes Relacionados

Este módulo permite gestionar tareas en una aplicación Android utilizando Jetpack Compose para la interfaz de usuario y un `ViewModel` (`TareasViewModel`) para gestionar la lógica de negocio relacionada con las tareas.

---

## Estructura del Proyecto

### Clases y Componentes Principales

1. **`Menu` (Composable)**
  - **Descripción**: Pantalla principal que muestra una lista de tareas.
  - **Funcionalidades**:
    - Carga las tareas desde una API.
    - Muestra un indicador de carga mientras se obtienen las tareas.
    - Muestra un mensaje de error si la carga falla.
    - Permite navegar a otras pantallas (alta, crear tareas, usuarios, admin).
  - **Dependencias**:
    - `AppBarConMenu`: Barra de aplicación con menú desplegable.
    - `TareaItem`: Componente que representa una tarea individual.

2. **`TareaItem` (Composable)**
  - **Descripción**: Componente que representa una tarea individual en la lista.
  - **Funcionalidades**:
    - Muestra el nombre, descripción, autor y estado de la tarea.
    - Permite eliminar, editar y marcar la tarea como completada.
    - Muestra diálogos de confirmación para eliminar y editar tareas.
  - **Dependencias**:
    - `ConfirmDialog`: Diálogo de confirmación para eliminar tareas.
    - `AlertDialog`: Diálogo para editar tareas.

3. **`ConfirmDialog` (Composable)**
  - **Descripción**: Diálogo de confirmación para eliminar una tarea.
  - **Funcionalidades**:
    - Muestra un mensaje de confirmación antes de eliminar la tarea.
    - Permite confirmar o cancelar la eliminación.

4. **`TareasViewModel` (ViewModel)**
  - **Descripción**: ViewModel que gestiona la lógica de negocio relacionada con las tareas.
  - **Funcionalidades**:
    - Carga las tareas desde la API.
    - Elimina, actualiza y marca tareas como completadas.
    - Gestiona el estado de carga y los mensajes de error.

5. **`Tarea` (Modelo)**
  - **Descripción**: Modelo de datos que representa una tarea.
  - **Propiedades**:
    - `_id`: Identificador único de la tarea.
    - `nombre`: Nombre de la tarea.
    - `descripcion`: Descripción de la tarea.
    - `estado`: Estado de la tarea (completada o no).
    - `autor`: Autor de la tarea.

---

## Flujo de Trabajo

1. **Carga de Tareas**:
  - Al iniciar la pantalla `Menu`, se llama a `cargarTareas()` para obtener las tareas desde la API.
  - Si la carga es exitosa, se muestra la lista de tareas.
  - Si hay un error, se muestra un mensaje de error y un botón para reintentar.

2. **Eliminación de Tareas**:
  - Al hacer clic en el ícono de eliminar en una tarea, se muestra un diálogo de confirmación.
  - Si se confirma, se llama a `eliminarTarea()` para eliminar la tarea de la API.

3. **Edición de Tareas**:
  - Al hacer clic en el botón "Editar tarea", se muestra un diálogo con campos editables.
  - Si se guardan los cambios, se llama a `actualizarTarea()` para actualizar la tarea en la API.

4. **Marcar Tarea como Completada**:
  - Al cambiar el estado del checkbox, se llama a `completarTarea()` para actualizar el estado de la tarea en la API.

---

## Dependencias

- **Jetpack Compose**: Para la construcción de la interfaz de usuario.
- **ViewModel**: Para gestionar el estado de la aplicación.
- **Coroutines**: Para manejar operaciones asíncronas.

---

## Ejemplo de Uso

### Pantalla Principal (`Menu`)

```kotlin
@Composable
fun Menu(navControlador: NavHostController, tareasViewModel: TareasViewModel) {
    // Lógica para cargar y mostrar tareas
}
```
# Documentación de la Pantalla `miZona`

Este componente permite a los usuarios gestionar su información personal, como el correo electrónico y la contraseña, y también les brinda la opción de eliminar su cuenta. Utiliza Jetpack Compose para la interfaz de usuario y se integra con un `ViewModel` (`UsuariosViewModel`) para gestionar la lógica de negocio relacionada con la actualización y eliminación de cuentas.

---

## Estructura del Componente

### Propiedades y Parámetros

- **`navControlador: NavHostController`**: Controlador de navegación para manejar la navegación entre pantallas.
- **`usuariosViewmodel: UsuariosViewModel`**: ViewModel que gestiona la lógica de actualización y eliminación de cuentas.
- **`modifier: Modifier`**: Modificador de diseño para personalizar la apariencia del componente.

---

### Estado del Componente

- **`email: String`**: Almacena el correo electrónico del usuario.
- **`contrasena: String`**: Almacena la contraseña del usuario.
- **`estadoventana: Boolean`**: Controla la visibilidad del diálogo de confirmación para eliminar la cuenta.
- **`cargando: Boolean`**: Indica si se está realizando una operación de carga (por ejemplo, durante la actualización o eliminación de la cuenta).

---

### Interfaz de Usuario

El componente consta de los siguientes elementos:

1. **Indicador de Carga**:
  - **CircularProgressIndicator**: Se muestra mientras se realiza una operación de carga.

2. **Diálogo de Confirmación**:
  - **AlertDialog**: Muestra un diálogo de confirmación para eliminar la cuenta.
    - **Título**: "¿Confirmar eliminación?".
    - **Mensaje**: "¿Seguro que quieres eliminar esta tarea?".
    - **Botones**:
      - **Eliminar**: Llama a `usuariosViewmodel.eliminarCuenta()` para eliminar la cuenta.
      - **Cancelar**: Cierra el diálogo sin realizar ninguna acción.

3. **Botón "Volver"**:
  - **Acción**: Navega de vuelta a la pantalla anterior utilizando `navControlador.popBackStack()`.

4. **Título**:
  - **Texto**: "Mi Zona".

5. **Información del Usuario**:
  - **Nombre**: Muestra el nombre del usuario (`Usuario.nombre`).
  - **Rol**: Muestra si el usuario es "administrador" o "usuario" (`Usuario.rol`).

6. **Campo de Texto para el Correo Electrónico**:
  - **Etiqueta**: "Email".
  - **Valor**: `email`.
  - **Acción**: Actualiza el estado `email` cuando el usuario escribe.

7. **Campo de Texto para la Contraseña**:
  - **Etiqueta**: "Contraseña".
  - **Valor**: `contrasena`.
  - **Acción**: Actualiza el estado `contrasena` cuando el usuario escribe.

8. **Botón "Guardar Cambios"**:
  - **Acción**: Llama a `usuariosViewmodel.actualizarUsuario()` para actualizar el correo electrónico y la contraseña del usuario.
  - **Habilitación**: Solo está habilitado si los campos `email` y `contrasena` no están vacíos.

9. **Botón "Eliminar Cuenta"**:
  - **Acción**: Muestra el diálogo de confirmación para eliminar la cuenta.
  - **Color**: Rojo, para indicar una acción crítica.

---

## Lógica de Negocio

### Actualización de la Información del Usuario

1. **Validación de Campos**:
  - El botón "Guardar Cambios" solo está habilitado si los campos `email` y `contrasena` no están vacíos.

2. **Llamada al ViewModel**:
  - Se llama a `usuariosViewmodel.actualizarUsuario()` para actualizar el correo electrónico y la contraseña del usuario en la API.

### Eliminación de la Cuenta

1. **Diálogo de Confirmación**:
  - Al hacer clic en el botón "Eliminar Cuenta", se muestra un diálogo de confirmación.

2. **Llamada al ViewModel**:
  - Si se confirma la eliminación, se llama a `usuariosViewmodel.eliminarCuenta()` para eliminar la cuenta del usuario de la API.

---

## Ejemplo de Uso

```kotlin
@Composable
fun PantallaMiZona(navControlador: NavHostController) {
    val usuariosViewmodel: UsuariosViewModel = viewModel()
    miZona(
        navControlador = navControlador,
        usuariosViewmodel = usuariosViewmodel,
        modifier = Modifier.fillMaxSize()
    )
}
```
# Documentación de la Pantalla `Registrarse`

Este componente permite a los usuarios registrarse en la aplicación. Utiliza Jetpack Compose para la interfaz de usuario y se integra con Retrofit para realizar llamadas a la API y registrar al usuario.

---

## Estructura del Componente

### Propiedades y Parámetros

- **`navControlador: NavHostController`**: Controlador de navegación para manejar la navegación entre pantallas.
- **`usuarioViewModel: UsuariosViewModel`**: ViewModel que gestiona la lógica de registro de usuarios.

---

### Estado del Componente

- **`username: String`**: Almacena el nombre de usuario ingresado por el usuario.
- **`email: String`**: Almacena el correo electrónico ingresado por el usuario.
- **`password: String`**: Almacena la contraseña ingresada por el usuario.
- **`passwordRepeat: String`**: Almacena la repetición de la contraseña ingresada por el usuario.
- **`calle: String`**: Almacena la calle de la dirección ingresada por el usuario.
- **`numero: String`**: Almacena el número de la dirección ingresada por el usuario.
- **`cp: String`**: Almacena el código postal de la dirección ingresada por el usuario.
- **`ciudad: String`**: Almacena la ciudad de la dirección ingresada por el usuario.
- **`municipio: String`**: Almacena el municipio de la dirección ingresada por el usuario.
- **`provincia: String`**: Almacena la provincia de la dirección ingresada por el usuario.
- **`titulo: String`**: Almacena el título de los mensajes de retroalimentación.
- **`mensaje: String`**: Almacena el mensaje de retroalimentación.
- **`cargando: Boolean`**: Indica si se está realizando una operación de carga (por ejemplo, durante el registro).
- **`estadoventana: Boolean`**: Controla la visibilidad del diálogo de retroalimentación en caso de error.
- **`estadoVentanaCorrecta: Boolean`**: Controla la visibilidad del diálogo de retroalimentación en caso de éxito.

---

### Interfaz de Usuario

El componente consta de los siguientes elementos:

1. **Mensaje de Bienvenida**:
  - **Texto**: "Bienvenido a registro\n por favor complete los campos".

2. **Campo de Texto para el Nombre de Usuario**:
  - **Etiqueta**: "username".
  - **Validación**: El campo debe tener más de 3 caracteres.

3. **Campo de Texto para el Correo Electrónico**:
  - **Etiqueta**: "email".
  - **Validación**: El campo debe ser un correo electrónico válido.

4. **Campo de Texto para la Contraseña**:
  - **Etiqueta**: "password".
  - **Validación**: El campo debe tener más de 6 caracteres.

5. **Campo de Texto para Repetir la Contraseña**:
  - **Etiqueta**: "passwordRepeat".
  - **Validación**: El campo debe tener más de 6 caracteres.

6. **Campos de Texto para la Dirección**:
  - **Calle**: Almacena la calle de la dirección.
  - **Código Postal**: Almacena el código postal de la dirección.
  - **Número**: Almacena el número de la dirección.
  - **Ciudad**: Almacena la ciudad de la dirección.
  - **Municipio**: Almacena el municipio de la dirección.
  - **Provincia**: Almacena la provincia de la dirección.

7. **Indicador de Carga**:
  - **CircularProgressIndicator**: Se muestra mientras se realiza la operación de registro.

8. **Botón "Registrarse"**:
  - **Acción**: Registra al usuario si todos los campos requeridos están completos y son válidos.
  - **Habilitación**: Solo está habilitado si no se está realizando una operación de carga y todos los campos están completos.
  - **Lógica**:
    - Llama a la API para registrar al usuario.
    - Muestra un mensaje de éxito o error utilizando el componente `ventana`.

9. **Botón "Logearse"**:
  - **Acción**: Navega a la pantalla de inicio de sesión (`login`).
  - **Habilitación**: Solo está habilitado si no se está realizando una operación de carga.

---

## Lógica de Negocio

### Registro de un Usuario

1. **Validación de Campos**:
  - El botón "Registrarse" solo está habilitado si todos los campos requeridos están completos y son válidos.

2. **Llamada a la API**:
  - Se llama a `RetrofitClient.instance.registrarUsuario()` para registrar al usuario.
  - Parámetros:
    - `UsuarioRegisterDTO`: Objeto que contiene los datos del usuario, incluyendo la dirección.

3. **Manejo de Respuesta**:
  - Si el registro es exitoso, se muestra un mensaje de éxito y se redirige al usuario a la pantalla de inicio de sesión (`login`).
  - Si el registro falla, se muestra un mensaje de error.

4. **Indicador de Carga**:
  - Mientras se realiza la operación de registro, se muestra un indicador de carga para evitar que el usuario realice múltiples registros simultáneos.

---

## Ejemplo de Uso

```kotlin
@Composable
fun PantallaRegistro(navControlador: NavHostController) {
    val usuarioViewModel: UsuariosViewModel = viewModel()
    Registrarse(
        navControlador = navControlador,
        usuarioViewModel = usuarioViewModel
    )
}
```


