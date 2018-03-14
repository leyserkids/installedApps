package com.grapecity.cordova.plugin;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InstalledApps extends CordovaPlugin {


    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("havePackages")) {
            return this.havePackages(data, callbackContext);
        }
        if (action.equals("haveNames")) {
            return this.haveNames(data, callbackContext);
        }
        if (action.equals("getPackages")) {
            return getPackages(callbackContext);
        }
        if (action.equals("getNames")) {
            return getNames(callbackContext);
        }
        if (action.equals("getNamesAndPackages")) {
            return getNamesAndPackages(callbackContext);
        }
        return false;
    }

    private boolean havePackages(JSONArray data, CallbackContext callbackContext) throws JSONException {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
        JSONArray ar = new JSONArray();
        for (int i = 0; i < data.length(); i++) {
            String pkgName = data.getString(i);
            Boolean existed = false;
            for (ApplicationInfo packageInfo : packages) {
                if (packageInfo.packageName.equals(pkgName)) {
                    existed = true;
                    break;
                }
            }
            ar.put(existed);
        }

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));
        return true;
    }

    private boolean haveNames(JSONArray data, CallbackContext callbackContext) throws JSONException {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
        ArrayList<String> names = new ArrayList<String>();

        for (ApplicationInfo packageInfo : packages) {
            try {
                names.add(pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, 0)).toString());
            } catch (NameNotFoundException e) {
                // ignore exception as it it
            }
        }

        JSONArray ar = new JSONArray();
        for (int i = 0; i < data.length(); i++) {
            String name = data.getString(i);
            Boolean existed = false;
            for (String n : names) {
                if (n.equals(name)) {
                    existed = true;
                    break;
                }
            }
            ar.put(existed);
        }

        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));

        return true;
    }

    private boolean getPackages(CallbackContext callbackContext) throws JSONException {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
        ArrayList<JSONObject> res = new ArrayList<JSONObject>();

        for (ApplicationInfo packageInfo : packages) {
            JSONObject json = new JSONObject();
            json.put("package", packageInfo.packageName);
            res.add(json);
        }

        JSONArray ar = new JSONArray(res);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));

        return true;
    }

    private boolean getNames(CallbackContext callbackContext) throws JSONException {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
        ArrayList<JSONObject> res = new ArrayList<JSONObject>();

        for (ApplicationInfo packageInfo : packages) {
            try {
                JSONObject json = new JSONObject();
                json.put("name", pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, 0)).toString());
                res.add(json);
            } catch (NameNotFoundException e) {
                // ignore exception as it it
            }

        }

        JSONArray ar = new JSONArray(res);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));

        return true;
    }

    private boolean getNamesAndPackages(CallbackContext callbackContext) throws JSONException {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
        ArrayList<JSONObject> res = new ArrayList<JSONObject>();

        for (ApplicationInfo packageInfo : packages) {
            try {
                JSONObject json = new JSONObject();
                json.put("package", packageInfo.packageName);
                json.put("name", pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, 0)).toString());
                res.add(json);
            } catch (NameNotFoundException e) {
                // ignore exception as it it
            }

        }

        JSONArray ar = new JSONArray(res);
        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));

        return true;
    }
}