In my project, for weka CSV2SU.java and CSV2Arff.java referenced jar/weka.jar, the others referenced jar/weka-3.6.10.jar.

#Use weka in Java
https://weka.wikispaces.com/Use+WEKA+in+your+Java+code

#weka develop doc version
http://weka.sourceforge.net/doc.dev/

#compile method
javac -cp ";./weka-3.6.10.jar" SU.java
javac -cp ";./weka.jar" CSV2Arff.java
javac -cp ";./weka.jar" CSV2SU.java

#run method
java -cp ";./weka-3.6.10.jar" SU SRBCT.arff
java -cp ";./weka.jar" CSV2Arff SRBCT.csv SRBCT2.arff
java -cp ";./weka.jar" CSV2SU SRBCT.csv

> java -cp ";./weka.jar" weka.core.converters.CSVLoader SRBCT.csv > SRBCT2.arff

File CSV2Arff.java doesn't work well, but command above.

> java -cp ";./weka.jar" CSV2Arff SRBCT.csv SRBCT2.arff
> Cannot create a new output file. Standard out is used.
> Exception in thread "main" java.io.IOException: Cannot create a new output file (Reason: java.io.IOException: File already exists.). Standard out is used.
>         at weka.core.converters.AbstractFileSaver.setDestination(AbstractFileSaver.java:421)
>         at CSV2Arff.main(CSV2Arff.java:44)