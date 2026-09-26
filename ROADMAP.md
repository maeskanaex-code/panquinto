# Panquinto — Roadmap

Portfolio upgrade plan. Phase-by-phase log of how this project went from a tutorial-level app to a distinctive portfolio piece.

## Phase 1 — Identity (DONE)
- New app name: Panquinto
- Custom launcher icon (orange pot on cream)
- Updated strings.xml with new app_name
- Updated README header

## Phase 2 — Weather + Meal Suggestions (DONE)
- WeatherAPI.com integration (WeatherApiService, WeatherRepository)
- MealSuggestionEngine mapping weather to category
- Weather card on Home screen with suggestion + reason text
- Two Retrofit instances sharing one OkHttp client
- Silent fail on weather API error

## Phase 3 — Splash + Privacy Policy (DONE)
- AndroidX Splash Screen API
- Splash theme for light + dark
- panquinto-privacy GitHub repo
- In-app Settings screen with Privacy Policy link

## Phase 4 — Recipe Assistant (DONE)
- QueryIntent + QueryParser — rule-based NLP
- No AI, no external calls, $0 cost per query
- Assistant screen with free-text input + suggestion chips
- "Not understood" fallback message
- Sparkle icon in Home top bar

## Phase 5 — Reposition (IN PROGRESS)
- GitHub README updated with new features
- ROADMAP.md updated
- Kwork gig description updated (manual)
- Contra bio updated (manual)
- New portfolio screenshots uploaded to Kwork (manual)
- (Optional) Rename recipe-finder repo to panquinto

## Ideas for future phases
- Offline-first architecture (cache the meal list, sync on reconnect)
- Cloud sync for favorites (requires auth + backend)
- AI-powered assistant via a proxy backend (currently rule-based to keep cost at $0)
- Play Store publishing
- Localization (Arabic, Russian — the developer speaks both)
