package app.shamilton.timecard.command

import app.shamilton.timecard.App

class VersionCommand : ICommand {

    override val m_Name: String = "VERSION"
    override val m_Help: String = "" // TODO

    override fun execute() {
        println("timecard version ${App().VERSION}")
    }

}