# Panquinto

Android app for discovering recipes — with live weather-based meal suggestions and a natural-language recipe assistant.

## What it does

- **Search meals** by name against TheMealDB's live REST API
- **Filter** by category (Beef, Chicken, Dessert, Seafood, ...) with category chips
- **Weather-aware suggestions** — fetches current weather for your city and suggests a meal that fits the conditions
- **Recipe assistant** — type things like "quick dinner for kids" or "spicy chicken" and a rule-based parser turns it into a structured query
- **Save favorites** to a local Room database that persists across app restarts
- **Light + dark theme** with Material 3 and a custom warm palette
- **Splash screen** with the app icon and brand color

## Tech stack

- Kotlin 2.2 + Jetpack Compose (Material 3)
- Retrofit 3 + OkHttp + Gson — two live APIs (TheMealDB + WeatherAPI.com)
- Room 2.7 + KSP for local persistence
- Coroutines + Flow for async and reactive streams
- Coil 3 for image loading
- Navigation Compose for multi-screen routing
- AndroidX Splash Screen API for the cold-start experience
- Manual dependency injection via a custom Application class
- MVVM architecture: DTOs → mappers → domain models → repository → ViewModel → Compose

## Architecture highlights

- Two Retrofit instances sharing one OkHttp client — one per API base URL
- Rule-based natural-language parser (QueryParser.kt) that turns free-text input into a structured QueryIntent — no AI, no external calls, zero cost
- Weather → meal suggestion engine (MealSuggestionEngine.kt) maps weather condition codes to meal categories
- DTO ↔ Domain ↔ Entity separation — the UI never sees Retrofit or Room types
- Silent fail on non-essential APIs — if weather is down, the app keeps working

## Setup

1. Clone the repo
2. Open in Android Studio (Husky / Ladybug / newer)
3. Add your WeatherAPI.com key to gradle.properties:
   WEATHER_API_KEY=your_key_here
   (This file is in .gitignore — the key never enters version control.)
4. Sync Gradle
5. Run on an emulator or device (min SDK 24)

TheMealDB uses a free public test key — no setup needed for it.

## Roadmap

See ROADMAP.md for the phase-by-phase build log.

## Privacy

See the privacy policy: https://github.com/maeskanaex-code/panquinto-privacy
The app collects no personal data.

## Author

Mahmoud Shehata — Android developer
- GitHub: https://github.com/maeskanaex-code
- Kwork: https://kwork.com/user/mahmoud_dev

## License

MIT — see LICENSE for details.
