VERSION = "1.2.0"

import os
import subprocess

# cd to rootProject dir
SCRIPT_PATH = os.path.realpath(__file__)
SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
os.chdir(SCRIPT_DIR)

APP_CLASS_PATH = os.path.join("src", "main", "kotlin", "app", "shamilton", "timecardkt", "App.kt")

# Add SNAPSHOT to version along with git commit if branch is not `main`
# https://stackoverflow.com/a/4760517
gitLog = subprocess.run(["git", "log", "-n 1", "--pretty=%d", "HEAD"], stdout=subprocess.PIPE).stdout.decode("utf-8").strip()
gitCommitHash = subprocess.run(["git", "rev-parse", "--short", "HEAD"], stdout=subprocess.PIPE).stdout.decode("utf-8").strip()
if "main" not in gitLog:
	VERSION = VERSION + "+SNAPSHOT." + gitCommitHash
	print("Not in main branch, added snapshot suffix: "+VERSION)

# Set VERSION constant in App.kt
fileLines = []
with open(APP_CLASS_PATH, 'r') as file:
	fileLines = file.readlines()
	
with open(APP_CLASS_PATH, 'w') as file:
	for fileLine in fileLines:
		versionDeclaration = "const val VERSION = "
		versionDeclarationIndex = fileLine.find(versionDeclaration)
		if versionDeclarationIndex != -1:
			fileLine = fileLine[0:versionDeclarationIndex+len(versionDeclaration)] + "\""+VERSION+"\"\n"
		file.write(fileLine)
