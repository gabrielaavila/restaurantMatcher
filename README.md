# Restaurant Matcher 
##Overview 
This is a Web REST Application that provides an endpoint to search through a list of restaurants (which is already loaded in the application). The list of restaurants is provided through an .csv file located in a csv folder inside the root folder.

##Technologies used
* Spring Boot 
* Java 8
* Gradle

##How to run 
Execute the following steps:
1. Open the terminal in the root folder of the repository 
2. Run command "./gradlew bootJar"
3. Access folder build/libs and move the .jar file to the root folder of the repository
4. Run command "java -jar restaurantMatcher-0.0.1-SNAPSHOT.jar"

##How to test
To run the unit tests, just execute the command "./gradlew test"

To test the API, execute the "How to run" steps and then, execute the following:

1. The service will be available at port 8080. To find a restaurant, call the URl http://localhost:8080/restaurant-matcher/matches and provide one or more of the 5 query parameters below: 
   - restaurantName : Should be a string
   - cuisine : Should be a string
   - distance: Should be an integer 
   - rating: Should be an integer
   - price: Should be an integer 

You could call the URL using your browser or an application such as Postman; 

For example, if you want to search for all the restaurants which the word "grill" in its name and within a distance of 5 miles from the office, you should use the following URL: **http://localhost:8080/restaurant-matcher/matches?restaurantName=grill&distance=5** 


##Assumptions
1. If none of the parameters is given, then the application will return an empty list. 
2. The name's search (restaurant's name and cuisine's name) will be based on the words or prefix of the words that compose the name, which means, The search function will return all the results in which this string is a word or a word's prefix of the restaurant's name. 
   - For example, if "rest" is given as a restaurant's name parameter, it could return the names "rest space", "Restaurant Of Madeleine" or even "Delicious Restaurant", but it will never return a name such as "Camile Spacerest" (in this case, "rest" is a suffix of a word in the restaurant's name); 