package service.v5;

import beans.factory.annotation.Autowired;
import dao.v5.AccountDao;
import dao.v5.ItemDao;
import stereotype.Component;
import util.MessageTracker;

@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    ItemDao itemDao;

    public PetStoreService() {

    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void placeOrder(){
        System.out.println("place order");
        MessageTracker.addMsg("place order");

    }

    public void placeOrderWithException(){
        throw new NullPointerException();
    }
}
