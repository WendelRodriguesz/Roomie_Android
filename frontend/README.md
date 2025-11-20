# Roomiê - Guia de Organização e Uso (Android/Kotlin)

Arquitetura baseada em **UI/Data** (com **Domain** opcional), **Jetpack Compose** com **UDF** (estado desce, eventos sobem), **MVVM (ViewModel + StateFlow)** e **Navigation for Compose**. Isso facilita testabilidade, escalabilidade e separação de responsabilidades. ([Android Developers][1])

## Como usar este template

1. **Abrir o módulo Android**

    * Se o repo tem outras pastas, anexe o projeto Gradle do app e crie a Run Config para `:app`.
2. **Rodar o app**

    * `MainActivity` inicia o `AppNavHost` com uma rota inicial.
3. **Criar uma nova feature**

    * Use o padrão abaixo (**feature-first**) e registre a rota no `NavHost`.
4. **Seguir UDF + MVVM**

    * **State imutável** na UI, **eventos** sobem para ViewModel, que fala com **Domain/Data** e emite **novo State**. ([Android Developers][2])

---

## Estrutura de pastas

```
app/
  MainActivity.kt                 # Activity raiz injeta o AppNavHost
  navigation/
    AppNavHost.kt                 # NavController + NavHost + Scaffold (BottomBar, etc.)
    Routes.kt / BottomDestinations.kt  # Rotas e itens da bottom bar
  core/
    ui/
      theme/                      # Material3 Theme (cores/tipografia/shapes)
      components/                 # Componentes reutilizáveis (ex.: BottomBar)
    model/                        # Modelos simples compartilhados (DTOs/entidades leves)
    util/                         # Helpers: Result/Error mappers, formatadores, etc.
  feature/
    <nome-da-feature>/
      ui/                         # *Stateless* Composables (Screen) + previews
      presentation/               # ViewModel + State + Event (UDF/MVVM)
      navigation/                 # Funções/extensões para rotas da feature
      data/                       # Repositórios e fontes (API/DB) da feature (quando houver)
      domain/                     # Use cases (opcional; regras reutilizáveis entre telas)
```

**Por que assim?**

* **UI layer** apresenta dados e reage a mudanças; **Data layer** concentra regras e fontes; **Domain** simplifica orquestração e reuso entre UI e Data. Recomendações oficiais indicam ao menos UI/Data e, quando útil, Domain. ([Android Developers][1])

---

## Padrão por tela/feature (UDF + MVVM)

Crie **sempre** estes 5 itens por feature:

1. `XState.kt` - Estado imutável da tela (ex.: campos, loading, erro).
2. `XEvent.kt` - Eventos da UI (ex.: `Submit`, `FieldChanged`).
3. `XViewModel.kt` - Recebe `Event`, chama **Domain/Data**, emite **novo `State`** (via `StateFlow`).
4. `XRoute.kt` - Composable *stateful* que conecta `ViewModel` ↔ `Screen`.
5. `XScreen.kt` - Composable *stateless* com `state` + `onEvent`.

> Compose recomenda **UDF** (estado desce, eventos sobem) e **state hoisting**; `ViewModel` atua como *state holder*. ([Android Developers][2])

Exemplo mínimo (trecho) de assinatura:

```kotlin
// presentation
data class LoginState(val email: String = "", val loading: Boolean = false, val error: String? = null)
sealed interface LoginEvent { data class EmailChanged(val v: String): LoginEvent; data object Submit: LoginEvent }

// ui
@Composable
fun LoginScreen(state: LoginState, onEvent: (LoginEvent) -> Unit) { /*...*/ }
```

---

## Navegação

* Usamos **Navigation for Compose**: `NavController` + `NavHost` + `composable(route)`.
* Cada rota deve ser **string única** e pode ter **args** (ex.: `"listing/{id}"`).
* **Bottom bar**: implemente em `Scaffold` (no `AppNavHost`) e sincronize o item selecionado com a `currentBackStackEntry`. ([Android Developers][3])

