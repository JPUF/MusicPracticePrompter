# 🎼 Practice Prompts 
***An Android app for music practice.***

▶️ Google Play

![screenshots of the app](https://i.imgur.com/wXIFc3l.jpg)

A visual aide, giving you helpful prompts when you're practising your instrument.
New prompts can be given on demand (tap the screen), or on a timer (with an interval of your choosing).

## ⌨️ Development
This app was made for Android, using Kotlin and Android Studio. It was designed as an exercise in best practice techniques, as well as to familiarise myself with this excellent programming language.

### ⚡ Fragment based design.
Being a simple app, my priority was to create a responsive UI, the app would be much more pleasant to use if transitions between pages were snappy. I knew I'd need to pass information between certain pages, and there was no need to support other phone features such as the camera. As such the benefits of a Fragment based approach were clear, and it proved to be more enjoyable to develop than the Activity-based flow I'd used before.  

### 🗺️ Navigation
The whole app was developed by myself, nevertheless it's good practice to work as if for a team. Implementing navigation throughout the app using a NavGraph component makes it easier for other developers to visualise the paths through the app.

![image of NavGraph](https://i.imgur.com/HLcgWvN.png)

Information corresponding to the user's choices is passed between fragments. This was implemented using the ***SafeArgs*** plugin. This allows for data to be passed in a type-safe manner, which is particularly important when developing in teams. 
### 💽 Data Binding
UI elements are implemented as `View` objects in the Android source code. These can be altered programmatically when on the UI thread. To bring these `View` objects into scope, I've previously used the `.findViewById(..)` method. However, this is an expensive run-time operation. So in the interest of a responsive UI with low overhead, I used *Data Binding*. This allows for declarative references to UI elements, bringing them into scope at compile-time, thus reducing overhead.
### 🖼️ Adaptive layouts
Devices have greatly varying resolutions and aspect ratios, this has been considered whilst designing the Fragment layouts, with appropriate constraints, and the use of ScrollView containers to cater for smaller screens. 

![screenshot of title page in landscape](https://i.imgur.com/kkVxUuU.png)

The user may wish to use the app in landscape orientation, so a separate layout XML file is used to dictate an appropriate ordering for the relevant pages.

### Memory Leaks
To avoid OutOfMemoryErrors, developers should identify and fix memory leaks. This is particularly important with my app, since some users may well wish to use the app for long sessions without closing the app. 

I used LeakCanary to identify the possible causes of memory leaks.
