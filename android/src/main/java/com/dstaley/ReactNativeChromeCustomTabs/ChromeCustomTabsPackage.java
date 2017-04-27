package com.dstaley.ReactNativeChromeCustomTabs;

import android.app.Activity;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import com.dstaley.ReactNativeChromeCustomTabs.ChromeCustomTabsModule;

public class ChromeCustomTabsPackage implements ReactPackage {

  private final CustomTabsIntentEditor mIntentEditor;

  public ChromeCustomTabsPackage() {
    this((CustomTabsIntentEditor) null);
  }

  public ChromeCustomTabsPackage(CustomTabsIntentEditor intentEditor) {
    mIntentEditor = intentEditor;
  }

  /**
   * @deprecated
   * The activity parameter is no longer used.
   */
  @Deprecated
  public ChromeCustomTabsPackage(Activity activity) {
    this((CustomTabsIntentEditor) null);
  }

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();
    modules.add(new ChromeCustomTabsModule(reactContext, mIntentEditor));
    return modules;
  }

  @Override
  public List<Class<? extends JavaScriptModule>> createJSModules() {
    return Collections.emptyList();
  }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Arrays.asList();
  }
}
