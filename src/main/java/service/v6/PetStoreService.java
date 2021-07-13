package service.v6;


import stereotype.Component;
import util.MessageTracker;

@Component(value="petStore")
public class PetStoreService implements IPetStoreService {

    public PetStoreService() {

    }

    public void placeOrder(){
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }



}
