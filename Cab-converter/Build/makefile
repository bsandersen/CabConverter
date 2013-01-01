# Makefile for the build and release of the CabConverter product
# 28 November 2012
# B. Scott Andersen, NE1RD

clean:
	/bin/rm -rf /tmp/CabConverter

jar:
	ant
	( cd ../bin/; jar cfm ../CabConverter.jar ../Build/Manifest.txt *)

release:
	make jar
	mkdir /tmp/CabConverter
	mkdir /tmp/CabConverter/src/
	mkdir /tmp/CabConverter/Documentation
	mkdir /tmp/CabConverter/CCContests
	mkdir /tmp/CabConverter/Build
	mkdir /tmp/CabConverter/JavaDoc
	cp JarIt        /tmp/CabConverter/Build
	cp Makefile     /tmp/CabConverter/Build
	cp Manifest.txt /tmp/CabConverter/Build
	cp build.xml    /tmp/CabConverter/Build
	cp ../CabConverter.jar /tmp/CabConverter
	cp ../Documentation/*.rtf /tmp/CabConverter/Documentation
	cp -r ../Documentation/*.rtfd /tmp/CabConverter/Documentation
	cp ../Documentation/README.rtf /tmp/CabConverter
	cp ../CCContests/*.xml /tmp/CabConverter/CCContests
	mkdir /tmp/CabConverter/src/com
	mkdir /tmp/CabConverter/src/com/bsandersen
	mkdir /tmp/CabConverter/src/com/bsandersen/CabConverter
	cp ../src/*.java /tmp/CabConverter/src
	cp ../src/com/bsandersen/CabConverter/*.java /tmp/CabConverter/src/com/bsandersen/CabConverter
	echo Moving JavaDoc...
	(cd ../JavaDoc; tar cf - * | (cd /tmp/CabConverter/JavaDoc; tar xf - ))
	tar cf ../Release/CabConverter.tar /tmp/CabConverter
	/bin/rm -rf /tmp/CabConverter

