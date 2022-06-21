#!/bin/python3
import os
import sys

# cd to rootProject dir
SCRIPT_PATH = os.path.realpath(__file__)
SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
os.chdir(SCRIPT_DIR)
os.chdir("../../")
ROOT_PROJECT_PATH = os.getcwd()
# Now we are at rootProject directory. Let's do this.

sys.path.append("deploy")
from version import getVersion

VERSION = getVersion()

fileLines = []
with open("deploy/snap/snapcraft.yaml", "r") as file:
	fileLines = file.readlines()

with open("deploy/snap/snapcraft.yaml", "w") as file:
	for fileLine in fileLines:
		if fileLine.startswith("version: "):
			fileLine = "version: '"+VERSION+"'\n"
			print("Set version line:")
			print(fileLine[:-1])
		file.write(fileLine)
