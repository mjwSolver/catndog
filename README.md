
# Introduction

## What is this Project About?

This project implements an API for random fact and image generation for both Cat and Dog. It also implements JSON reader to read Trivia data from a local JSON file and express it's content in simple code logic.

## What does this project cover?

How to query text and image from API's with Retrofit.
How to display acquired data. 
How to navigate with a simple Cat or Dog state. 
How to create JSON files in Assets Directory

# Overview

## Dependencies and Resources

### Android Manifest Permissions

Enable Application to access the Internet
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
- Permits retrofit to make a query to the API via the Internet. 
### Gradle Dependencies

Adding Additional Dependencies
```groovy
implementation 'com.squareup.retrofit2:retrofit:2.9.0'  
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'  
implementation 'com.squareup.picasso:picasso:2.8'
```

- Retrofit - handles querying the API via the URL
- Converter-Gson - handles the conversion of incoming JSON responses into Java Model Classes
- Picasso - After retrieving image data, this package helps display the image. 

### API Used

*Live Endpoint* is used for the `baseUrl()` function when building Retrofit.

*API Path* is the url path inside the API interface files that completes the baseUrl upon execution.
- For example: `CatFactAPI.java` will have 
	- element `@GET("/facts/random")` 
	- to complete `baseUrl("https://cat-fact.herokuapp.com")`
#### Cat Fact

Live Endpoint
"https://cat-fact.herokuapp.com"

API Path
"/facts/random"
#### Dog Fact

Live Endpoint
"https://dog-api.kinduff.com"

API Path
"/api/facts"
#### Cat Image

Live Endpoint
"https://cat-fact.herokuapp.com"

API Path
"/V1/images/search"
#### Dog Image

Live Endpoint
"https://api.thedogapi.com/"

API Path
"/V1/images/search"
### Tools Used

- Android Studio - Main IDE
- Github (Optional) - for Versioning Control
- Android SDK 34
- Pixel 3a API 34 - Android Virtual Device
- JSON Formatter - Online website to help us understand the JSON output from API's
	- https://jsonformatter.curiousconcept.com/#
## Break Down of Essential Code

### Java Model Classes and Interfaces

There are two essential files to make JSON querying and conversion possible. 
1. The Java Model Classes, is the Java representation of the JSON File we're querying.
2. The API Interface File, is the Java Interface that completes the baseUrl and defines which Java Model Class the response should be converted to.

Here's a JSON Output from the Dog Fact API.

```json
{
   "facts":[
      "The fastest breed, the Greyhound, can run up to 44 miles per hour."
   ],
   "success":true
}
```

This is the Java Model Class, notice how the *facts* and *success* are mapped out from JSON to Java with the `@SerializeName` tag. 

```java
public class DogFact {  
  
    // Kinduff API Serialization  
    @SerializedName("facts")  
    private ArrayList<String> facts;  
  
    @SerializedName("success")  
    private boolean success;  
  
    public ArrayList<String> getText() {  
        return this.facts;  
    }  
  
    public boolean getStatus() {  
        return this.success;  
    }  
  
}
```

Finally, here's the API Interface file that connects the `baseUrl` with the web path to complete the querying process. The response is then accepted in the form of `Call<DogFact>`. 

```java
public interface DogFactAPI {  
    
    @GET("/api/facts")  
    Call<DogFact> getDogFact();  
    
}
```

### Generating Image and Facts

Most API Querying will follow a code structure as follows:

```java
private void generateCatImage() {  
  
    ImageView animalImage = findViewById(R.id.animalImageView);  
  
    // Generate Cat Image  
    Retrofit retrofit = new Retrofit.Builder()  
            .baseUrl("https://api.thecatapi.com")  
            .addConverterFactory(GsonConverterFactory.create())  
            .build();  
  
    CatImageAPI catImageApi = retrofit.create(CatImageAPI.class);  
    Call<List<CatImage>> call = catImageApi.getData();  
    call.enqueue(new Callback<List<CatImage>>() {  
        @Override  
        public void onResponse(Call<List<CatImage>> call, Response<List<CatImage>> response) {  
            if (response.isSuccessful()) {  
                List<CatImage> data = response.body();  
                String imageUrl = data.get(0).getUrl();  
                Picasso.get().load(imageUrl).into(animalImage);  
                // Will be a Cat  
            }  
        }  
  
        @Override  
        public void onFailure(Call<List<CatImage>> call, Throwable t) {  
            System.out.println("Failed to Acquire Cat Image");  
        }  
    });  
  
}
```

There are some exceptions to accommodate certain responses from each API.

The Cat Fact API has a tendency to return gibberish / Russian text and thus a conditional statement using ReGeX expression is used to filter the content. 

```java
String text = response.body().getText();  
if (Pattern.matches(".*\\p{InCyrillic}.*", text)) {  
    animalFactText.setText("Bro this is Russian");  
} else {  
    animalFactText.setText(text);  
}
```

Notice the presence of the `List<>` object in Image querying but it's absence in Fact querying.
- Difference is caused by how the Image API's will return JSON Array structures and to accommodate the JSON to Java Model class conversion, we use `List<>`. 

