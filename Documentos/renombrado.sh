#!/bin/sh
#ftrim by vrS
find . -name '* *' | sort | while read FILE
do
NEWFILE=`echo ${FILE} | sed 's/ /_/g;'`
mv "${FILE}" ${NEWFILE}
echo ${NEWFILE}
done
