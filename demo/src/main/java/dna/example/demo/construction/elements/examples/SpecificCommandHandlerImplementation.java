package dna.example.demo.construction.elements.examples;

//can be a bean -> registered component
class SpecificCommandHandlerImplementation implements CommandHandler<Command> {
    @Override
    public void handle(Command command) {
        //this can work as domain service
        //todo we need commandBus which takes care of triggering handlers -> we can register or lookup for beans which are command handlers
        //look for beans that are annotated for example
    }
}
