package it.innotek.demo.bankaccount.providers;

import java.time.LocalDate;

import it.innotek.demo.bankaccount.model.balance.ServerResponseBalance;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransfer;
import it.innotek.demo.bankaccount.model.banktransfer.BankTransferResult;
import it.innotek.demo.bankaccount.model.banktransfer.ServerResponseBankTransferResult;
import it.innotek.demo.bankaccount.model.transaction.ServerResponseTransactions;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DefaultBankAccountProviderService {
	
	@GET("accounts/{accountId}/balance")
    public Call<ServerResponseBalance> getBalance(@Path("accountId") int accountID);

    @GET("accounts/{accountId}/transactions")
    public Call<ServerResponseTransactions> getTransactions(@Path("accountId") int accountID,@Query("fromAccountingDate") LocalDate from,@Query("toAccountingDate") LocalDate to);
    
    
    @POST("accounts/{accountId}/payments/money-transfers")
    public Call<ServerResponseBankTransferResult> moneyTransfers(@Path("accountId") int accountID , @Header("X-Time-Zone") String timeZone, @Body BankTransfer  transfer);
    
    
}