```java
// Example of Getting CatImage being a List
Call<List<CatImage>> call = catImageApi.getData();

// Example of CatFact being a singular objet
Call<CatFact> factCall = catFactApi.getCatFact();
```

The Dog Fact API returns facts in the form of a JSONArray. The following code block highlights the difference in comparison to the other Image and Fact API used thus far.

```java
	factCall.enqueue(new Callback<DogFact>() {  
		@Override  
		public void onResponse(...) {  
			if (response.isSuccessful()) {  
				// Instead of "String" class we use "ArrayList".
				ArrayList<String> text = response.body().getText();
				  
				// Because it's an ArrayList, we use ".get(0)" 
				// to access the first element inside the ArrayList.
				animalFactText.setText(text.get(0));  
			}  
		}
```

## Folder Structure and Essential Files

```java
├── app
│   ├── build
│   │   ├── generated
│   │   │   ├── ....
│   │   ├── intermediates
│   │   │   ├── ...
│   │   │   ├── packaged_manifests
│   │   │   │   └── debug
│   │   │   │       ├── AndroidManifest.xml
│   │   │   │       └── output-metadata.json
│   │   │   ├── packaged_res
│   │   │   │   └── debug
│   │   │   │       ├── drawable
│   │   │   │       │   ├── cat_default.jpg
│   │   │   │       │   ├── dog_default.jpg
│   │   │   │       │   ├── ic_dashboard_black_24dp.xml
│   │   │   │       │   ├── ic_home_black_24dp.xml
│   │   │   │       │   ├── ic_launcher_background.xml
│   │   │   │       │   ├── ic_launcher_foreground.xml
│   │   │   │       │   └── ic_notifications_black_24dp.xml
│   │   │   │       ├── layout
│   │   │   │       │   ├── activity_generator.xml
│   │   │   │       │   ├── activity_main.xml
│   │   │   │       │   ├── activity_trivia.xml
│   │   │   │       │   ├── activity_trivia_imgenerate.xml
│   │   │   │       │   ├── fragment_dashboard.xml
│   │   │   │       │   ├── fragment_home.xml
│   │   │   │       │   └── fragment_notifications.xml
│   │   │   │       ├── ...
│   │   │   ├── ...
│   │   ├── ...
│   ├── build.gradle
│   ├── libs
│   ├── proguard-rules.pro
│   └── src
│       ├── androidTest
│       │   └── java
│       │       └── ...
│       ├── main
│       │   ├── AndroidManifest.xml
│       │   ├── assets
│       │   │   └── catdogtrivia.json
│       │   ├── java
│       │   │   └── com
│       │   │       └── example
│       │   │           └── marcellcatdogapp
│       │   │               ├── AnimalGenerator.java
│       │   │               ├── Entity
│       │   │               │   ├── AnimalFact.java
│       │   │               │   ├── AnimalFactAPI.java
│       │   │               │   ├── AnimalImage.java
│       │   │               │   ├── AnimalImageAPI.java
│       │   │               │   ├── AnimalKey.java
│       │   │               │   ├── Fact
│       │   │               │   │   ├── CatFact.java
│       │   │               │   │   ├── CatFactAPI.java
│       │   │               │   │   ├── DogFact.java
│       │   │               │   │   └── DogFactAPI.java
│       │   │               │   ├── Image
│       │   │               │   │   ├── CatImage.java
│       │   │               │   │   ├── CatImageAPI.java
│       │   │               │   │   ├── DogImage.java
│       │   │               │   │   └── DogImageAPI.java
│       │   │               │   └── Trivia
│       │   │               │       ├── CatTrivia.java
│       │   │               │       ├── CatTriviaList.java
│       │   │               │       ├── DogTrivia.java
│       │   │               │       └── DogTriviaList.java
│       │   │               ├── MainActivity.java
│       │   │               ├── Trivia.java
│       │   │               ├── TriviaImgGenerate.java
│       │   │               └── ui
│       │   └── res
│       │       ├── drawable
│       │       │   ├── cat_default.jpg
│       │       │   ├── dog_default.jpg
│       │       │   ├── ic_dashboard_black_24dp.xml
│       │       │   ├── ic_home_black_24dp.xml
│       │       │   ├── ic_launcher_background.xml
│       │       │   ├── ic_launcher_foreground.xml
│       │       │   └── ic_notifications_black_24dp.xml
│       │       ├── layout
│       │       │   ├── activity_generator.xml
│       │       │   ├── activity_main.xml
│       │       │   ├── activity_trivia.xml
│       │       │   ├── activity_trivia_imgenerate.xml
│       │       │   ├── fragment_dashboard.xml
│       │       │   ├── fragment_home.xml
│       │       │   └── fragment_notifications.xml
│       │       ├── ...
│       └── test
│           └── ...
├── build.gradle
├── gradle
│   └── wrapper
├── gradle.properties
├── gradlew
├── gradlew.bat
├── local.properties
└── settings.gradle
```

