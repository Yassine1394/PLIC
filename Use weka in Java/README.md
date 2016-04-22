In my project, for weka CSV2SU.java and CSV2Arff.java referenced jar/weka.jar, the others referenced jar/weka-3.6.10.jar.

#### Use weka in Java

https://weka.wikispaces.com/Use+WEKA+in+your+Java+code

#### weka develop doc version

http://weka.sourceforge.net/doc.dev/

#### compile method

``` 
javac -cp ";./jar/weka-3.6.10.jar" SU.java

javac -cp ";./jar/weka.jar" CSV2Arff.java

javac -cp ";./jar/weka.jar" CSV2SU.java
``` 

#### run method

``` 
java -cp ";./jar/weka-3.6.10.jar" SU SRBCT.arff

java -cp ";./jar/weka.jar" CSV2Arff SRBCT.csv SRBCT2.arff

java -cp ";./jar/weka.jar" CSV2SU SRBCT.csv
``` 

```
java -cp ";./jar/weka.jar" weka.core.converters.CSVLoader SRBCT.csv > SRBCT2.arff
```
File CSV2Arff.java doesn't work well(error below), but command work(above!).

``` 
java -cp ";./weka.jar" CSV2Arff SRBCT.csv SRBCT2.arff
Cannot create a new output file. Standard out is used.
Exception in thread "main" java.io.IOException: Cannot create a new output file (Reason: java.io.IOException: File already exists.). Standard out is used.
        at weka.core.converters.AbstractFileSaver.setDestination(AbstractFileSaver.java:421)
        at CSV2Arff.main(CSV2Arff.java:44)
```

---

### What's more

If file CSV2SU.java and SRBCT.csv are under folder jar, and we want compile and run at current dir(Use weka in Java). Firstly, we shoule add **package jar;** in the top of file CSV2SU.java. And then:

```
javac -cp ";./jar/weka.jar" jar/CSV2SU.java

java -cp ";./jar/weka.jar" jar.CSV2SU ./jar/SRBCT.csv
```

If you use C++ in windows, you shoule include **"windows.h"**, then use **system("javac -cp \";./jar/weka.jar\" jar/CSV2SU.java");**.