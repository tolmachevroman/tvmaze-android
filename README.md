# tvmaze-android
TVmaze Android app

An Android app for [TVmaze service](https://www.tvmaze.com/api).

## Features
- Authentication using PIN or Biometric (fingerprint) if available.
-  Shows list with pagination.
- Add/remove/list favorite Shows.
- Display Show details with Seasons and Episodes.
- Display Episode details.
- Search for a Show or a Person.
- Show Person and list of Shows he or she participated in.

## Architecture and implementation details
App takes advantage of MVVM architecture + Jetpack Compose, LiveData for state management, Hilt for dependency injection, Retrofit for network requests, Biometric for fingertip authentication, among others.

Two UI flows live under [authentication](https://github.com/tolmachevroman/tvmaze-android/tree/main/app/src/main/java/com/tv/maze/ui/authentication) and [main](https://github.com/tolmachevroman/tvmaze-android/tree/main/app/src/main/java/com/tv/maze/ui/main) packages.  They contain `Composable` `NavController`s navigating between several `Composable` screens.

- Regarding PIN code to open the app, since there's no way to recover it, once you set it up, if you forget it you'll have to reinstall the app or use biometric authentication.

- Regarding pagination, API return 250 items on one page, so next page is fetched after nearly 250 items are scrolled. Backend caches this data for up to 24 hours, and so to save space and unnecessary requests app caches the data too. App [stores json files for each page](https://github.com/tolmachevroman/tvmaze-android/blob/main/app/src/main/java/com/tv/maze/data/TVMazeRepository.kt#L28), and reads data from them if 24 hours have not passed or page is not a newer one.

## Screenshots

Login             |  Create new PIN code
:-------------------------:|:-------------------------:
![](https://user-images.githubusercontent.com/560815/165883743-6a3bcee2-55f0-41c9-92a5-454a00b863e0.png)  |  ![](https://user-images.githubusercontent.com/560815/165883745-6d3a8964-c31c-4d56-81fa-9d94d61b0d02.png)

---
All Shows             |  Favorite Shows
:-------------------------:|:-------------------------:
![](https://user-images.githubusercontent.com/560815/165883756-b281bfc4-26d0-4371-84ad-00e0039fb9c4.png)  |  ![](https://user-images.githubusercontent.com/560815/165883773-ab4d9e4e-f5a9-45d1-867b-28d995ff82fb.png)

---
Search, shows found             |  Search, people found
:-------------------------:|:-------------------------:
![](https://user-images.githubusercontent.com/560815/165883777-f11b3a36-72c7-4895-ace6-d25e8701c9e1.png)  |  ![](https://user-images.githubusercontent.com/560815/165883779-662a7241-2a9b-4bed-bf02-092d3f3d7ae9.png)

---
Different placeholders for actress and actors         |  Actress details
:-------------------------:|:-------------------------:
![](https://user-images.githubusercontent.com/560815/165883781-2e7dcb23-3ff1-4bfe-9827-187f3b36999e.png)  |  ![](https://user-images.githubusercontent.com/560815/165883775-d1c1fde5-2dca-47e8-8717-f9f3aa9de115.png)

---
Show details               |  Episode details
:-------------------------:|:-------------------------:
![](https://user-images.githubusercontent.com/560815/165883782-54412d56-41ce-4b7d-ba42-44e8462c2179.png)  |  ![](https://user-images.githubusercontent.com/560815/165883769-32be3dcd-6acb-41fa-a6e8-6590902c2e75.png)