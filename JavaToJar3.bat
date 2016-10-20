md class
rem Стороние классы скопированы в директорию lib
rem Это отражено в манифесте
javac -encoding UTF-8 ^
-classpath jar\lib\slf4j-api-1.7.21.jar;^
jar\lib\slf4j-simple-1.7.21.jar;^
jar\lib\sqlite-jdbc-3.8.11.2.jar ^
-sourcepath .\ ^
-d .\class ^
org\java3\lesson2\BooksCatalog.java
echo Main-Class: org.java3.lesson2.BooksCatalog > mymanifest
echo Class-Path: lib\slf4j-api-1.7.21.jar lib\slf4j-simple-1.7.21.jar lib\sqlite-jdbc-3.8.11.2.jar >> mymanifest
md jar
"%ProgramFiles%\Java\jdk1.8.0_92\bin\jar" cvfm jar\BooksCatalog.jar mymanifest -C class .
erase /Q /F mymanifest
pause