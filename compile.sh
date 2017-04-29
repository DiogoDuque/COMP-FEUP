#!/bin/bash

echo "--------------JJTREE--------------"
jjtree teste.jjt
ret="$?"
if [ $ret -ne 0 ]; then
	echo "failed jjt with $ret";
	return;
fi
echo "\njjt returned with $ret\n"

echo "--------------JAVACC--------------"
javacc teste.jj
ret="$?"
if [ $ret -ne 0 ]; then
	echo "failed jj with $ret";
	return;
fi
echo "\njavacc returned with $ret\n"

echo "--------------JAVAC--------------"
javac *.java
ret="$?"
if [ $ret -ne 0 ]; then
	echo "failed javac with $ret";
	return;
fi
echo "\njavac returned with $ret\n"
