# react-native-chrome-custom-tabs

![Demo of Chrome Custom Tabs in React Native](http://i.imgur.com/ZxO4Tut.gif)

## Installation

1. `npm install --save react-native-chrome-custom-tabs`
2. Add the following to `android/settings.gradle`:
```
include ':ReactNativeChromeCustomTabs', ':app'
project(':ReactNativeChromeCustomTabs').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-chrome-custom-tabs/android')
```
3. Add `compile project(':ReactNativeChromeCustomTabs')` to  `dependencies` in `android/app/build.gradle`
4. Import and register the module in your `MainActivity.java` file:
```java
import com.dstaley.ReactNativeChromeCustomTabs.ChromeCustomTabsPackage; // <-- Import
```

### For React Native >= v0.29

```java
public class MyReactNativeHost extends ReactNativeHost {
  @Override
  protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        ...
        new ChromeCustomTabsPackage(),  // <-- Register
        ...
    );
  }
}
```

### For React Native v0.19 - v0.28

```java
public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .addPackage(new ChromeCustomTabsPackage(this))          // <-- Register
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        mReactRootView.startReactApplication(mReactInstanceManager, "DemoReactNativeCustomTabs", null);

        setContentView(mReactRootView);
    }
```

## Usage

```js
var ChromeCustomTabsClient = require('react-native-chrome-custom-tabs');

// Tell Chrome to preload a URL
ChromeCustomTabsClient.mayLaunchUrl('http://i.imgur.com/6ogeF96.gif');

// Launch a Custom Tab
ChromeCustomTabsClient.launchCustomTab('http://i.imgur.com/xjdem.gif');
```

## Customization

You can supply a `CustomTabsIntentEditor` to customize the CustomTabsIntent produced by
this package.

```java
    new ChromeCustomTabsPackage(new CustomTabsIntentEditor() {
      @Override
      public void customize(Context context, CustomTabsIntent.Builder builder) {
        builder
          .addDefaultShareMenuItem()
          .setShowTitle(true)
          .enableUrlBarHiding();
      }
    })
```

For a full description of the available options, see the official documentation for
[CustomTabsIntent.Builder](https://developer.android.com/reference/android/support/customtabs/CustomTabsIntent.Builder.html)
and the [custom tabs implementation guide](https://developer.chrome.com/multidevice/android/customtabs#implementationguide).

## License

MIT