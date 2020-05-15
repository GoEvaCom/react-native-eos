package io.plactal.eoscommander.data.local.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by swapnibble on 2017-12-11.
 */
@Dao
public interface EosAccountDao {
    @Query("SELECT account_name FROM eos_account ORDER BY account_name")
    List<String> getAll();

    @Query("SELECT account_name FROM eos_account WHERE account_name like :nameStarts ORDER BY account_name")
    List<String> getAll(String nameStarts);

    @Query("SELECT account_name FROM eos_account WHERE account_name like :nameStarts AND type=:account_type ORDER BY account_name")
    List<String> getAllWithType(String nameStarts, Integer account_type);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll( List<EosAccount> accounts);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll( EosAccount... accounts);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert( EosAccount account);

    @Delete
    void delete( EosAccount account);

    @Query("DELETE FROM eos_account")
    void deleteAll();
}
