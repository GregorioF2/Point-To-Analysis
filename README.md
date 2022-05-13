**Requirements**
- *Maven:*
  - download: http://maven.apache.org/download.cgi
  - install: http://maven.apache.org/install.html

- *Java 8:*
  - install https://github.com/shyiko/jabba
  - run jabba install zulu@1.8.92

- *Souffle:*
  - brew install --HEAD souffle-lang/souffle/souffle


```(With higher version of java, the following error appear `java.lang.RuntimeException: Error: cannot find rt.jar.`. Error kindof explained in here https://github.com/eclipse-cognicrypt/CogniCrypt/issues/202#issuecomment-431386825)```


## Steps to run the solution

- make build
- make run

## Test cases
On `src/main/java/final/test` exist diferent test cases to run the analysis on. By default the solution will run the `CaseOne` but to run the other ones for example must run the command: `make class=CaseTwo run `