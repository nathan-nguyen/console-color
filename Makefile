test:
	mvn test

install:
	mvn clean install

run-server:
	mvn compile exec:java -Dexec.mainClass="com.noiprocs.App" -Dexec.args="pc gnik server localhost 8080"

install-run-server: install run-server
