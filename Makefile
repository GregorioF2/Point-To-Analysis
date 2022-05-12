DLOUT = DLOutput
DLIN = DLInput
FINAL_DIR = src/main/java/final

clean:
	rm ./$(DLOUT)/*.csv
	rm ./$(DLIN)/*.facts

build:
	mvn clean install

run:
	java -cp target/leak-analysis-1.0-SNAPSHOT-jar-with-dependencies.jar  tp_final.PointsToAnalysis
	souffle --fact-dir=./$(DLIN)  --output-dir=./$(DLOUT)  $(FINAL_DIR)/final.dl
	java -cp target/leak-analysis-1.0-SNAPSHOT-jar-with-dependencies.jar  tp_final.ResultsInterpreter

full: build run
