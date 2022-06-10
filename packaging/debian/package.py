#!/bin/python3
# Steps followed:
# https://wiki.debian.org/Packaging/Intro?action=show&redirect=IntroDebianPackaging
import os
import shutil
import tarfile

SCRIPT_PATH = os.path.realpath(__file__)
SCRIPT_DIR = os.path.dirname(SCRIPT_PATH)
SCRIPT_NAME = os.path.basename(SCRIPT_PATH)
BUILD_DIR = 'dist'

os.chdir(SCRIPT_DIR)
if os.path.exists(BUILD_DIR):
	shutil.rmtree(BUILD_DIR)
os.mkdir(BUILD_DIR)
os.chdir(BUILD_DIR)

ORIG_BUILD_NAME = 'timecard-kt'
BUILD_ARTIFACT = os.path.join('..', '..', '..', 'build', 'distributions', ORIG_BUILD_NAME+'.tar')
APPKT_PATH = os.path.join('..', '..', '..', 'src', 'main', 'kotlin', 'app', 'shamilton', 'timecardkt', 'App.kt')
PACKAGE_NAME = None
VERSION = None

with open(APPKT_PATH, 'r') as file:
	fileLines = file.readlines()
	for line in fileLines:
		if line.find('VERSION = ') != -1:
			openQuote = line.find('"')
			line = line[openQuote+1:]
			closeQuote = line.find('"')
			VERSION = line[:closeQuote]
			break

if VERSION == None:
	raise Exception('Could not determine version from App.kt! Is there a VERSION constant?')

PACKAGE_NAME = 'timecardkt_'+VERSION

def runCommand(command):
	print(command)
	exitCode = os.system(command)
	print('Finished with exit code '+str(exitCode))
	if exitCode != 0:
		raise Exception('Command `'+command+'` failed with exit code '+str(exitCode)+'.')

def copyUpstreamTarball():
	UPSTREAM_TARBALL = PACKAGE_NAME+'.orig.tar'
	print(UPSTREAM_TARBALL)

	shutil.copy2(BUILD_ARTIFACT, UPSTREAM_TARBALL)
	return UPSTREAM_TARBALL

def unpackUpstreamTarball(UPSTREAM_TARBALL):
	with tarfile.open(UPSTREAM_TARBALL) as tarball:
		tarball.extractall();
		os.rename(ORIG_BUILD_NAME, PACKAGE_NAME)

def addDebianPackagingFiles():
	os.chdir(PACKAGE_NAME)
	os.mkdir('debian')
	runCommand('dch --create -v '+VERSION+' --package '+PACKAGE_NAME[:-len(VERSION)-1]+'')
	os.chdir('debian')

	# https://stackoverflow.com/a/3207973
	filenames = next(os.walk(SCRIPT_DIR), (None, None, []))[2]
	
	for filename in filenames:
		if filename != SCRIPT_NAME:
			shutil.copy2(os.path.join(SCRIPT_DIR, filename), filename)

UPSTREAM_TARBALL = copyUpstreamTarball()
unpackUpstreamTarball(UPSTREAM_TARBALL)
addDebianPackagingFiles()
