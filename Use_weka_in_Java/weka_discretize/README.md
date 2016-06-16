For weka DiscretizeData.java referenced ../jar/weka.jar.

If your data organized as samples*features, should first run data_transform.cpp to change the data format.

Also, you can run it in current directory or others. Just change the compile and run command as discribed in ../README.md;

If you want run it in another directory, add the **package name** in the first line of File DiscretizeData.java.

The aim is discretize train-0.csv and test-0.csv; But, the help of data_transform.cpp help to product *-0-trans.csv; So, the flow is train-0.csv -> train-0-trans.csv(by data_transform.cpp) -> train-0-discretize.csv.