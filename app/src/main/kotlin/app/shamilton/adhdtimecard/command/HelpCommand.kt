package app.shamilton.adhdtimecard.command;

class HelpCommand(val command: String? = null) : Command() {
    
    public override val m_name: String = "help";
    public override val m_help: String = "";
    public override val m_alias: String? = "?";

    public override fun execute() {
        if(command == null) {
            println()
        }
    }

}
