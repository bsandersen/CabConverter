# Makefile for the build and release of the CabConverter product
# 28 November 2012
# B. Scott Andersen, NE1RD

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
	(cd /tmp; tar cf ~/Desktop/CabConverter.tar CabConverter)
	/bin/rm -rf /tmp/CabConverter

