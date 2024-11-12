This is the README for the CLI service
Steps to run:
1) download the project from github
2) checkout master branch 
3) navigate to where project was downloaded to
4) make sure you're using jdk 1.8
5) run mvn clean install
6) run mvn exec:java
7) now the CLI should be running

## Using the CLI

### Available Commands
- **getMostRecentCookie**

### Command Format
To retrieve the most recent cookies from a CSV file, use the following format:
getMostRecentCookie -f pathToFile/filename.csv -d YYYY-MM-DD


### Testing the Application
To test the application, you can run: 
getMostRecentCookie -f src/main/resources/test.csv -d 2018-09-12

You should expect to see the following output printed in your terminal, each on a separate line:
asd1 asd2 asd3

### Description
The `getMostRecentCookie` command processes a CSV file and returns the most active cookie for a specified day.