mvn clean package -DskipTests
mvn exec:java -Dexec.mainClass="scrapper.TextBrowser" -Dexec.args=$1
