# The Garden — Plant ID Mobile App

## Overview

**The Garden** is an Android app that lets users identify plants by taking a photo and automatically builds a personal virtual herbarium. It combines a third-party AI plant identification API with a custom Spring Boot backend that handles user accounts, authentication, and plant persistence.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Mobile | Android (Java), Retrofit, Gson |
| Backend | Spring Boot (Java), Spring Security |
| Database | PostgreSQL / SQLite |
| AI | Plant.id API v3 |
| Auth | JWT (HS512) |
| Networking | Retrofit 2, OkHttp |

---

## Architecture

```
┌─────────────────────────────────────────────┐
│               Android App                   │
│                                             │
│  Camera → Base64 → Plant.id API (External)  │
│                         │                   │
│               Identification Results        │
│                         │                   │
│           User selects plant suggestion     │
│                         │                   │
│        Spring Boot Backend (Your server)    │
│                         │                   │
│                    PostgreSQL DB            │
└─────────────────────────────────────────────┘
```

The app works in two parallel tracks:
- **Identification track**: image goes directly from the phone to the Plant.id AI API.
- **Persistence track**: once the user confirms a plant, it's sent to your own backend and stored linked to their account.

---

## How the AI Integration Works (Behind the Screen)

### Step 1 — Capturing the Image

When the user taps the scan button in `ScanFragment`, the app launches the Android camera intent. After the photo is taken, the resulting `Bitmap` is passed to `IdentifyPlantUtil`.

```
ScanFragment → camera intent → Bitmap
```

### Step 2 — Encoding the Image

The raw bitmap can't be sent directly over HTTP. `IdentifyPlantUtil.convertBitmapToBase64()` compresses it as a JPEG and encodes it to a Base64 string. This string is what the Plant.id API expects.

```java
// IdentifyPlantUtil.java
ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
byte[] byteArray = byteArrayOutputStream.toByteArray();
return Base64.encodeToString(byteArray, Base64.DEFAULT);
```

### Step 3 — Sending to the Plant.id API

`IdentifyPlantUtil.identifyPlantFromBase64()` calls `PlantIDApi` via Retrofit. The request body is a `PlantRequest` object containing the Base64 image wrapped in a list (the API supports multi-image requests) and a flag to include similar images in the response.

```
POST https://plant.id/api/v3/identification
Header: Api-Key: <your_key>
Body: { "images": ["<base64string>"], "similar_images": true }
```

The `PlantIDApi` interface is a Retrofit definition:

```java
// PlantIDApi.java
@POST("v3/identification")
Call<PlantResponse> identifyPlant(
    @Header("Api-Key") String apiKey,
    @Body PlantRequest plantRequest
);
```

`RetrofitClient` provides a singleton Retrofit instance pointed at `https://api.plant.id` using a Gson converter to automatically map JSON responses to Java objects.

### Step 4 — Parsing the Response

The Plant.id API returns a structured JSON response. `PlantResponse` models this hierarchy:

```
PlantResponse
  └── result
        └── classification
              └── suggestions[]          ← list of possible plants
                    ├── name             ← e.g. "Rosa canina"
                    └── similar_images[]
                          └── url        ← reference image URL
```

Each suggestion represents a candidate plant species with a confidence score. The app extracts the `name` and a representative `url` from the top suggestions and presents them to the user.

### Step 5 — User Selects a Plant

`SelectPlantActivity` displays the suggestions. When the user picks one, the plant's name and image URL are packaged into a `PlantSaveRequestDto` and sent to the backend.

### Step 6 — Saving to Your Backend

`PlantApi` (another Retrofit interface, this time pointed at your own Spring Boot server) calls:

```
POST /api/plants
Header: Authorization: Bearer <jwt_token>
Body: { "name": "Rosa canina", "imageUrl": "https://..." }
```

`PlantController` on the backend receives this, extracts the currently authenticated user's email from the Spring Security context (populated by the JWT filter), and delegates to `PlantServiceImpl`, which:
1. Looks up the user in the database.
2. Checks for duplicate plant names (case-insensitive) per user.
3. Persists a new `Plant` entity linked to that user via a many-to-one relationship.

### Step 7 — Viewing Saved Plants

`GET /api/plants` returns all plants for the authenticated user. The mobile app displays these in `PlantsFragment` using `PlantAdapter`.

---

## Authentication Flow

User sessions are secured with JWT tokens:

1. User registers via `POST /api/v1/registration`.
2. User logs in via `POST /api/v1/auth/login` — the backend validates credentials, generates a JWT signed with HS512, and returns it in the response header.
3. The Android app stores the token and attaches it as a `Bearer` header on every subsequent request.
4. `JwtAuthenticationFilter` intercepts every request, validates the token, and populates the Spring Security context. Tokens expire after **3 days**.

---

## Features

- **Plant Identification** — AI-powered species recognition from a single photo.
- **Identification Suggestions** — Multiple candidate species with reference images returned by the AI.
- **Virtual Herbarium** — Save identified plants to a personal collection stored in the cloud.
- **User Accounts** — Registration and login with JWT-secured sessions.
- **Duplicate Prevention** — The backend rejects re-saving the same plant species for the same user.

---

## Getting Started

### Prerequisites

- Android Studio
- IntelliJ IDEA
- JDK 17+
- Maven
- PostgreSQL

### 1. Clone the Repository

```sh
git clone https://github.com/sorana01/TheGarden
```

### 2. Set Up the Backend

- Open the `backendApp` directory as a project in IntelliJ IDEA.
- Update `src/main/resources/application.properties` with your PostgreSQL connection details.
- Run `BackendAppApplication.java`.

### 3. Obtain a Plant.id API Key

- Create an account at [plant.id](https://plant.id) and request an API key.
- Open `mobileApp` in Android Studio.
- In `PlantIDApi.java`, replace the placeholder with your API key.

### 4. Configure the Backend URL

- In `mobileApp/.../network/ApiClient.java`, update the base URL to point to the machine running your Spring Boot backend.

### 5. Run the App

Run `MainActivity` from Android Studio with the backend already running.

---

## Scanning Tutorial

Here's a short demo of how to correctly scan a plant:

![tutorial_gif](https://github.com/sorana01/TheGarden/assets/74464853/9daa6f32-0f4d-471a-a5c6-ed5e320f979b)
