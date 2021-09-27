# Top Albums
An Android App to display a list of albums

The application consists of 2 modules containing
* app:
  * activities & fragments
  * mvvm framework & retrofit services
  * navigation
  * view binding
* core
  * data models
  * repository
  * use cases

### Requirements

* Android 6.0 Marshmallow or higher.
* Android Studio 4.1.1

### Architecture Components

* **MVVM pattern**: ViewModels are used from androidx lifecycle packages. View reads LiveData from the ViewModel.
* **Koin dependency injection**
* **Retrofit network client**

### Test coverage

* Instrumentation tests with Espresso to test the loading of albums
* Unit tests with Mockk to test Repositiory and ViewModel

### Future Improvements

* Introduce exception handling framework
* Implement Pull to refresh on the recyclerView
