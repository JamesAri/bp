# Android Jetpack Lost & Found Application

## Zásady pro vypracování

- Prozkoumejte, jaké možnosti a výhody Android Jetpack přináší při vytváření mobilních aplikací na platformě Android.
- Navrhněte vhodnou netriviální aplikaci, na které ukážete použití Android JetPacku.
- Navrženou aplikaci realizujte, ověřte její funkcionalitu reprezentativní sadou testů na platformě Android a navrhněte další možná rozšíření aplikace.

## Development notes

### Emulator version and Firestore

If you are using emulator version above `31.2.10` I recommend switching to older version since  
there are known issues with newer version of android studio emulators and Firestore API.  
You can check more on this issue [here](https://stackoverflow.com/questions/73370728/firebase-doesnt-work-on-android-studio-emulator/73584389#73584389).

To download older version of emulator you can navigate [here](https://developer.android.com/studio/emulator_archive).

### Firestore rules

Current Firestore development rules:

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if true;
    }
  }
}
```

Accounted for: kubaslechtaaa@gmail.com

## Personal notes

### Jepack compose

Jetpack Compose is a modern, reactive, declarative framework for building user interfaces in Android apps. It was designed to make it easier and faster to build user interfaces that are performant, accessible, and customizable.

---

#### Advantages

Here are some of the key advantages of Jetpack Compose:

##### Simplified UI development

Jetpack Compose uses a declarative, functional style of programming that makes it easier to build and maintain complex user interfaces. With Jetpack Compose, you define your UI as a function of the app's state, and the framework takes care of rendering the UI and updating it as the state changes. This can help reduce the amount of boilerplate code and make it easier to build and test UI components.

Here's an example of how you might define a simple button in Jetpack Compose:

``` Kotlin
@Composable
fun MyButton(onClickHandler: () -> Unit) {
  Button(onClick = onClickHandler) {
    Text("Click here")
  }
}

```

In this example, the `MyButton` function is annotated with `@Composable`, which tells the framework that it can be used to build a UI element. The function takes a lambda function as an argument and returns a `Button` element with a `Text` child element. When the button is clicked, the `onClick` lambda function will be called.


##### Performance optimizations

Jetpack Compose uses a number of techniques to optimize UI performance, including automatic view recycling, lazy evaluation, and incremental rendering which means you won't have to deal with these optimizations yourself. This can help ensure that your app's UI is smooth and responsive, even when dealing with large amounts of data. Of course it is possible to achieve the same result with traditional Android framework, but it is more time consuming.


##### Enhanced customization

Jetpack Compose allows you to easily customize the appearance and behavior of your app's UI using themes, styles, and other customizations. It also provides a rich set of built-in UI components and tools for building custom components, making it easy to create a consistent and cohesive look and feel for your app.


##### Improved accessibility

Jetpack Compose includes built-in support for accessibility, including support for screen readers, keyboard navigation, and other assistive technologies. This can help ensure that your app is accessible to users with disabilities and meets the latest accessibility standards.

---

#### Disadvantages

Jetpack Compose is a relatively new framework, and as with any new technology, it may have some challenges and limitations that you should consider when deciding whether to use it in your app. Here are a few potential disadvantages of Jetpack Compose:


##### Limited adoption

Jetpack Compose is still a relatively new framework, and as such, it has not yet gained widespread adoption among Android developers. This can make it more difficult to find resources, examples, and support when working with Jetpack Compose.


##### Complexity

Jetpack Compose introduces a new way of building user interfaces in Android, and it can take some time to learn and master the framework. In particular, the declarative, functional style of programming used in Jetpack Compose can be quite different from the traditional imperative style of Android development, which can be a challenge for some developers.


##### Limited support for legacy code

Jetpack Compose is designed to work alongside the traditional Android framework, but it is not fully compatible with older code written in the traditional Android framework. If you have a large codebase that relies on the traditional Android framework, it may be difficult to integrate Jetpack Compose into your app without significant refactoring.


##### Performance overhead

Jetpack Compose introduces an additional layer of abstraction between the app code and the underlying Android framework. This can result in some performance overhead compared to the traditional Android framework, which directly manipulates Android views and view hierarchies. In most cases, the performance impact will be minimal, but it is worth considering when designing your app.

---

#### Conclusion

Overall, Jetpack Compose offers many benefits and can be a powerful tool for building user interfaces in Android apps. However, it is important to carefully consider the potential disadvantages and determine whether Jetpack Compose is the right fit for your project.


