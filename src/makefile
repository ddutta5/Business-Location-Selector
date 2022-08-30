default: run

CLASSPATH = ".:./json-simple-1.1.jar"

Main.class: Main.java
	                javac -cp $(CLASSPATH) -d . MapADT.java
			javac -cp $(CLASSPATH) -d . HashTableMap.java
		        javac -cp $(CLASSPATH) -d . User.java
		        javac -cp $(CLASSPATH) -d . Building.java
			javac -cp $(CLASSPATH) -d . AppDataLoad.java
			javac -cp $(CLASSPATH) -d . AppBackEnd.java
			javac -cp $(CLASSPATH) -d . AppFrontEnd.java
			javac -cp $(CLASSPATH) -d . Main.java


run: Main.class
	        java -cp $(CLASSPATH) Main

test: TestApp.class
	        java -jar junit5.jar -cp . -c TestApp

TestApp.class: AppTester.java
	        javac -cp .:junit5.jar AppTester.java


clean:
	                \rm *.class
