
# PayPay Coding Challange

Android application that shows live exchanges in US Dollar and on user input calculate locally different exchanged currencies.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/CaiqueAAndrade/PayPay.git
```

## Development

### Architecture:

Project developed using MVVM architecture with DataBinding from Google Android JatPack: 
Create `app/build.gradle.app` with the following info:
```gradle
buildFeatures {
    dataBinding true
}

implementation "androidx.lifecycle:lifecycle-extensions:$rootProject.extensionsVersion"
implementation "androidx.lifecycle:lifecycle-common-java8:$rootProject.lifecycleVersion"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$rootProject.lifecycleVersion"
```
Android Lifecycle and ViewModel version under `build.gradle` file:
- `lifecycleVersion = '2.3.1'`
- `extensionsVersion = '2.2.0'`

Dependency injection with Koin:
```gradle
implementation "org.koin:koin-android-viewmodel:$rootProject.koinVersion"
```

- `koinVersion = ‘2.0.1’`


## Data Repository:
Used Retrofit2 to fetch data from API and Room to persist data locally in the application:

```gradle
// Retrofit
implementation "com.squareup.retrofit2:retrofit:$rootProject.retrofit2Version"
implementation "com.squareup.retrofit2:converter-moshi:$rootProject.retrofit2Version"
implementation "com.squareup.okhttp3:logging-interceptor:$rootProject.okhttp3Version"
implementation "com.squareup.okhttp3:okhttp-urlconnection:$rootProject.okhttp3Version"

// Room
implementation "androidx.room:room-ktx:$rootProject.roomVersion"
kapt "androidx.room:room-compiler:$rootProject.roomVersion"
androidTestImplementation "androidx.room:room-testing:$rootProject.roomVersion"
```

- `retrofit2Version = '2.9.0'`
- `roomVersion = '2.3.0'`

## Coding style
The project used a variety of features to improve code quality and make it easy to maintain, as with:
 - Android extensions to in an easy way modify values.
 - CustomView to encapsulate view behavior and reuse if necessary in other parts of the application.
 - Custom retrofit call that returns results as Success, Failure, and Network Failure to intercept API calls in an easy way.
 - Shared Preferences to save the current API call time so the app will only call again when passed 30 min.


## Notice

My free plan in CurrencyLayer API didn’t have access to the `Convert` API, so I had to use `Live` API that only got exchanges in USD. After that, I had to exchange to other currencies locally.

With the free plan my account only have 250 calls por month, so if it runs out please use my mock API call instead.

 - Mock: `https://private-7021a4-currencylayer.apiary-mock.com/live`

You only need to go to file `RetrofitConfig.kt` and change the commented line with the `.baseUrl("https://private-7021a4-currencylayer.apiary-mock.com/“)`.

```file
// "convert" endpoint - convert any amount from one currency to another
// using real-time exchange rates
…
…
…

// Caique, your current subscription does not support this API function.
```

