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

---
