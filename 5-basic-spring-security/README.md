# Spring Boot + Security + OAUTH2
In this application, you will learn 
* How to use Spring Boot + Security + OAuth2 token to validate the user account using Google API SSO.

Steps to configure clientId and clientSecret using Google Cloud Platform.
1. Login / Create an account in [Google Cloud Platform](https://console.cloud.google.com)
2. Navigate to APIs & Services -> Credentials -> OAuth consent screen
3. Enter your Application name (This name will be shown on your Google Login Page)
4. If you would like to add more Scopes, you can use "Add scope" button.
5. Enter your "Authorized domains" name (ex. [http://localhost:8080](http://localhost:8080))
6. Enter your "Application Homepage link" URL (ex. [http://localhost:8080/login](http://localhost:8080/login))
7. Save your changes.
8. After changes has been saved, you will get a popup there it will show CLIENT_ID and CLIENT_SECRET
9. Use those two values in your application.properties files.
10. If you would like to see the SSO token information, you can use [http://localhost:8080/user](http://localhost:8080/user). It will show the SSO token in JSON format.
