# AhoyTestProject

Test project for AHOY.

How to work with it:

1.) In the main menu ou have EditText, write city name in it, it will make api request and find weather info for this location;

2.) If you press "Add to favourites" this city will be saved in local DB;

3.) Also there is a Swtich, just to swothc between calsius and fahrenheit unit;

4.) At the right mottom you will see a button with a star, if you press it will open BottomSheet with favourite cities;

5.) If you press on city it will find a eather info for this city;

6.) If you press "Delete" it will delete this city from local DB;

7.) Also at the bottom you could see a button with a placemark. If you press it will find a weather info at your location;

8.) Added Service to push notification every day at 6 AM. It would show your current location and what current temperature is outside.

P.S. Didn't polished this app enough (for example I'm not checking if permission is not granted (when you disallow to share geolocation))
because I have a job to do, but I made all tasks.

P.S.S I used different api to gather weather info for few days further, they are limited to 10 request a day (had 1 already to check how it's working, be careful. If limit is out, ask me, I will disable daily forecast.)
