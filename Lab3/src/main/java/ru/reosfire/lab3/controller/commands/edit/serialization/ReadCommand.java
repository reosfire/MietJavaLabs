package ru.reosfire.lab3.controller.commands.edit.serialization;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;
import ru.reosfire.lab3.logging.Log;
import ru.reosfire.lab3.models.Zoo;

public class ReadCommand implements Command {
    @Override
    public String getId() {
        return "read";
    }

    @Override
    public String getName() {
        return "Read database from file";
    }

    @Override
    public void execute(CommandContext context) {
        try {
            Log.i("Database read initiated");
            Zoo read = Zoo.deserializeFromFile(context.DATABASE_FILE);
            context.setZoo(read);

            context.view.showDeserializationSuccess();
            Log.i("Database read success");
        } catch (Exception e) {
            context.view.showDeserializationError();
            Log.e(e.getMessage());
        }
    }
}
