JFLAGS = -g -d bin -classpath "libs/craftbukkit1.4.7.jar:libs/craftbukkit1.5.2.jar:libs/craftbukkit1.5_R2.jar:libs/craftbukkit1.6.1.jar:libs/craftbukkit1.6.2.jar:libs/jcsv-1.4.0.jar:libs/worldedit-5.5.5.jar:bin"
JC = javac
JAR = jar
JARFLAGS = cf GriefLog.jar -C bin

all:
	$(JC) $(JFLAGS) @sourcefiles
jar:
	$(JAR) $(JARFLAGS) . plugin.yml config.yml LICENCE
clean: 
	rm -rf bin/*
