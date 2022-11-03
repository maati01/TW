## EXAMPLE RUN 

`mvn compile exec: java -D exec.mainClass = agh.tw.Main -D exec.args = "10 100 true true"` \
where: \
first arg - number of threads \
second arg - iterations for the philosopher \
third arg - save the result (true/false) \
fourth arg - use the waiter (true/false)