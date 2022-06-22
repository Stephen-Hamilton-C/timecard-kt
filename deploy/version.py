import os
import subprocess

def getVersion(snapshotTag = True):
	# cd to rootProject dir
	SCRIPT_PATH = os.path.realpath(__file__)
	SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
	os.chdir(SCRIPT_DIR)
	os.chdir("../")

	# Get app version
	print("Determining VERSION from App.kt...")
	APP_CLASS_PATH = os.path.join("src", "main", "kotlin", "app", "shamilton", "timecardkt", "App.kt")
	VERSION = ""
	with open(APP_CLASS_PATH, 'r') as file:
		for fileLine in file.readlines():
			fileLine = fileLine.strip()
			if fileLine.startswith("const val VERSION"):
				openQuote = fileLine.find("\"")
				closeQuote = fileLine.find("\"", openQuote+1)
				VERSION = fileLine[openQuote+1:closeQuote]
				print("VERSION determined from App.kt: "+VERSION)

	if snapshotTag:
		# Add SNAPSHOT to version along with git commit if branch is not `main`
		# https://stackoverflow.com/a/4760517
		gitLog = subprocess.run(["git", "log", "-n 1", "--pretty=%d", "HEAD"], stdout=subprocess.PIPE).stdout.decode("utf-8").strip()
		gitCommitHash = subprocess.run(["git", "rev-parse", "--short", "HEAD"], stdout=subprocess.PIPE).stdout.decode("utf-8").strip()
		if "main" not in gitLog:
			VERSION = VERSION + "+SNAPSHOT." + gitCommitHash
			print("Not in main branch, added snapshot suffix: "+VERSION)

	return VERSION