package com.dstaley.ReactNativeChromeCustomTabs;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;
import java.util.HashMap;

import com.dstaley.ReactNativeChromeCustomTabs.CustomTabActivityHelper;

public class ChromeCustomTabsModule extends ReactContextBaseJavaModule implements CustomTabActivityHelper.ConnectionCallback {

  private CustomTabActivityHelper mCustomTabActivityHelper;
  private ReactApplicationContext mContext;

  public ChromeCustomTabsModule(ReactApplicationContext reactContext) {
    super(reactContext);
    mContext = reactContext;
    mCustomTabActivityHelper = new CustomTabActivityHelper();
    mCustomTabActivityHelper.setConnectionCallback(this);
    mCustomTabActivityHelper.bindCustomTabsService(reactContext.getApplicationContext());
  }

  private void sendEvent(String eventName) {
    if (mContext.hasActiveCatalystInstance()) {
      mContext
        .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, null);
    }
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
    Activity activity = getCurrentActivity();
    if (activity == null) {
      return;
    }

    CustomTabsIntent customTabsIntent =
        new CustomTabsIntent.Builder(mCustomTabActivityHelper.getSession()).build();
    CustomTabActivityHelper.openCustomTab(activity, customTabsIntent, Uri.parse(url), null);
  }
}
