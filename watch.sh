#mvn clean package -DskipTests
mvn exec:java -Dexec.mainClass="scrapper.sgx.SGXSymbolWatcher" -Dexec.args=$@
