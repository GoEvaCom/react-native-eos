React-Native EOS

At eva coop, we used to browserify eosjs in order to sign transactions. However, ion production on a cellphone, it is way too slow, especially on older phones. In order to diminish the signing time, we had to write an android and iOS implementation with an API for react-native.

# Installation notes
```
npm install [...]
react-native link
```


##Android

add this line in your root build.gradle under android/ in repositories {}
```
maven { url 'https://jitpack.io' }
```
