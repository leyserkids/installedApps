package com.grapecity.cordova.plugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;

import java.util.List;
import java.util.ArrayList;

public class InstalledApps extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("havePackages")) {
            PackageManager pm = this.cordova.getActivity().getPackageManager();
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
        } else if (action.equals("haveNames")) {
            PackageManager pm = this.cordova.getActivity().getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
            ArrayList<String> names = new ArrayList<String>();

            for (ApplicationInfo packageInfo : packages) {
                try {
                    names.add(pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, 0)).toString());
                } catch (NameNotFoundException e) {
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
        } else if (action.equals("getPackages")) {
            PackageManager pm = this.cordova.getActivity().getPackageManager();
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

        } else if (action.equals("getNames")) {
            PackageManager pm = this.cordova.getActivity().getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
            ArrayList<JSONObject> res = new ArrayList<JSONObject>();

            for (ApplicationInfo packageInfo : packages) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("name", pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, 0)).toString());
                    res.add(json);
                } catch (NameNotFoundException e) {
                }

            }

            JSONArray ar = new JSONArray(res);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));

            return true;

        } else if (action.equals("getNamesAndPackages")) {
            PackageManager pm = this.cordova.getActivity().getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_PROVIDERS);
            ArrayList<JSONObject> res = new ArrayList<JSONObject>();

            for (ApplicationInfo packageInfo : packages) {
                try {
                    JSONObject json = new JSONObject();
                    json.put("package", packageInfo.packageName);
                    json.put("name", pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, 0)).toString());
                    res.add(json);
                } catch (NameNotFoundException e) {
                }

            }

            JSONArray ar = new JSONArray(res);
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, ar));

            return true;
        } else {

            return false;

        }
    }
}