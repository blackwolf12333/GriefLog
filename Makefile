JFLAGS = -g -d bin -classpath "libs/craftbukkit1.4.7.jar:libs/craftbukkit1.5.2.jar:libs/craftbukkit1.5_R2.jar:libs/craftbukkit1.6.1.jar:libs/craftbukkit1.6.2.jar:libs/jcsv-1.4.0.jar:libs/craftbukkit-1.6.4.jar:libs/craftbukkit1.7.2.jar:libs/craftbukkit-1.7.9.jar:libs/craftbukkit-1.7.5.jar:libs/worldedit-5.6.2.jar:bin"
JC = javac
JAR = jar
JARFLAGS = cf GriefLog.jar -C bin

all:
	$(JC) $(JFLAGS) @sourcefiles
jar:
	$(JAR) $(JARFLAGS) . plugin.yml config.yml LICENCE
clean: 
	rm -rf bin/*
jarl:
	$(JAR) cf ~/server/plugins/GriefLog.jar -C bin . plugin.yml config.yml LICENCE
build:
	find src/tk/blackwolf12333/grieflog -maxdepth 5 -type f -name "*.java" > sourcefiles
