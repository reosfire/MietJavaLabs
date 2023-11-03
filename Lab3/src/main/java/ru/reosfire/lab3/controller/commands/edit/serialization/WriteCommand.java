package ru.reosfire.lab3.controller.commands.edit.serialization;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.logging.Log;

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
            Log.i("Database write initiated");
            context.getZoo().serializeToFile(context.DATABASE_FILE);

            context.view.showSerializationSuccess();
            Log.i("Database write success");
        } catch (Exception e) {
            context.view.showSerializationError();
            Log.e(e.getMessage());
        }
    }
}
