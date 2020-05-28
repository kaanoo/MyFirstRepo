  Lamia Başak Amaç ~ 21601930
  Ege Kaan Eren ~ 21601625
  Alper Kandemir ~ 21703062
  Bora Fenari Köstem ~ 
  Azar Hasanaliyev ~ 21600591
  Kaan Yüksel ~ 
  
 BILCLUB
 That provides a social platform for university clubs.
 
 Current status: 
    Partly working...
 
 Not working: 
    The function for displaying club members remains because we did not have time to complete it.
The facility of student councill's permission to create clubis also missing. Manager profile is not present. 
Actually we finished it but could not integrate it to the project.

What remains to be done:
    Integration of manager profile, creating a system for student council and to display member of a certain club.

CONTRIBUTIONS
Lamia Başak Amaç : MapsActivity, MapsMain and StartActivity 
Alper Kandemir : StoryActivity, EditProfileActivity, FollowersActivity, PostActivity
Ege Kaan Eren : BilkentNewsActivity, RadioActivity and CreateEvent
Azar Hasanaliyev : LoginActivity, SignUpActivity, ManagerProfileActivity and HomeActivity
Bora Fenari Köstem : ChatClient Package
Kaan Yüksel : SearchBarActivity, CommentsActivity and OptionsActivity

To run project: merge the app.rar, build.rar and intermediates.rar with bilclub.rar properly. We had to upload separately because of 25mb file limitation. app directory includes build directory and build directory includes intermediates directory. Lastly, bilclub directory includes all.

Firebase instructions:
-for database: tools/firebase/realtime database/save and retrieve data/ connect and add the realtime database
-for storage: tools/firebase/storage/upload and download a file with cloud storage/ connect and add the cloud storage
-for auth: tools/firebase/authentication/email and password auth/ connect and add firebase auth 
-then go to https://console.firebase.google.com
-start it with testmode

Chat server instructions:
-for connection: open a serverSocket with a try - catch method
-for clients: accept client's socket with serverSocket.accept(); and add the clients to the client counter.
-for read messages: with BufferedReader create a listener for incoming messages from client.
-for broadcast: with a for loop, count all the client sockets and send the message with PrintwWriter

manifest permission:
manifest permission <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    
    
    
for maps function :
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="AIzaSyCEGsn34zxakTw-bmfcThC3PIX6iunmyWo"/>  //adding API Key to the manifest
        <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />      //adding manifest to the application

        <meta-data
            android:name="com.google.android.geo.AIzaSyCEGsn34zxakTw-bmfcThC3PIX6iunmyWo"
            android:value="AIzaSyCEGsn34zxakTw-bmfcThC3PIX6iunmyWo" />     //adding API Key to the manifest



for gradle dependencies use that format "implementation 'com.google.firebase:firebase-storage:19.1.1' "

dependencies:

-'com.google.firebase:firebase-auth:19.3.1':
Firebase Authentication provides backend services,
easy-to-use SDKs,and ready-made UI libraries to authenticate 
users to your app.It supports authentication using passwords, 
phone numbers,popular federated identity providers 

-'com.google.firebase:firebase-storage:19.1.1': Cloud Storage serves user-generated content,
such as photos or videos.

-'com.google.firebase:firebase-database:19.3.0':


-'androidx.constraintlayout:constraintlayout:1.1.3': ConstraintLayout allows you to create large and complex layouts with a flat view hierarchy 
(no nested view groups).

-'com.android.support:cardview-v7:28.0.0': This library adds support for the CardView widget, which lets you show information inside cards that have a consistent look on any app.



-'com.android.support:recyclerview-v7:28.0.0': to display a scrolling list of elements based on large data sets (or data that frequently changes). the main page has a lot of photos.

'com.google.android.gms:play-services-maps:17.0.0': adding google play services to the project

"com.google.android.gms:play-services-location:15.0.1": adding location services from google play to the project

'com.google.android.libraries.places:places:2.2.0':  adding places services from google play to the project (searching places)

'com.theartofdev.edmodo:android-image-cropper:2.8.+': simple image cropping library for Android.

'com.github.ittianyu:BottomNavigationViewEx:2.0.4': An android lib for enhancing BottomNavigationView. we used no item shifting mode

'com.github.shts:StoriesProgressView:3.0.0': Library that shows a horizontal progress like stories.

'com.rengwuxian.materialedittext:library:2.1.4': AppCompat v21 makes it easy to use Material Design EditText in our apps, but it's so limited. this has more features and it used in edit profile for editText blanks

'de.hdodenhof:circleimageview:3.1.0': for circle imageview

'com.github.bumptech.glide:glide:4.11.0' Glide is a fast and efficient open source media management and image loading framework. we could use Picasso but glide has more methods and it is faster at displaying images from cache. Also, glide supports gifs.



