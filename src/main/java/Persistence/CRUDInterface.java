package Persistence;

import Models.Model;

import java.util.List;

public  interface CRUDInterface <T extends Model>{
    //CRUD - create, read, update, delete
    public T create(T model);

    public T read(int id);
    //get all
    public List<T> getAll();

    public void update(T model);

    public void delete(int id);
    //Also
    public void delete(T model);

}
