package com.dstaley.ReactNativeChromeCustomTabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.widget.Toast;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;
import java.util.HashMap;

import com.dstaley.ReactNativeChromeCustomTabs.CustomTabActivityHelper;

public class ChromeCustomTabsModule extends ReactContextBaseJavaModule implements CustomTabActivityHelper.ConnectionCallback {

  Activity mActivity;
  private CustomTabActivityHelper mCustomTabActivityHelper;
  private ReactApplicationContext mContext;

  public ChromeCustomTabsModule(ReactApplicationContext reactContext, Activity activity) {
    super(reactContext);
    mActivity = activity;
    mContext = reactContext;
    String packageName = CustomTabsHelper.getPackageNameToUse(mActivity);
    mCustomTabActivityHelper = new CustomTabActivityHelper();
    mCustomTabActivityHelper.setConnectionCallback(this);
    mCustomTabActivityHelper.bindCustomTabsService(mActivity, reactContext.getApplicationContext());
  }

  private void sendEvent(String eventName) {
    mContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
      .emit(eventName, null);
  }

  @Override
  public String getName() {
    return "ChromeCustomTabsClient";
  }

  @Override
  public void onCustomTabsConnected() {
    this.sendEvent("customTabsConnected");
  }

  @Override
  public void onCustomTabsDisconnected() {
    this.sendEvent("customTabsDisconnected");
  }

  @ReactMethod
  public void mayLaunchUrl(String url) {
    Boolean didWarm = mCustomTabActivityHelper.mayLaunchUrl(Uri.parse(url), null, null);
    if (didWarm) {
      this.sendEvent("didWarmUrl");
    } else {
      this.sendEvent("didNotWarmUrl");
    }
  }

  @ReactMethod
  public void launchCustomTab(String url) {
    CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder(mCustomTabActivityHelper.getSession()).build();
    mCustomTabActivityHelper.openCustomTab(mActivity, customTabsIntent, Uri.parse(url), null);
  }
}