package io.onemfive.data;

/**
 * Provides a message indicating what command to run and the target to run it against.
 * Supplied target must implement Command
 *
 * @author objectorange
 */
public final class CommandMessage extends BaseMessage {

    public enum Command {
        Start,
        Shutdown,
        Restart
    }

    private Command command;
    private String targetName;

    public CommandMessage() {}

    public CommandMessage(Command command, String targetName) {
        this.command = command;
        this.targetName = targetName;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }
}
