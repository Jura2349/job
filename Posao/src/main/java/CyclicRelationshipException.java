public class CyclicRelationshipException extends Exception{

    public CyclicRelationshipException(){

        super("cyclic relationships (A -> B -> C -> A)");

    }

}
