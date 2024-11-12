This is the README for the CLI service
Steps to run:
1) download the project from github
2) checkout master branch 
3) navigate to where project was downloaded to
4) make sure you're using jdk 1.8
5) run mvn clean install
6) run mvn exec:java
7) now the CLI should be running

To use the CLI
    Commands available are getMostRecentCookie
    the following format should be used to get most recent cookies out of a csv file - 
    getMostRecentCookie -f pathToFile/filename.csv -d 2018-09-12
