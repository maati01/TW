## EXAMPLE RUN 

`mvn compile exec:java -D exec.mainClass=agh.tw.Main -D exec.args="arg1 arg2 arg3 arg4"`\
`mvn compile exec:java -D exec.mainClass=agh.tw.Main -D exec.args="10 100 true true"` \
where: \
arg1 - number of threads \
arg2 - iterations for the philosopher \
arg3 - save the result (true/false) \
arg4 - use the waiter (true/false)