# Cordova Installed Apps Plugin

Simple plugin that implement following two features:
* detect special packages whether install on android device 
* returns all of the installed apps, their names and their packages on android devices.
* check whether the Remote Push Notification is enabled.

##Install

recomended

    $ cordova plugin add https://github.com/leyserkids/installedApps.git --save
    

## Using

```js
    var success = function( object ) {
        console.log( object );
    }
  
    var failure = function() {
        alert("Error calling Installed Apps Plugin");
    }

    installedApps.havePackages(['com.grapecity.leyserkids.parent'], success, failure);
```

  Or
  
 ```js 
    // not recommended, as the name of app may not be unified
    installedApps.haveNames(['Leyserkids-P'], success, failure);
 ``` 
 
  Or
  
 ```js 
    installedApps.getPackages( success, failure);
 ``` 
 
  Or
  
 ```js 
    installedApps.getNames( success, failure);
 ``` 
 
  Or
  
```js  
    installedApps.getNamesAndPackages( success, failure);
```    

  Or
  
```js  
	// In the 1st arg of the success callback, 0 stands for disabled while 1 stands for enabled
    installedApps.checkNotificationEnabled( success, failure);
``` 

* For haveXXX api, result will be array of booleans.
* For getXXX api, result will be array of objects, each containing name, package or both.

It works great with this plugin for starting apps from cordova http://plugins.cordova.io/#/package/com.hutchind.cordova.plugins.launcher

  
