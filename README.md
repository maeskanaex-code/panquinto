# Panquinto

Android app for discovering recipes from TheMealDB's live REST API, with offline favorites and a modern Compose UI.

## What it does

- Searches **TheMealDB's live REST API** by meal name
- Filters meals by category (Beef, Chicken, Dessert, Seafood, ...)
- Shows full recipe details: image, ingredients with measurements, instructions
- Saves favorites to a **local Room database** that persists across app restarts
- Full light + dark theme support with Material 3

## Tech stack

- **Kotlin 2.2** + **Jetpack Compose** (Material 3)
- **Retrofit 3** + **OkHttp** + **Gson** for networking
- **Room 2.7** + **KSP** for local persistence
- **Coroutines + Flow** for async and reactive streams
- **Coil 3** for image loading
- **Navigation Compose** for multi-screen routing
- Manual dependency injection via a custom `Application` class
- **MVVM** architecture: DTOs → mappers → domain models → repository → ViewModel → Compose

## Architecture

```
app/
├── data/
│   ├── remote/              Retrofit service, network module, DTOs
│   ├── local/               Room database, DAO, entity
│   ├── mapper/              DTO ↔ Domain ↔ Entity conversion
│   └── repository/          Repository interface + implementation
├── domain/
│   └── model/               Clean domain models (Meal, Ingredient, Category)
└── ui/
    ├── home/                Search + category filter screen
    ├── detail/              Recipe detail screen
    ├── favorites/           Saved meals screen
    ├── navigation/          Navigation graph
    ├── components/          Reusable Compose UI
    └── theme/               Warm Material 3 color palette
```

## Setup

1. Clone the repo
2. Open in Android Studio (Husky / Ladybug / newer)
3. Sync Gradle
4. Run on an emulator or device (min SDK 24)

**No API key needed** — the project uses TheMealDB's free test API.

## Roadmap

See [ROADMAP.md](ROADMAP.md) for the planned upgrades: weather-based meal suggestions, recipe assistant, custom launcher icon, splash screen, and privacy policy.

## Author

**Mahmoud Shehata** — Android developer
- GitHub: [@maeskanaex-code](https://github.com/maeskanaex-code)
- Kwork: [kwork.com/user/mahmoud_dev](https://kwork.com/user/mahmoud_dev)

## License

MIT — see [LICENSE](LICENSE) for details.