Exemplo de registro simples:

```kotlin
NavHost(navController, startDestination = Routes.HOME) {
  composable(Routes.HOME) { HomeRoute() }
  composable(Routes.CHAT) { ChatRoute() }
}
```

---

## Diretrizes de código e nomes

* **Packages**: sempre minúsculos, sem underscore (ex.: `com.example.roomie.app.feature.auth`). ([Android Developers][4])
* **Arquivos/classes**: **PascalCase**; propriedades/funções em **lowerCamelCase** (guia Kotlin). ([Kotlin][5])
* **UI**: separar *stateful* (Route) de *stateless* (Screen). Evite lógica pesada em `Screen`; delegue ao `ViewModel`. ([Android Developers][6])
* **Side-effects**: componíveis devem ser puros; use APIs de efeito (`LaunchedEffect`, etc.) quando necessário. ([Android Developers][7])

---

## O que vai em cada pasta (checklist rápido)

* `app/navigation/`

    * **`AppNavHost.kt`**: `Scaffold` (BottomBar/TopBar), `NavHost` e rotas raiz.
    * **`Routes.kt`**: constantes/selos de rotas; **`BottomDestinations.kt`** para itens da bottom bar.

* `core/ui/theme/`

    * `Color.kt`, `Type.kt`, `Theme.kt` (Material3).

* `core/ui/components/`

    * Componentes reutilizáveis (ex.: `BottomBar`, `RTextField`, `LoadingBox`).

* `core/model/`

    * Modelos **simples** compartilhados (ex.: `Usuario`, `ListingPreview`).

* `core/util/`

    * `Result<T>`, mappers de erro, formatadores (datas/validações), extensões.

* `feature/<nome>/ui/`

    * **`XScreen.kt`** (stateless) + previews.

* `feature/<nome>/presentation/`

    * **`XState.kt`**, **`XEvent.kt`**, **`XViewModel.kt`** (Fluxo UDF via `StateFlow`).

* `feature/<nome>/navigation/`

    * Funções para registrar grafos ou rotas da feature no `NavHost`.

* `feature/<nome>/data/`

    * Repositórios, datasources (Retrofit/Room/Cache), mapeamento DTO→Model.

* `feature/<nome>/domain/` (quando útil)

    * **Use cases** coesos e reutilizáveis; called pela ViewModel. ([Android Developers][1])

---

## Modularização (quando crescer)

* Evolua para **multi-módulo Gradle**: `:app`, `:core:ui`, `:core:model`, `:feature:auth`, `:feature:listings`, etc.
* Benefícios: builds paralelos/mais rápidos, isolamento de dependências, reuso.
* Siga os **guias oficiais** de modularização e padrões comuns; use o **Now in Android** como referência prática. ([Android Developers][8])

---

## Boas práticas essenciais

* **Minimizar estado** na UI; **hoistar** estado para quem precisa controlá-lo. ([Android Developers][2])
* **Uma fonte de verdade** para o estado da tela (geralmente a `ViewModel`). ([Android Developers][6])
* **Side-effects controlados** (ex.: toasts, navegação, chamadas I/O) - nunca confie em ordem de recomposição. ([Android Developers][7])
* **Rotas previsíveis** e simples; use Navigation Compose para consistência UX. ([Android Developers][3])

---

## Referências

* Guia oficial: **Recommended App Architecture** (UI/Data/Domain). ([Android Developers][1])
* **State em Compose** (UDF, state hoisting). ([Android Developers][2])
* **Get started with Compose** (arquitetura, navegação, testes). ([Android Developers][6])
* **Navigation for Compose**. ([Android Developers][3])
* **Side-effects** em Compose. ([Android Developers][7])
* **Kotlin style guide** (nomes de packages). ([Android Developers][4])
* **Modularização** (guia e padrões). ([Android Developers][8])
