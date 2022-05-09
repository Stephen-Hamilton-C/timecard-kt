package app.shamilton.timecard.command;

class HelpCommand : ICommand {
    
    override val m_Name: String = "HELP";
    override val m_Help: String = "";

    override fun execute() {
        return;
    }

}
