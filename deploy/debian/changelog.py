#!/bin/python3
import os
import re
from datetime import datetime
from enum import Enum

# TODO: Need to pass in version at some point
CHANGELOG_HEADER = "timecard-kt (1.0.0) focal; urgency=medium\n"
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

class State(Enum):
	FIND_VERSION = 1
	FIND_HEADER = 2
	GET_LIST_ITEMS = 3
	FIN = 4


state = State.FIND_VERSION

def findVersion(fileLine: str):
	global state
	if not fileLine.startswith("# "): return
	if re.search(".\..\.. - ", fileLine):
		print("Found version header:")
		print(fileLine)
		state = State.FIND_HEADER

def getHeader(fileLine: str) -> str:
	global state
	pass

def getListItem(fileLine: str) -> str:
	global state
	pass

listItems = []

with open(CHANGELOG, 'r') as file:
	fileLines = file.readlines()
	header = ""
	for fileLine in fileLines:
		fileLine = fileLine.strip()
		if state == State.FIND_VERSION:
			findVersion(fileLine)
		elif state == State.FIND_HEADER:
			header = getHeader(fileLine)
		elif state == State.GET_LIST_ITEMS:
			listItem = getListItem(fileLine)
			if listItem != None:
				listItems.append(header+": "+listItem)
		elif state == State.FIN:
			break
			
