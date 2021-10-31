# Krishi Mandi

Krishi Mandi is an Android app developed using Kotlin.

## Download Link

[Click here](https://drive.google.com/file/d/1zsNnv8ApPqjAQqJh3SPAUGspKq7soQdj/view?usp=sharing) to download.

## About

An app to make API Request calls to an API through Retrofit Library in Android.

Used features and Libraries:
- Recycler View
- ViewModel
- Kotlin Coroutines
- Retrofit
- Picasso
- Room Persistence Library


## Requirements

Add the Following dependencies in your build.gradle(module):

```bash

    //ROOM
    implementation 'androidx.room:room-ktx:2.3.0'
    kapt "androidx.room:room-compiler:2.3.0"

    //VIEWMODEL
    implementation "androidx.lifecycle:lifecycle-extensions:2.2.0"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"

    //RETROFIT
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

    //Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.1"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"

    //Picasso
    implementation 'com.squareup.picasso:picasso:2.71828'

```