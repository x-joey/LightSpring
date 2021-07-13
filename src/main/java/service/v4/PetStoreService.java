package service.v4;

import beans.factory.annotation.Autowired;
import dao.v4.AccountDao;
import dao.v4.ItemDao;
import stereotype.Component;

@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao(){
        return accountDao;
    }

    public ItemDao getItemDao(){
        return itemDao;
    }
}
