<h1 align="center">My Movies App</h1>
<p align="center">
A fully functional, modern, yet simple example of a movie app. 
Remote data source is popular <b>TheMovieDB API</b>.
The project uses latest methods and best practices as much as possible from architecture to UI design patterns.
</p>

<p align="center">
  <a href="https://kotlinlang.org/docs/releases.html#release-details"><img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-1.5.+-green.svg?style=flat&logo=kotlin"/></a>
  <a href="https://developer.android.com/studio/releases/gradle-plugin#7-0-1"><img alt="Gradle Version" src="https://img.shields.io/badge/Gradle-7.0.1-yellowgreen.svg?style=flat&logo=gradle"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/boy12hoody/MyMovies/actions/workflows/android.yml"><img alt="GitHub Workflow Status" src="https://img.shields.io/github/workflow/status/boy12hoody/MyMovies/Build%20&%20Publish%20Debug%20APK"></a>
  <a href="https://t.me/boywonder"><img alt="Telegram" src="https://img.shields.io/badge/Telegram-@BoyWonder-blue.svg?style=flat&logo=telegram"/></a>
</p>

### Screenshots
Soon to be added..

## Status
No further development. It was meant to test the API..

## Download
Go to the [Releases](https://github.com/boy12hoody/MyMovies/releases) to download the latest APK (Demo Api Key included).

## Build And Run

1. Get a free API Key at [TheMovieDB.com](https://developers.themoviedb.org)
2. Clone the repo
   ```sh
   git clone https://github.com/boy12hoody/MyMovies.git
   ```
3. From Android Studio, select *Import Project*, then select the root folder of the cloned repository.
4. Click *Make Project* to build the app and download all the required dependencies.
5. Replace API key with yours in `util/Constants.kt`
   ```JS
   const val API_KEY = "Paste_Your_Key_Here"
   ```
6. Click *Run app* to install the app on your device or emulator.


## 🛠 Tech stack & Open-source libraries
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Parcelize](https://developer.android.com/kotlin/parcelize) - Parcelable implementation generator.
- [TheMovieDB API](https://developers.themoviedb.org) - Famous Movies and TV database.
- [Retrofit 2](https://github.com/square/retrofit) - A type-safe HTTP client for Android and the JVM.
- [Moshi + Codegen](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Chucker](https://github.com/ChuckerTeam/chucker) - An HTTP inspector for Android & OkHTTP.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin Coroutines.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [Jetpack](https://developer.android.com/jetpack) 
        - [Navigation](https://developer.android.com/guide/navigation) - Navigation component designed for apps that have one main activity with multiple fragment destinations.
        - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
        - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Handling lifecycles with lifecycle-aware components.
        - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
        - [AndroidViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/AndroidViewModel) - Application context aware `ViewModel`
        - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
    - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
    - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Material Components for Android](https://material.io/develop/android/docs/getting-started/) - Modular and customizable Material Design UI components for Android.
- [Shimmer Effect](https://facebook.github.io/shimmer-android/) - An easy way to add a shimmer effect to any view.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.
![](https://user-images.githubusercontent.com/70273198/128603663-0c51a103-296e-433f-9da0-6a013b82a969.png)

## Package Structure

    uz.boywonder.mymovies          # Root Package
    .
    ├── adapters                    # Adapters for RecyclerView
    |
    ├── data                        # Data sources and repositories.
    │   └── network                 # Remote Data Handler (API)
    │
    ├── models                      # Model classes
    |
    ├── di                          # Dependency Injection
    |
    ├── ui                          # View layer - Activity / Fragments / ViewModels
    │
    └── utils                       # Utility Classes

## Contact
If you have questions or need any help, contact me on
[![Telegram](https://img.shields.io/badge/Telegram-@BoyWonder-blue.svg?style=flat&logo=telegram)](https://t.me/boywonder)

# License
```
   Copyright (c) 2021 Ismatov Xurshid
   
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
