#!/usr/bin/env python

import sys
import commands

s = commands.getoutput("cat "+sys.argv[1])
s = s.replace("MPLATFORM_DEPRECATED_WITH_MESSAGE", "")
s = s.replace("MPLATFORM_DEPRECATED", "")
print s


