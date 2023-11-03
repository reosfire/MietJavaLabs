package ru.reosfire.lab3.controller.commands.edit.serialization;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;

public class WriteCommand implements Command {
    @Override
    public String getId() {
        return "write";
    }

    @Override
    public String getName() {
        return "Write database to file";
    }

    @Override
    public void execute(CommandContext context) {
        try {
            context.getZoo().serializeToFile(context.DATABASE_FILE);

            context.view.showSerializationSuccess();
        } catch (Exception e) {
            context.view.showSerializationError();
        }
    }
}
