#mvn clean package -DskipTests
mvn exec:java -Dexec.mainClass="scrapper.sgx.BusinessTimesScrapper" -Dexec.args=$1
