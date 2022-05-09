package app.shamilton.timecard.command

import app.shamilton.timecard.App

class VersionCommand : ICommand {

    override val m_Name: String = "VERSION";
    override val m_Help: String = "";

    override fun execute() {
        val app = App();
        println("timecard version ${app.VERSION}");
    }

}