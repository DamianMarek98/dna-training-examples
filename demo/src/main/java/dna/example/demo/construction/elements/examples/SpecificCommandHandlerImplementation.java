package dna.example.demo.construction.elements.examples;

//can be a bean -> registered component
class SpecificCommandHandlerImplementation implements CommandHandler<Command> {
    @Override
    public void handle(Command command) {
        //this can work as domain service
    }
}
