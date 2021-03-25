# Movie App

Movie App is a mobile app display movie information with the backend api [theMovieDb](https://developers.themoviedb.org/).
There are 2 main screen to showing multiple movie list and the movie details.

## Features

- Home screen: 
  - Display a multiple movie list.
  - Load more.
  - Pull to refresh.
- Details screen: 
  - Display movie information.
  - Play trailer (coming soon).
  - Rate movie (conming soon).
  - Comment (coming soon).

## Technical

- **Android Jetpack Component**:
  - **[ViewModel]** - a class is designed to store and manage UI-related data in a lifecycle conscious way.
  - **[LiveData]** - an observable data holder class.
  - **[Paging]** - library that helps you load and display small chunks of data at a time.
  - **[ViewBinding]** - a feature that allows you to more easily write code that interacts with views.
 
- **Design Pattern**:
  - **[MVVM]** a Model-View-ViewModel architecture that removes the tight coupling between each component.

- **Networking**:  
  - **[Retrofit]** - a type-safe REST client for Android and Java which aims to make it easier to consume RESTful web services.
  - **[RxJava]** - a JVM library for doing asynchronous and executing event-based programs by using observable sequences.
  - **[Disposer]** - the object managing multiple Disposable instances and disposes them at the correct time.
  
 - **Dependency Injection**:
   - **[Koin]** - a lightweight dependency injection framework, that uses Kotlin's DSLs to lazily resolve your dependency graph at runtime.
 
 - **Tesing**:
   - **[Junit]** - a great unit testing platform for Java applications and now it offers special APIs for Android developers.
   - **[Mockito]** - a mocking framework that tastes really good. It lets you write beautiful tests with a clean & simple API.


## Installation

Movie App requires [Android Studio](https://developer.android.com/studio?gclid=CjwKCAjw6fCCBhBNEiwAem5SOzVx64TmqkCgZkks1ITf-GrA7PNuiud96x_102ikSczYUUzfo9ainBoCtcMQAvD_BwE&gclsrc=aw.ds) to run.

## License

**Free Software!**
