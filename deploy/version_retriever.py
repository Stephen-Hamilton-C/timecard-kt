import os

def getVersion():
	# cd to rootProject dir
	SCRIPT_PATH = os.path.realpath(__file__)
	SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
	os.chdir(SCRIPT_DIR)
	os.chdir("../")

	# Get app version
	print("Determining VERSION from Version.kt...")
	APP_CLASS_PATH = os.path.join("src", "main", "kotlin", "app", "shamilton", "timecardkt", "Version.kt")
	VERSION = ""
	with open(APP_CLASS_PATH, 'r') as file:
		for fileLine in file.readlines():
			if fileLine.strip().startswith("const val VERSION"):
				openQuote = fileLine.find("\"")
				closeQuote = fileLine.find("\"", openQuote+1)
				VERSION = fileLine[openQuote+1:closeQuote]
				print("VERSION determined from Version.kt: "+VERSION)
				break

	return VERSION