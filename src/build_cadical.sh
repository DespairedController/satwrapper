#!/bin/bash
CADPATH=$(realpath ../cadical/cadical-master)
mkdir ${CADPATH}/build
cd ${CADPATH}/build
# for f in ../src/*.cpp; do g++ -O3 -DNDEBUG -DNBUILD -c -fPIC $f; done
LIBCADICALPATH=$(realpath ../../../libs/)
g++ -shared -o ${LIBCADICALPATH}libcadical.so `ls *.o | grep -v ical.o`
