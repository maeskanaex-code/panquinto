# Recipe Finder

Android portfolio app demonstrating end-to-end **Retrofit + Room + Jetpack Compose** in a clean MVVM architecture.

## What it does

- Searches **TheMealDB's live REST API** by meal name
- Filters meals by category (Beef, Chicken, Dessert, Seafood, ...)
- Shows full recipe details: image, ingredients with measurements, instructions
- Saves favorites to a **local Room database** that persists across app restarts
- Full light + dark theme support with Material 3

## Screens

| Home | Detail | Favorites |
|------|--------|-----------|
| Search + category chips | Ingredients + instructions | Room-persisted list |

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
│   ├── remote/      Retrofit service, network module, DTOs
│   ├── local/       Room database, DAO, entity
│   ├── mapper/      DTO ↔ Domain ↔ Entity conversion
│   └── repository/  Repository interface + implementation
├── domain/
│   └── model/       Clean domain models (Meal, Ingredient, Category)
└── ui/
    ├── home/        Search + category filter screen
    ├── detail/      Recipe detail screen
    ├── favorites/   Saved meals screen
    ├── navigation/  Navigation graph
    ├── components/  Reusable Compose UI
    └── theme/       Warm Material 3 color palette
```

## Setup

1. Clone the repo
2. Open in Android Studio (Husky / Ladybug / newer)
3. Sync Gradle
4. Run on an emulator or device (min SDK 24)

**No API key needed** — the project uses TheMealDB's free test API (`key=1`).

## Notes

- TheMealDB returns ingredients as 20 numbered fields (`strIngredient1..20`) rather than a JSON array. The mapper handles this by zipping ingredient and measure slots and dropping empty ones — see `MealMappers.kt`.
- The `filter.php` endpoint returns only partial meal data (id + name + thumbnail). The DTO marks all non-essential fields nullable to handle this.

## Author

**Mahmoud Dev** — Android developer
- Kwork: [kwork.com/user/mahmoud_dev](https://kwork.com/user/mahmoud_dev)
- GitHub: [@maeskanax-code](https://github.com/maeskanax-code)

## License

MIT — see [LICENSE](LICENSE) for details.
