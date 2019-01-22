React-Native EOS

At eva coop, we used to browserify eosjs in order to sign transactions. Block.one also recently made their eosjs copmpatible with React-Native because it is really convenient.

However, in production mode on a cellphone, the ecc signing  is way too slow because of the javascript browserfying and polyfills. In order to diminish the signing time on our app and improve UX, we had to write an android and iOS implementation with an API for react-native.

# Installation notes
```
npm install react-native-eos
react-native link
```


##Android

add this line in your root build.gradle under android/ in repositories {}
```
maven { url 'https://jitpack.io' }
```
