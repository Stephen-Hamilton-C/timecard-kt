#!/bin/python3
import os
import re
import sys
from datetime import datetime

# cd to rootProject dir
SCRIPT_PATH = os.path.realpath(__file__)
SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
os.chdir(SCRIPT_DIR)
os.chdir("../../")
ROOT_PROJECT_PATH = os.getcwd()
# Now we are at rootProject directory. Let's do this.

sys.path.append('deploy')
from version import getVersion

VERSION = getVersion()

if len(sys.argv) > 1 and sys.argv[1].strip() != "":
	VERSION = VERSION + "-" + sys.argv[1].strip()
	print("Debian version found, VERSION is "+VERSION)
print()

# Setup Changelog constants
CHANGELOG_HEADER = "timecard-kt ("+VERSION+") focal; urgency=medium\n\n"
CHANGELOG_FOOTER = "\n -- Stephen Hamilton <stephen.hamilton.c@gmail.com>  "+datetime.utcnow().strftime("%a, %d %b %Y %H:%M:%S")+" +0000\n"
CHANGELOG_ITEM_PREFIX = "  * "

CHANGELOG = "CHANGELOG.md"
DEB_CHANGELOG = os.path.join("deploy", "debian", "debian", "changelog")

# Checks if the line is a version header
def findVersion(fileLine: str):
	if not fileLine.startswith("# "): return False
	if re.search(".\..\.. - ", fileLine):
		print("Found version header: `"+fileLine+"`")
		return True
	return False

# Checks if the line is a feature header
def getHeader(fileLine: str) -> str:
	if fileLine.startswith("## "):
		if fileLine.endswith(" <!-- omit in toc -->"):
			header = fileLine[3:-21]
		else:
			header = fileLine[3:]
		print("Found feature header: "+header)
		return header.strip()

# Checks if the line is a changelog list item
def getListItem(fileLine: str) -> str:
    if fileLine.startswith("- "):
        return fileLine[2:].strip()

# Get all changelog list items
listItems = []
with open(CHANGELOG, 'r') as file:
	header: str = None
	foundVersion = False
	for fileLine in file.readlines():
		fileLine = fileLine.strip()
		if not foundVersion:
			foundVersion = findVersion(fileLine)
		else:
			if findVersion(fileLine):
				print("Another version header found. We're done now.")
				break
			elif fileLine.startswith("## "):
				header = getHeader(fileLine)

			listItem = getListItem(fileLine)
			if listItem != None:
				if header != None:
					item = header+": "+listItem
					print("Found listItem with header: "+item)
					listItems.append(item)
				else:
					print("Found listItem without header: "+listItem)
					listItems.append(listItem)

# Write the listItems to the debian changelog
with open(DEB_CHANGELOG, 'w') as file:
    file.write(CHANGELOG_HEADER)
    
    for listItem in listItems:
        file.write(CHANGELOG_ITEM_PREFIX+listItem+"\n")
    
    file.write(CHANGELOG_FOOTER)
