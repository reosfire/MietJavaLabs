package ru.reosfire.lab3.controller.commands.edit.serialization;

import ru.reosfire.lab3.controller.CommandContext;
import ru.reosfire.lab3.controller.commands.Command;
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
            Zoo read = Zoo.deserializeFromFile(context.DATABASE_FILE);
            context.setZoo(read);

            context.view.showDeserializationSuccess();
        } catch (Exception e) {
            context.view.showDeserializationError();
        }
    }
}
