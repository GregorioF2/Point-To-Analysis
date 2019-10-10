Leak sensible vairables analysis.

To test the analsysis with the example implemented on `src/main/java/examples`, just run from `srs` folder the command

```
mvn clean install compile

java -cp target/leak-analysis-1.0-SNAPSHOT-jar-with-dependencies.jar  Launcher -keep-line-number -f J -w -pp -cp target/classes/ examples.Main
```
