#!/bin/python3
import os
import re
from datetime import datetime

# TODO: Need to pass in version at some point
CHANGELOG_HEADER = "timecard-kt (1.0.0) focal; urgency=medium\n\n"
CHANGELOG_FOOTER = "\n -- Stephen Hamilton <stephen.hamilton.c@gmail.com>  "+datetime.utcnow().strftime("%a, %d %b %Y %H:%M:%S")+" +0000"
CHANGELOG_ITEM_PREFIX = "  * "

SCRIPT_PATH = os.path.realpath(__file__)
SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
os.chdir(SCRIPT_DIR)
os.chdir("../../")
ROOT_PROJECT_PATH = os.getcwd()

# Now we are at rootProject directory. Let's do this.
CHANGELOG = "CHANGELOG.md"
DEB_CHANGELOG = os.path.join("deploy", "debian", "debian", "changelog")

if os.path.exists(DEB_CHANGELOG):
	os.remove(DEB_CHANGELOG)

def findVersion(fileLine: str):
	if not fileLine.startswith("# "): return False
	if re.search(".\..\.. - ", fileLine):
		print("Found version header: `"+fileLine+"`")
		return True
	return False

def getHeader(fileLine: str) -> str:
	if fileLine.startswith("## "):
		if fileLine.endswith(" <!-- omit in toc -->"):
			header = fileLine[3:-21]
		else:
			header = fileLine[3:]
		print("Found feature header: "+header)
		return header.strip()

def getListItem(fileLine: str) -> str:
    if fileLine.startswith("- "):
        return fileLine[2:].strip()

listItems = []

with open(CHANGELOG, 'r') as file:
	fileLines = file.readlines()
	header: str = None
	foundVersion = False
	for fileLine in fileLines:
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

with open(DEB_CHANGELOG, 'w') as file:
    file.write(CHANGELOG_HEADER)
    
    for listItem in listItems:
        file.write(CHANGELOG_ITEM_PREFIX+listItem+"\n")
    
    file.write(CHANGELOG_FOOTER)
