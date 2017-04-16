/*global cordova, module*/

module.exports = {
    /**
     * detect whether install the packages or not by their package names
     * @param {Array<String>} packages all package names in array
     * @param {function(Array<Boolean>)} successCallback
     * @param {Function} errorCallback
     */
    havePackages       : function (packages, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "InstalledApps", "havePackages", packages);
    },
    /**
     * detect whether install the package or not by their names
     * @param {Array<String>} names
     * @param {function(Array<Boolean>)} successCallback
     * @param {Function} errorCallback
     */
    haveNames          : function (names, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "InstalledApps", "haveNames", names);
    },
    getPackages        : function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "InstalledApps", "getPackages", []);
    },
    getNames           : function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "InstalledApps", "getNames", []);
    },
    getNamesAndPackages: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "InstalledApps", "getNamesAndPackages", []);
    }

};
