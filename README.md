# mapDB
Example of mapDB , JSoup, CQEngine, JetBrains xodus,ibm streams and JavaFX.

It takes stock values from https://www.google.com/finance and stores into mapDB file database.

Put https://http://finance.yahoo.com/q values into CQCollection and runs query.

A stock based application using jsoup, mapdb and google finance. Storing stock values in mapDB.

Run worldMarket.sh and if everything is fine, you view the screen like below.

![World Market with Java FX, MapDB, GoogleFinance](WorldMarketScreenShot.png)

To install added jars in lib folder
mvn install:install-file -Dfile=./lib/com.ibm.streamsx.topology.jar -DgroupId=com.ibm -DartifactId=streamsx -Dversion=1.4 -Dpackaging=com.ibm

mvn install:install-file -Dfile=./lib/com.ibm.streams.operator.jar -DgroupId=ibm.streams -DartifactId=operator -Dversion=4.0 -Dpackaging=com.ibm
