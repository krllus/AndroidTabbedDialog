<a href="https://opensource.org/licenses/Apache-2.0" target="_blank"><img src="https://img.shields.io/badge/License-Apache_v2.0-blue.svg?style=flat"/></a>

[![](https://jitpack.io/v/krllus/AndroidTabbedDialog.svg)](https://jitpack.io/#krllus/AndroidTabbedDialog)

# AndroidTabbedDialog

![](https://raw.githubusercontent.com/krllus/AndroidTabbedDialog/master/screenshots/test.gif)

## Setup
Add jitpack to your project’s repositories.

```
repositories {
    // ...
    maven { url "https://jitpack.io" }
}
```

Then add Tabbed dialog to your Module’s dependencies

```
dependencies {
    implements 'com.github.krllus:AndroidTabbedDialog:v1.3.0'
}
```


## How to create tab dialogs:

```java
    private ArrayList<DialogTabItem> items;

    TabDialogFragment.createBuilder(getContext(), getFragmentManager(),
        new IViewPagerAdapterInterface() {
            @Override
            public CharSequence getTitle(int position) {
                return retrieveItems().get(position).getTitle();
            }

            @Override
            public Fragment getItem(int position) {
                return retrieveItems().get(position).getFragment();
            }

            @Override
            public int getCount() {
                return retrieveItems().size();
            }
        })
        .setTitle("Title")
        .setSubTitle("SubTitle")
        .setPositiveButtonText("Ok")
        .setNegativeButtonText("Cancel")
        .setNeutralButtonText("Hello")
        .setRequestCode(REQUEST_SIMPLE_DIALOG)
        .show();

    private ArrayList<DialogTabItem> retrieveItems() {
        if (items == null)
            items = new ArrayList<>();
        if (items.isEmpty()) {
            items.add(new DialogTabItem(PageFragment.newInstance(), "Tab 01"));
            items.add(new DialogTabItem(PageFragment.newInstance(), "Tab 02"));
            items.add(new DialogTabItem(PageFragment.newInstance(), "Tab 03"));
        }
        return items;
    }
```

### How to react on button press in your Activity/Fragment:
Simply implement interface `ISimpleDialogListener` in your Activity/Fragment. Listener's callbacks have `requestCode` parameter - you can use it if you have more dialogs in one Activity/Fragment.

## License
Copyright (c) 2018 João Carlos

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
