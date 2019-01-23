# react-native-eos

At eva coop, we used to browserify and polyfill eosjs in order to sign transactions. Block.one also recently made their eosjs compatible with React-Native using the same method.

However, in production mode on a cellphone, the ecc signing  is way too slow because of the javascript browserfying and polyfills. In order to diminish the signing time on our app and improve UX, we had to implement an android and iOS native implementations with an API for react-native.

# Contributors
- [Raphael Gaudreault](https://github.com/raphaelgodro)


# Performance
According to our tests, the signature goes much faster on both Android and iOS. Tests are ran in production without the js debugger open (it uses the core-js from the computer instead of the device).


| Platform      | eosjs browserify | react-native-eos  |
| ------------- |:----------------:| -----------------:|
| iPhone 7    | 13.719 s   | 0.621s             |
| Android 25 LG G3    | 15.110s        |   1.233s     	   |

# Installation notes

This library was not tested with Expo. It needs XCode and Android Studio manual builds

```
npm install react-native-eos
react-native link
```

## Android

In your `android/app/build.gradle` file. Under the android{} add:

```
compileOptions{
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```
The project needs Java8.

## iOS

Your build needs to work with Swift.
In order to do that Right click on your project and choose `Add new file` in the same directory as your `AppDelegate.m` entrypoint file. Name it whatever you want since it won't be used, we just need your xcode build config to handle Swift. 

Select yes when prompted to create a Swift bridge header file.

Leave this file empty.

# Usage
### Import the lib
```
import ReactNativeEos from 'react-native-eos'
```
### Set the RPC API URL
```
ReactNativeEos.setUrl('http', 'localhost', 8888);
```
### Get chain info
```
ReactNativeEos.getInfo(info => {
	console.log('info', info);
});
```
### Send action
```
actionData = {
  "from": "eva",
  "to": "m1evfycor1b1",
  "quantity": "10.0000 EVA",
  "memo": "fast?"
}
ReactNativeEos.pushAction('eosio.token','transfer', JSON.stringify(actionData),'eva','active','5KC4AhWMFzRa3xnKDPZTYnmyANG7JDuzrruxAzvxozkTnj9QUjT').then(trxResponse => {
    console.log('trxResponse', trxResponse);
});
```

# Dependencies
This API with react-native would not have been possible without the work of the following libraries:

 - [EosCommander](https://github.com/playerone-id/EosCommander)
 - [SwiftyEOS](https://github.com/ProChain/SwiftyEOS)

# Roadmap

- Add get table 
- Integrate native wallet API
- JSON response on iOS
- Support multiple actions in transactions
- Support multisigs
