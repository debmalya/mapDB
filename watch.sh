#mvn clean package -DskipTests
mvn exec:java -Dexec.mainClass="scrapper.sgx.SGXSymbolWatcher" -Dexec.args=$@
#mvn exec:java -Dexec.mainClass="scrapper.sgx.SGXSymbolWatcher" -Dexec.args="OV8 OV8 1.04"
