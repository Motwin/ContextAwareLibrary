#!/bin/sh

CONFIGURATION=$1
SUFFIX=$2

if ! [ -f ContextAwareLib.DoxyFile ] 
then 
  echo "Doxygen config file does not exist"
  exit 1
fi

if [ -z "$DOXYGEN_PATH" ]; 
then
  DOXYGEN_PATH=/Applications/Doxygen.app/Contents/Resources/doxygen
fi

if ! [ -f $DOXYGEN_PATH ] 
then 
	DOXYGEN_PATH=/usr/local/bin/doxygen
fi

if ! [ -f $DOXYGEN_PATH ] 
then 
	echo "Doxygen not found (neither in /Applications nor in /usr/local/bin). No documentation produced."
	exit 1
fi

#  Run doxygen on the config file.
echo "\nGenerating documentation..."

( cat ./ContextAwareLib.DoxyFile ; echo "OUTPUT_DIRECTORY = ../../Distribution$SUFFIX/$CONFIGURATION/ContextAwareLibDocumentation" ; echo "INPUT = ../../Distribution$SUFFIX/$CONFIGURATION/ContextAwareLib/Headers  ./") | $DOXYGEN_PATH -

echo "\nGenerating Xcode DocSet..."
make -C ../../Distribution$SUFFIX/$CONFIGURATION/ContextAwareLibDocumentation install >& /dev/null

exit 0
