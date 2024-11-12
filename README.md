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
        The following format should be used to get most recent cookies out of a csv file - 
        getMostRecentCookie -f pathToFile/filename.csv -d 2018-09-12
        to test the application you can use getMostRecentCookie -f src/main/resources/test.csv -d 2018-09-12 and you should expect 
        to see asd1, asd2, asd3 printed in your terminal on separate lines.

        The getMostRecentCookie command will process a csv file and return the most active cookie for a specific day.
